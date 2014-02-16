package ru.org.icad.mishka.app.loader.db;

import ru.org.icad.mishka.app.model.CastingUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author Ivan Solovyev.
 */
public class CastingUnitLoader implements DBLoader<CastingUnit>
{
    @Override
    public Collection<CastingUnit> load() throws SQLException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        return em.createNamedQuery("CastingUnit.findAll", CastingUnit.class).getResultList();
    }

    @Override
    public Map<Object, CastingUnit> loadMap() throws SQLException
    {
        Map<Object, CastingUnit> customerOrderMap = new HashMap<>();

        for (CastingUnit cu : this.load())
        {
            customerOrderMap.put(cu.getId(), cu);
        }

        return customerOrderMap;
    }
}
