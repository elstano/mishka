package ru.org.icad.mishka.app.jdbc;

import java.sql.PreparedStatement;

/**
 * User: Boss
 * Date: 9/22/13
 * Time: 4:28 AM
 */
public abstract class RSHandler<T> implements JDBCHandler<T>{
    @Override
    public void parametrize(PreparedStatement statement) {
        //do nothing
    }
}
