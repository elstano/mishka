package ru.org.icad.mishka.app.cache;

import ru.org.icad.mishka.app.model.PrepareTimeConst;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PrepareTimeConstCache {

    private static volatile PrepareTimeConstCache instance;

    private ConcurrentMap<EquipmentKey, PrepareTimeConst> prepareTimeConstMap;

    private PrepareTimeConstCache() {
        prepareTimeConstMap = new ConcurrentHashMap<>();

        loadPrepareTimeConsts();
    }

    public static PrepareTimeConstCache getInstance() {
        PrepareTimeConstCache localInstance = instance;
        if (localInstance == null) {
            synchronized (PrepareTimeConstCache.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PrepareTimeConstCache();
                }
            }
        }

        return localInstance;
    }

    private void loadPrepareTimeConsts() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        List<PrepareTimeConst> prepareTimeConsts
                = em.createNamedQuery("PrepareTimeConst.findAll", PrepareTimeConst.class).getResultList();
        for (PrepareTimeConst prepareTimeConst : prepareTimeConsts) {

            if (prepareTimeConst.getCastingUnitCollector() != null) {
                prepareTimeConstMap.put(
                        new EquipmentKey(prepareTimeConst.getCastingUnitCollector().getId(), prepareTimeConst.getMark().getId())
                        , prepareTimeConst
                );
            }

            if (prepareTimeConst.getCastingUnitDistributor() != null) {
                prepareTimeConstMap.put(
                        new EquipmentKey(prepareTimeConst.getCastingUnitDistributor().getId(), prepareTimeConst.getMark().getId())
                        , prepareTimeConst
                );
            }

            if (prepareTimeConst.getCastingUnitCastingMachine() != null) {

                prepareTimeConstMap.put(
                        new EquipmentKey(prepareTimeConst.getCastingUnitCastingMachine().getId(), prepareTimeConst.getMark().getId())
                        , prepareTimeConst
                );
            }
        }
    }

    public int getDurationTime(EquipmentKey equipmentKey) {
        return prepareTimeConstMap.get(equipmentKey).getDurationTime();
    }
}