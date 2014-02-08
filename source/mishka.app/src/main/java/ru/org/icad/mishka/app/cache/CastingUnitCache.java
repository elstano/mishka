package ru.org.icad.mishka.app.cache;

import ru.org.icad.mishka.app.model.CastingUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CastingUnitCache {

    private static volatile CastingUnitCache instance;

    private ConcurrentMap<Integer, CastingUnit> castingUnitConcurrentMap;

    private CastingUnitCache() {
        castingUnitConcurrentMap = new ConcurrentHashMap<>();

        loadCastingUnit();
    }

    private void loadCastingUnit() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        List<CastingUnit> castingUnits = em.createNamedQuery("CastingUnit.findAll", CastingUnit.class).getResultList();
        for (CastingUnit castingUnit : castingUnits) {
            castingUnitConcurrentMap.put(castingUnit.getId(), castingUnit);
        }
    }

    public int getLadlePourTimeMax(Integer id) {
        return castingUnitConcurrentMap.get(id).getLadlePourTimeMax();
    }

    public static CastingUnitCache getInstance() {
        CastingUnitCache localInstance = instance;
        if (localInstance == null) {
            synchronized (CastingUnitCache.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CastingUnitCache();
                }
            }
        }
        return localInstance;
    }
}
