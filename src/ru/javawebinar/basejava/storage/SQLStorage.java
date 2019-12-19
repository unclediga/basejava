package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.*;
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
        });
    }

    @Override
    public void save(Resume resume) {
        helper.execute(conn -> {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES(?,?)");
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            try {
                ps.execute();
            } catch (SQLException e) {
//                The SQLState is a 5-char code, of which the first two are common among all DB's...
//                23: integrity constraint violation
//                ...
                if (e.getSQLState().startsWith("23")) {
                    throw new ExistStorageException(resume.getUuid());
                } else {
                    throw e;
                }
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeAndGet(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return helper.getResume(rs);
        });
    }

    @Override
    public void delete(String uuid) {
        helper.execute(conn -> {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?");
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0)
                throw new NotExistStorageException(uuid);
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> list = new ArrayList<>();
        helper.execute(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(helper.getResume(rs));
            }
        });
        return list;
    }

    @Override
    public int size() {
        return helper.executeAndGet(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    static class SQLHelper {
        private final ConnectionFactory connectionFactory;

        public SQLHelper(String dbUrl, String dbUser, String dbPassword) {
            connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }

        public <T> void execute(codeNoValue<T> codeBlock) {
            try (Connection conn = connectionFactory.getConnection()) {
                codeBlock.exec(conn);
            } catch (SQLException e) {
                throw new StorageException("SQL Error", e);
            }
        }

        public <T> T executeAndGet(codeReturnValue<T> codeBlock) {
            try (Connection conn = connectionFactory.getConnection()) {
                return codeBlock.exec(conn);
            } catch (SQLException e) {
                throw new StorageException("SQL Error", e);
            }
        }

        public Resume getResume(ResultSet rs) throws SQLException {
            return new Resume(rs.getString(1).trim(), rs.getString("full_name"));
        }
    }

    interface codeReturnValue<T> {
        T exec(Connection conn) throws SQLException;
    }

    interface codeNoValue<T> {
        void exec(Connection conn) throws SQLException;
    }
}
