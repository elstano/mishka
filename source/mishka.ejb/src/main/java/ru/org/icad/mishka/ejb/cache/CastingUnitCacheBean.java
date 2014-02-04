package ru.org.icad.mishka.ejb.cache;

import ru.org.icad.mishka.app.model.CastingUnit;

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
public class CastingUnitCacheBean {
    @PersistenceContext(unitName = "MishkaService")
    private EntityManager em;

    private ConcurrentMap<Integer, CastingUnit> castingUnitConcurrentMap;

    private void loadCastingUnit() {
        List<CastingUnit> castingUnits = em.createNamedQuery("CastingUnit.findAll", CastingUnit.class).getResultList();
        for (CastingUnit castingUnit : castingUnits) {
            castingUnitConcurrentMap.put(castingUnit.getId(), castingUnit);
        }
    }

    @Lock(LockType.READ)
    public CastingUnit getCastingUnit(Integer id) {
        return castingUnitConcurrentMap.get(id);
    }

    @PostConstruct
    public void init() {
        castingUnitConcurrentMap = new ConcurrentHashMap<>();
        loadCastingUnit();
    }
}
