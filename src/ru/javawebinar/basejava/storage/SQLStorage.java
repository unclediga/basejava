package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SQLHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLStorage implements Storage {
    private final SQLHelper helper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        helper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return helper.execute("SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume resume) {
        helper.<Void>execute("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0)
                throw new NotExistStorageException(resume.getUuid());
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        helper.<Void>execute("INSERT INTO resume(uuid, full_name) VALUES(?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.<Void>execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0)
                throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.execute("SELECT * FROM resume r ORDER BY full_name, uuid", ps -> {
            ArrayList<Resume> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return list;
        });
    }

    @Override
    public int size() {
        return helper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
