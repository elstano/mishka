package ru.org.icad.mishka.app.jdbc;

import java.sql.PreparedStatement;

public abstract class RSHandler<T> implements JDBCHandler<T>{
    @Override
    public void parametrize(PreparedStatement statement) {
        //do nothing
    }
}
