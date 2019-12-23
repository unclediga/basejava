package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {
    private final ConnectionFactory connectionFactory;

    public SQLHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(String sqlStatement, CustomCode<T> codeBlock) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sqlStatement);
            return codeBlock.exec(ps);
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
        T exec(PreparedStatement ps) throws SQLException;
    }
}
