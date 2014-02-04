package ru.org.icad.mishka.ejb.cache;

import ru.org.icad.mishka.app.model.PrepareTimeConst;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Singleton
@LocalBean
@Startup
public class PrepareTimeConstCacheBean {
    @PersistenceContext(unitName = "MishkaService")
    private EntityManager em;

    private ConcurrentMap<Integer, PrepareTimeConst> prepareTimeConstCollectorConcurrentMap;
    private ConcurrentMap<Integer, PrepareTimeConst> prepareTimeConstDistributorConcurrentMap;
    private ConcurrentMap<Integer, PrepareTimeConst> prepareTimeConstCastingMachineConcurrentMap;

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

    @Lock(LockType.READ)
    public PrepareTimeConst getPrepareTimeConstForCollector(Integer id) {
        return prepareTimeConstCollectorConcurrentMap.get(id);
    }

    @Lock(LockType.READ)
    public PrepareTimeConst getPrepareTimeConstForDistributor(Integer id) {
        return prepareTimeConstDistributorConcurrentMap.get(id);
    }

    @Lock(LockType.READ)
    public PrepareTimeConst getPrepareTimeConstForCastingMachine(Integer id) {
        return prepareTimeConstCastingMachineConcurrentMap.get(id);
    }

    @PostConstruct
    public void init() {
        prepareTimeConstCollectorConcurrentMap = new ConcurrentHashMap<>();
        prepareTimeConstDistributorConcurrentMap = new ConcurrentHashMap<>();
        prepareTimeConstCastingMachineConcurrentMap = new ConcurrentHashMap<>();

        loadPrepareTimeConsts();
    }
}