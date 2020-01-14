package ru.javawebinar.basejava.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private final Gson GSON = new GsonBuilder().create();

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
                    addContact(resume, rs.getString("type"), rs.getString("value"));
                } while (rs.next());
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT type, content FROM section " +
                    "WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resume, rs.getString("type"), rs.getString("content"));
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
                        "select r.uuid," +
                        "r.full_name," +
                        "(select json_object_agg(type,value) from contact where resume_uuid = r.uuid)," +
                        "(select json_object_agg(type,content) from section where resume_uuid = r.uuid) " +
                        "from resume r " +
                        "ORDER BY r.full_name, r.uuid",
                ps -> {
                    LinkedHashMap<String, Resume> resumes = new LinkedHashMap<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid").trim();
                        Resume resume = new Resume(uuid, rs.getString("full_name"));
                        resumes.put(uuid, resume);
                        new JsonContentProcessor(resume, rs.getString(3), this::addContact).process();
                        new JsonContentProcessor(resume, rs.getString(4), this::addSection).process();
                    }
                    return new ArrayList<>(resumes.values());
                });
    }

    interface ElementProcessor {
        void addElement(Resume resume, String type, String content);
    }

    private class JsonContentProcessor {
        private final Resume resume;
        private final String content;
        private final ElementProcessor elementProcessor;

        JsonContentProcessor(Resume resume, String content, ElementProcessor elementProcessor) {
            this.resume = resume;
            this.content = content;
            this.elementProcessor = elementProcessor;
        }

        public void process() {
            if (content == null) return;
            Map<String, String> el = GSON.<Map<String, String>>fromJson(content, Map.class);
            if (el != null) {
                for (String type : el.keySet()) {
                    elementProcessor.addElement(resume, type, el.get(type));
                }
            }
        }
    }

    private void addContacts(Resume resume, String contacts) {
        if (contacts == null) return;
        Map<String, String> el = GSON.<Map<String, String>>fromJson(contacts, Map.class);
        if (el != null) {
            for (String type : el.keySet()) {
                addContact(resume, type, el.get(type));
            }
        }
    }

    private void addSections(Resume resume, String sections) {
        if (sections == null) return;
        Map<String, String> el = GSON.<Map<String, String>>fromJson(sections, Map.class);
        if (el != null) {
            for (String type : el.keySet()) {
                addSection(resume, type, el.get(type));
            }
        }
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

    private void addContact(Resume resume, String type, String value) {
        if (type == null) return;
        ContactType contactType = ContactType.valueOf(type);
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

    private void addSection(Resume resume, String type, String content) {
        SectionType sectionType = SectionType.valueOf(type);
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                resume.addSection(sectionType, new TextSection(content));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                if (content != null) {
                    resume.addSection(sectionType, new ListSection(content.split("\n")));
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
        }
    }
}
