package ru.org.icad.mishka.app.loader.db;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Ivan Solovyev
 * This interface loads Entities from DataBase.
 */
public interface DBLoader<T>
{
    public Collection<T> load() throws SQLException;

    public Map<Object, T> loadMap() throws SQLException;
}
