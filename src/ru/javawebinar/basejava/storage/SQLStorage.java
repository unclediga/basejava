package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SQLHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLStorage implements Storage {
    private final SQLHelper helper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException("Can't find jdbc-driver class  'org.postgresql.Driver'", e);
        }
        helper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        helper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeTransactional(conn -> {
            Resume resume = null;
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT * FROM resume r " +
                    "LEFT JOIN contact c " +
                    "ON r.uuid = c.resume_uuid " +
                    "WHERE r.uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
                do {
                    addContact(resume, rs);
                } while (rs.next());
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT type, content FROM section " +
                    "WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resume, rs);
                }
            }
            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        helper.<Void>executeTransactional(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0)
                    throw new NotExistStorageException(resume.getUuid());
            }
            deleteDetails(resume, "contact");
            insertContacts(resume, conn);
            deleteDetails(resume, "section");
            insertSections(resume, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        helper.<Void>executeTransactional(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES(?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.executeUpdate();
            }
            insertContacts(resume, conn);
            insertSections(resume, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.<Void>execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.execute("" +
                "SELECT " +
                "  r.full_name, r.uuid, " +
                "  c.type, c.value " +
                "FROM resume r LEFT JOIN contact c " +
                "ON r.uuid = c.resume_uuid " +
                "ORDER BY r.full_name, r.uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            LinkedHashMap<String, Resume> resumes = new LinkedHashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid").trim();
                Resume resume = new Resume(uuid, rs.getString("full_name"));
                resumes.put(uuid, resume);
                helper.execute("SELECT type, value FROM contact c WHERE resume_uuid = ?", ps_c -> {
                    ps_c.setString(1, uuid);
                    ResultSet rs_c = ps_c.executeQuery();
                    while (rs_c.next())
                        addContact(resume, rs_c);
                    return null;
                });
                helper.execute("SELECT type, content FROM section WHERE resume_uuid = ?", ps_s -> {
                    ps_s.setString(1, uuid);
                    ResultSet rs_s = ps_s.executeQuery();
                    while (rs_s.next())
                        addSection(resume, rs_s);
                    return null;
                });
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return helper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void deleteDetails(Resume resume, String tableName) {
        helper.execute("DELETE FROM " + tableName + " WHERE resume_uuid = ?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
            return null;
        });
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact(resume_uuid, type, value) VALUES(?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if (type == null) return;
        ContactType contactType = ContactType.valueOf(type);
        String value = rs.getString("value");
        resume.addContact(contactType, value);
    }

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section(resume_uuid, type, content) VALUES(?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                SectionType sectionType = e.getKey();
                ps.setString(2, sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, ((TextSection) e.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ps.setString(3, String.join("\n", ((ListSection) (e.getValue())).getContent()));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        SectionType type = SectionType.valueOf(rs.getString("type"));
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                resume.addSection(type, new TextSection(rs.getString("content")));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                String joined = rs.getString("content");
                if (joined != null) {
                    resume.addSection(type, new ListSection(joined.split("\n")));
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
        }
    }
}
