package ru.org.icad.mishka.app.cache;

import ru.org.icad.mishka.app.model.CastHouse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CastHouseCache {

    private static volatile CastHouseCache instance;

    private Map<Integer, CastHouse> castHouseMap;

    private CastHouseCache() {
        castHouseMap = new ConcurrentHashMap<>();

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
            castHouseMap.put(castHouse.getId(), castHouse);
        }
    }

    public double getLadleTonnageMax(Integer id) {
        return castHouseMap.get(id).getLadleTonnageMax();
    }
}
