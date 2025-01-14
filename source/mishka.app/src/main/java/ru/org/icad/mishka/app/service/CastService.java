package ru.org.icad.mishka.app.service;


import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.CustomerOrder;

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
            CustomerOrder customerOrder,
            int ingotCount,
            int ingotInBlankCount
    ) {
        Cast cast = new Cast();
        cast.setCastingUnit(new CastingUnit(castingUnitId));
        cast.setCastDate(date);
        cast.setShift(shift);
        cast.setCastNumber(castNumber);
        cast.setCustomerOrder(customerOrder);
        cast.setIngotCount(ingotCount);
        cast.setIngotInBlankCount(ingotInBlankCount);

        entityManager.persist(cast);
        return cast;
    }
}
