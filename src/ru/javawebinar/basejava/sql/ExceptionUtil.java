package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    public static StorageException convertException(SQLException e) {
//                The SQLState is a 5-char code, of which the first two are common among all DB's...
//                23: integrity constraint violation
//                ...
        if (e instanceof PSQLException) {
            if (e.getSQLState().startsWith("23505")) {
                return new ExistStorageException(null/*resume.getUuid()*/);
            }
        }
        return new StorageException("SQL Error", e);
    }
}
