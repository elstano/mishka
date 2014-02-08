package ru.org.icad.mishka.app.cache;

import ru.org.icad.mishka.app.model.CastHouse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CastHouseCache {

    private static volatile CastHouseCache instance;

    private ConcurrentMap<Integer, CastHouse> CastHouseCachetConcurrentMap;

    private CastHouseCache() {
        CastHouseCachetConcurrentMap = new ConcurrentHashMap<>();

        loadCastingUnit();
    }

    public static CastHouseCache getInstance() {
        CastHouseCache localInstance = instance;
        if (localInstance == null) {
            synchronized (CastHouseCache.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CastHouseCache();
                }
            }
        }

        return localInstance;
    }

    private void loadCastingUnit() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        List<CastHouse> castHouses = em.createNamedQuery("CastHouse.findAll", CastHouse.class).getResultList();
        for (CastHouse castHouse : castHouses) {
            CastHouseCachetConcurrentMap.put(castHouse.getId(), castHouse);
        }
    }

    public double getLadleTonnageMax(Integer id) {
        return CastHouseCachetConcurrentMap.get(id).getLadleTonnageMax();
    }
}
