package ru.org.icad.mishka.app.cache;

import ru.org.icad.mishka.app.model.PrepareTimeConst;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PrepareTimeConstCache {

    private static final PrepareTimeConstCache INSTANCE = new PrepareTimeConstCache();
    @PersistenceContext(unitName = "MishkaService")
    private EntityManager em;
    private ConcurrentMap<Integer, PrepareTimeConst> prepareTimeConstCollectorConcurrentMap;
    private ConcurrentMap<Integer, PrepareTimeConst> prepareTimeConstDistributorConcurrentMap;
    private ConcurrentMap<Integer, PrepareTimeConst> prepareTimeConstCastingMachineConcurrentMap;

    private PrepareTimeConstCache() {
        prepareTimeConstCollectorConcurrentMap = new ConcurrentHashMap<>();
        prepareTimeConstDistributorConcurrentMap = new ConcurrentHashMap<>();
        prepareTimeConstCastingMachineConcurrentMap = new ConcurrentHashMap<>();

        loadPrepareTimeConsts();
    }

    public static PrepareTimeConstCache getInstance() {
        return INSTANCE;
    }

    private void loadPrepareTimeConsts() {
        List<PrepareTimeConst> collectorPrepareTimeConsts
                = em.createNamedQuery("PrepareTimeConst.findAllWhereCastingUnitCollectorIsNotNull", PrepareTimeConst.class).getResultList();
        for (PrepareTimeConst prepareTimeConst : collectorPrepareTimeConsts) {
            prepareTimeConstCollectorConcurrentMap.put(prepareTimeConst.getCastingUnitCollector().getId(), prepareTimeConst);
        }

        List<PrepareTimeConst> distributorPrepareTimeConsts
                = em.createNamedQuery("PrepareTimeConst.findAllWhereCastingUnitDistributorIsNotNull", PrepareTimeConst.class).getResultList();
        for (PrepareTimeConst prepareTimeConst : distributorPrepareTimeConsts) {
            prepareTimeConstDistributorConcurrentMap.put(prepareTimeConst.getCastingUnitDistributor().getId(), prepareTimeConst);
        }


        List<PrepareTimeConst> castingMachinePrepareTimeConsts
                = em.createNamedQuery("PrepareTimeConst.findAllWhereCastingUnitCastingMachineIsNotNull", PrepareTimeConst.class).getResultList();
        for (PrepareTimeConst prepareTimeConst : castingMachinePrepareTimeConsts) {
            prepareTimeConstCastingMachineConcurrentMap.put(prepareTimeConst.getCastingUnitDistributor().getId(), prepareTimeConst);
        }
    }

    public PrepareTimeConst getPrepareTimeConstForCollector(Integer id) {
        return prepareTimeConstCollectorConcurrentMap.get(id);
    }

    public PrepareTimeConst getPrepareTimeConstForDistributor(Integer id) {
        return prepareTimeConstDistributorConcurrentMap.get(id);
    }

    public PrepareTimeConst getPrepareTimeConstForCastingMachine(Integer id) {
        return prepareTimeConstCastingMachineConcurrentMap.get(id);
    }
}