package ru.org.icad.mishka.app.process.casting;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.PeriodicOperation;

import javax.persistence.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class CastingUnitWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitWrapper.class);

    private static final String START_DATE = "01/05/2013 00:00:00";
    private static final String END_DATE = "01/06/2013 00:00:00";

    private static final Function<Cast, CastWrapper> CAST_WRAPPER_FUNCTION = new Function<Cast, CastWrapper>() {
        @Override
        public CastWrapper apply(Cast cast) {
            return new CastWrapper(cast);
        }
    };

    private Queue<Operation> operations = Queues.newConcurrentLinkedQueue();

    private CastingUnit castingUnit;
    private Schema schema;

    public CastingUnitWrapper() {
    }

    public CastingUnitWrapper(CastingUnit castingUnit, Schema schema) {
        this.castingUnit = castingUnit;
        this.schema = schema;
    }

    public CastingUnit getCastingUnit() {
        return castingUnit;
    }

    public void setCastingUnit(CastingUnit castingUnit) {
        this.castingUnit = castingUnit;
    }

    public Queue<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Queue<Operation> operations) {
        this.operations = operations;
    }

    public void castingProcess() {
        Date startDate = stringToDate(START_DATE);
        Date endDate = stringToDate(END_DATE);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();



        TypedQuery<PeriodicOperation> cleanOperationQuery = em.createNamedQuery("PeriodicOperation.findCleanOperationForCollectorBetweenDate", PeriodicOperation.class);
        cleanOperationQuery.setParameter("startDate", startDate, TemporalType.DATE);
        cleanOperationQuery.setParameter("endDate", endDate, TemporalType.DATE);
        cleanOperationQuery.setParameter("castingUnitCollectorId", 49);

        Queue<PeriodicOperation> cleanCollectorOperations = Queues.newConcurrentLinkedQueue(cleanOperationQuery.getResultList());


        TypedQuery<PeriodicOperation> periodicOperationQuery = em.createNamedQuery("PeriodicOperation.findPeriodicOperationForCastingMachineBetweenDate", PeriodicOperation.class);
        periodicOperationQuery.setParameter("startDate", startDate, TemporalType.DATE);
        periodicOperationQuery.setParameter("endDate", endDate, TemporalType.DATE);
        periodicOperationQuery.setParameter("castingUnitCastingMachineId", 46);

        Queue<PeriodicOperation> periodicOperations = Queues.newConcurrentLinkedQueue(periodicOperationQuery.getResultList());


        TypedQuery<Cast> typedQuery = em.createNamedQuery("Cast.getCastsForCastingUnitBetweenDate", Cast.class);
        typedQuery.setParameter("startDate", startDate, TemporalType.DATE);
        typedQuery.setParameter("endDate", endDate, TemporalType.DATE);
        typedQuery.setParameter("castingUnitId", castingUnit.getId());

        List<Cast> casts = typedQuery.getResultList();

        List<CastWrapper> castWrappers = Lists.newArrayList();
        for (Cast cast : casts) {
            castWrappers.add(new CastWrapper(cast));
        }

        Collections.sort((List<CastWrapper>) castWrappers, new Comparator<CastWrapper>() {
            @Override
            public int compare(CastWrapper o1, CastWrapper o2) {
                final Cast castFirst = o1.getCast();
                final Cast castSecond = o2.getCast();
                int castDateCompareResult = castFirst.getCastDate().compareTo(castSecond.getCastDate());
                if (castDateCompareResult != 0) {
                    return castDateCompareResult;
                }

                int castShiftCompareResult = ObjectUtils.compare(castFirst.getShift(), castSecond.getShift());
                if (castShiftCompareResult != 0) {
                    return castShiftCompareResult;
                }

                return ObjectUtils.compare(castFirst.getCastNumber(), castSecond.getCastNumber());
            }
        });

        schema.setOperations(operations);
        schema.setCleanCollectorOperations(cleanCollectorOperations);
        schema.setPeriodicOperations(periodicOperations);
        schema.setSourceCastWrappers(castWrappers);

        for (Operation operation : schema.getInitOperations()) {
            operation.setActivationDate(startDate);
        }

        operations.addAll(schema.getInitOperations());

        final long startTime = System.currentTimeMillis();

        while (!operations.isEmpty()) {
            operations.poll().activate();
        }

        LOGGER.debug("Casting time: " + (System.currentTimeMillis() - startTime));
    }

    @Nullable
    private Date stringToDate(String string) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return new Date(format.parse(string).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
