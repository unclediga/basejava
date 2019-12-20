package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.SQLHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLStorage implements Storage {
    private final SQLHelper helper;

    SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SQLHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        helper.execute(conn -> {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM resume");
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        helper.execute(conn -> {
            PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?");
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0)
                throw new NotExistStorageException(resume.getUuid());
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        helper.execute(conn -> {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES(?,?)");
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.execute(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        helper.execute(conn -> {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?");
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0)
                throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> list = new ArrayList<>();
        helper.execute(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name, uuid");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return null;
        });
        return list;
    }

    @Override
    public int size() {
        return helper.execute(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
