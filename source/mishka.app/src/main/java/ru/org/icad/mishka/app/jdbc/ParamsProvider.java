package ru.org.icad.mishka.app.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ParamsProvider implements JDBCHandler<Void> {
    @Override
    public Void onResultSet(ResultSet rs) throws SQLException {
        throw new RuntimeException("Not Supported");
    }
}
