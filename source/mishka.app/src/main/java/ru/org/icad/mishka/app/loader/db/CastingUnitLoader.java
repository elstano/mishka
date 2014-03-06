package ru.org.icad.mishka.app.loader.db;

import ru.org.icad.mishka.app.model.CastingUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CastingUnitLoader implements DBLoader<CastingUnit>
{
    @Override
    public Collection<CastingUnit> load() throws SQLException
    {
        Collection<CastingUnit> castingUnits = new ArrayList<>();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();
        TypedQuery<CastingUnit> query = em.createNamedQuery("CastingUnit.findByPrimaryKey", CastingUnit.class);
        query.setParameter("id", 22);
        CastingUnit cu22 = query.getSingleResult();
        cu22.setStartTime(new Date(2013, 5, 1));
        castingUnits.add(cu22);
        query.setParameter("id", 24);
        CastingUnit cu24 = query.getSingleResult();
        cu24.setStartTime(new Date(2013, 5, 1));
        castingUnits.add(cu24);

        return castingUnits;
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
