package ru.org.icad.mishka.app.cache;

import ru.org.icad.mishka.app.model.CastingUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CastingUnitCache {

    private static volatile CastingUnitCache instance;

    private Map<Integer, CastingUnit> castingUnitMap;

    private CastingUnitCache() {
        castingUnitMap = new ConcurrentHashMap<>();

        loadCastingUnit();
    }

    private void loadCastingUnit() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        List<CastingUnit> castingUnits = em.createNamedQuery("CastingUnit.findAll", CastingUnit.class).getResultList();
        for (CastingUnit castingUnit : castingUnits) {
            castingUnitMap.put(castingUnit.getId(), castingUnit);
        }
    }

    public int getLadlePourTimeMax(Integer id) {
        return castingUnitMap.get(id).getLadlePourTimeMax();
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
