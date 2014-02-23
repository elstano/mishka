package ru.org.icad.mishka.app.cache;

import ru.org.icad.mishka.app.model.PeriodicOperation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PeriodicOperationCache {

    private static final PeriodicOperationCache INSTANCE = new PeriodicOperationCache();
    @PersistenceContext(unitName = "MishkaService")
    private EntityManager em;
    private Map<Integer, PeriodicOperation> periodicOperationCollectorConcurrentMap;

    private PeriodicOperationCache() {
        periodicOperationCollectorConcurrentMap = new ConcurrentHashMap<>();

        loadPeriodicOperation();
    }

    public static PeriodicOperationCache getInstance() {
        return INSTANCE;
    }

    private void loadPeriodicOperation() {
        List<PeriodicOperation> periodicOperations
                = em.createNamedQuery("PeriodicOperation.findAllWhereCastingUnitCollectorIsNotNull", PeriodicOperation.class).getResultList();
        for (PeriodicOperation periodicOperation : periodicOperations) {
            periodicOperationCollectorConcurrentMap.put(periodicOperation.getCastingUnitCollector().getId(), periodicOperation);
        }
    }

    public PeriodicOperation getPeriodicOperationForCollector(Integer id) {
        return periodicOperationCollectorConcurrentMap.get(id);
    }
}
