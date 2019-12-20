package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private final ConnectionFactory connectionFactory;

    public SQLHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(CustomCode<T> codeBlock) {
        try (Connection conn = connectionFactory.getConnection()) {
            return codeBlock.exec(conn);
        } catch (SQLException e) {
//                The SQLState is a 5-char code, of which the first two are common among all DB's...
//                23: integrity constraint violation
//                ...
            if (e.getSQLState().startsWith("23")) {
                throw new ExistStorageException("???"/*resume.getUuid()*/);
            } else {
                throw new StorageException("SQL Error", e);
            }
        }
    }

    public interface CustomCode<T> {
        T exec(Connection conn) throws SQLException;
    }
}
