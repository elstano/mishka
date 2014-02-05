package ru.org.icad.mishka.app.cache;

import ru.org.icad.mishka.app.model.PeriodicOperation;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Singleton
public class PeriodicOperationCache {

    @PersistenceContext(unitName = "MishkaService")
    private EntityManager em;

    private ConcurrentMap<Integer, PeriodicOperation> periodicOperationCollectorConcurrentMap;

    public PeriodicOperationCache() {
        periodicOperationCollectorConcurrentMap = new ConcurrentHashMap<>();

        loadPeriodicOperation();
    }

    private void loadPeriodicOperation() {
        List<PeriodicOperation> periodicOperations
                = em.createNamedQuery("PeriodicOperation.findAllWhereCastingUnitCollectorIsNotNull", PeriodicOperation.class).getResultList();
        for (PeriodicOperation periodicOperation : periodicOperations) {
            periodicOperationCollectorConcurrentMap.put(periodicOperation.getCastingUnitCollector().getId(), periodicOperation);
        }
    }

}
