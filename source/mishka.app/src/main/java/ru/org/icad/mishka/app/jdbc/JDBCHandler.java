package ru.org.icad.mishka.app.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: Boss
 * Date: 9/22/13
 * Time: 4:27 AM
 */
public interface JDBCHandler<T> {
    T onResultSet(ResultSet rs) throws SQLException;
    void parametrize(PreparedStatement statement) throws SQLException;
}
