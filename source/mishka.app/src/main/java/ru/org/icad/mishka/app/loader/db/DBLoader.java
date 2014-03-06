package ru.org.icad.mishka.app.loader.db;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public interface DBLoader<T>
{
    public Collection<T> load() throws SQLException;

    public Map<Object, T> loadMap() throws SQLException;
}
