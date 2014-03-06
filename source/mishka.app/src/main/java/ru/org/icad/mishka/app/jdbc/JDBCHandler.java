package ru.org.icad.mishka.app.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface JDBCHandler<T> {
    T onResultSet(ResultSet rs) throws SQLException;
    void parametrize(PreparedStatement statement) throws SQLException;
}
