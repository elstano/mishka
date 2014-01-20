package ru.org.icad.mishka.app.service;


import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;

public class CastService {

    @PersistenceContext(unitName = "MishkaService")
    protected EntityManager entityManager;

    public CastService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Cast createCast(
            int castingUnitId,
            Date date,
            int shift,
            int castNumber,
            Order order,
            int ingotCount,
            int ingotInBlankCount
    ) {
        Cast cast = new Cast();
        cast.setCastingUnit(new CastingUnit(castingUnitId));
        cast.setDate(date);
        cast.setShift(shift);
        cast.setCastNumber(castNumber);
        cast.setOrder(order);
        cast.setIngotCount(ingotCount);
        cast.setIngotInBlankCount(ingotInBlankCount);

        entityManager.persist(cast);
        return cast;
    }
}
