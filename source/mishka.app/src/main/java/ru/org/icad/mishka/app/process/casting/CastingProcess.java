package ru.org.icad.mishka.app.process.casting;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.cache.CastingUnitProductChangeCache;
import ru.org.icad.mishka.app.cache.key.ProductChangeKey;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;

import javax.persistence.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class CastingProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingProcess.class);

    private static final String START_DATE = "01/05/2013 00:00:00";
    private static final String END_DATE = "01/06/2013 00:00:00";
    private static final Comparator<CastWrapper> CAST_WRAPPER_COMPARATOR = new Comparator<CastWrapper>() {
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
    };

    private Queue<Operation> operations = Queues.newConcurrentLinkedQueue();

    private Schema schema;

    public CastingProcess() {
    }

    public CastingProcess(Schema schema) {
        this.schema = schema;
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

        SchemaConfiguration schemaConfiguration = schema.getSchemaConfiguration();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        TypedQuery<PeriodicOperation> cleanOperationQuery = em.createNamedQuery("PeriodicOperation.findCleanOperationForCollectorBetweenDate", PeriodicOperation.class);
        cleanOperationQuery.setParameter("startDate", startDate, TemporalType.DATE);
        cleanOperationQuery.setParameter("endDate", endDate, TemporalType.DATE);

        List<Integer> collectorIds = Ints.asList(schemaConfiguration.getCastingUnitCollectorIds());
        cleanOperationQuery.setParameter("castingUnitCollectorIds", collectorIds);

        List<PeriodicOperation> operationList = cleanOperationQuery.getResultList();
        Collections.sort(operationList, new Comparator<PeriodicOperation>() {
            @Override
            public int compare(PeriodicOperation o1, PeriodicOperation o2) {
                return o1.getOperationDate().compareTo(o2.getOperationDate());
            }
        });

        Queue<PeriodicOperation> cleanCollectorOperations = Queues.newConcurrentLinkedQueue(operationList);

        TypedQuery<PeriodicOperation> periodicOperationQuery = em.createNamedQuery("PeriodicOperation.findPeriodicOperationForCastingMachineBetweenDate", PeriodicOperation.class);
        periodicOperationQuery.setParameter("startDate", startDate, TemporalType.DATE);
        periodicOperationQuery.setParameter("endDate", endDate, TemporalType.DATE);

        List<Integer> castingMachineIds = Ints.asList(schemaConfiguration.getCastingUnitCastingMachineIds());
        periodicOperationQuery.setParameter("castingUnitCastingMachineIds", castingMachineIds);

        List<PeriodicOperation> periodicList = periodicOperationQuery.getResultList();
        Collections.sort(periodicList, new Comparator<PeriodicOperation>() {
            @Override
            public int compare(PeriodicOperation o1, PeriodicOperation o2) {
                return o1.getOperationDate().compareTo(o2.getOperationDate());
            }
        });

        Queue<PeriodicOperation> periodicOperations = Queues.newConcurrentLinkedQueue(periodicList);

        TypedQuery<Cast> typedQuery = em.createNamedQuery("Cast.getCastsForCastingUnitBetweenDate", Cast.class);
        typedQuery.setParameter("startDate", startDate, TemporalType.DATE);
        typedQuery.setParameter("endDate", endDate, TemporalType.DATE);
        typedQuery.setParameter("castingUnitId", schemaConfiguration.getCastingUnitId());

        List<Cast> casts = typedQuery.getResultList();

        List<CastWrapper> castWrappers = Lists.newArrayList();
        for (Cast cast : casts) {
            castWrappers.add(new CastWrapper(cast));
        }

        Collections.sort(castWrappers, CAST_WRAPPER_COMPARATOR);

        List<CastWrapper> castWrappersWithoutGowk = Lists.newArrayList();

        if (schema instanceof Schema4) {
            for (int i = 0; i < castWrappers.size(); i++) {
                CastWrapper castWrapper = castWrappers.get(i);

                if ((i + 1) >= castWrappers.size()) {
                    continue;
                }

                CastWrapper castWrapperAfter = castWrappers.get(i + 1);

                if (castWrapperAfter == null) {
                    break;
                }

                if (CAST_WRAPPER_COMPARATOR.compare(castWrapper, castWrapperAfter) == 0) {
                    Cast mergeCast = castWrapper.getCast();
                    CastWrapper mergeCastWrapper = new CastWrapper(mergeCast);
                    mergeCastWrapper.setBlankCountTwo(castWrapperAfter.getCast().getBlankCount());
                    mergeCastWrapper.setIngotInBlankCountTwo(castWrapperAfter.getCast().getIngotInBlankCount());
                    mergeCastWrapper.setLengthTwo(castWrapperAfter.getCast().getCustomerOrder().getLength());

                    castWrappersWithoutGowk.add(mergeCastWrapper);


                } else {
                    castWrappersWithoutGowk.add(castWrapper);
                }
            }

            castWrappers = castWrappersWithoutGowk;

            for (int i = 0; i < castWrappers.size(); i++) {

                CastWrapper castWrapper = castWrappers.get(i);

                if ((i + 1) >= castWrappers.size()) {
                    continue;
                }

                CastWrapper castWrapperAfter = castWrappers.get(i + 1);

                if (castWrapperAfter == null) {
                    break;
                }

                int markId = 0;
                try {
                    markId = castWrapper.getCast().getCustomerOrder().getProduct().getMark().getId();
                } catch (Exception e) {
                    LOGGER.error("Can't get mark id for customer order: " + castWrapper.getCast().getCustomerOrder(), e);

                }

                int markIdAfter = 0;
                try {
                    markIdAfter = castWrapperAfter.getCast().getCustomerOrder().getProduct().getMark().getId();
                } catch (Exception e) {
                    LOGGER.error("Can't get mark id for customer order: " + castWrapperAfter.getCast().getCustomerOrder(), e);
                }

                CastingUnitProductChange castingUnitProductChange = CastingUnitProductChangeCache.getInstance().getCastingUnitProduct(
                        new ProductChangeKey(schemaConfiguration.getCastingUnitId(), markId, markIdAfter)
                );

                if (castingUnitProductChange == null) {
                    continue;
                }

                Integer timeCast = castingUnitProductChange.getTimeCast();
                if (timeCast == null || timeCast == 0) {
                    Integer timePrepareCollector = castingUnitProductChange.getTimePrepareCollector();

                    if (timePrepareCollector == null || timePrepareCollector == 0) {
                        continue;
                    }

                    castWrapperAfter.setFlushCollectorPrepareTime(timePrepareCollector);

                    continue;
                }

                Cast flushCast = new Cast();
                flushCast.setCastingUnit(new CastingUnit(30));
                flushCast.setCustomerOrder(new CustomerOrder("flush"));

                CastWrapper flushCastWrapper = new CastWrapper(flushCast);
                flushCastWrapper.setFlushCastTime(timeCast * 60 * 1000);
                flushCastWrapper.setFlushCollectorPrepareTime(castingUnitProductChange.getTimePrepareCollector() * 60 * 1000);
                flushCastWrapper.setFlushCmPrepareTime(castingUnitProductChange.getTimePrepareCastingMachine() * 60 * 1000);

                castWrappers.add(castWrappers.indexOf(castWrapper), new CastWrapper(new Cast()));
            }

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
