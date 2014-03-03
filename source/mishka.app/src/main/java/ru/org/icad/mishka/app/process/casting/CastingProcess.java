package ru.org.icad.mishka.app.process.casting;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.cache.CastingUnitProductChangeCache;
import ru.org.icad.mishka.app.cache.key.ProductChangeKey;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;
import ru.org.icad.mishka.app.process.casting.schema5_6.Schema5_6;
import ru.org.icad.mishka.app.process.casting.schema9.Schema9;
import ru.org.icad.mishka.app.util.TimeUtil;

import javax.persistence.*;
import java.sql.Date;
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
            int castDateCompareResult = ObjectUtils.compare(castFirst.getCastDate(), castSecond.getCastDate());
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

    public CastingProcess(Schema schema) {
        this.schema = schema;
    }

    public Queue<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Queue<Operation> operations) {
        this.operations = operations;
    }

    public List<CastWrapper> castingProcess(List<Cast> casts) {
        Date startDate = TimeUtil.stringToDate(START_DATE);
        Date endDate = TimeUtil.stringToDate(END_DATE);

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
                return ObjectUtils.compare(o1.getOperationDate(), o2.getOperationDate());
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
                return ObjectUtils.compare(o1.getOperationDate(), o2.getOperationDate());
            }
        });

        Queue<PeriodicOperation> periodicOperations = Queues.newConcurrentLinkedQueue(periodicList);

        List<CastWrapper> castWrappers = Lists.newArrayList();
        for (Cast cast : casts) {
            if (cast.getCustomerOrder().getProduct() == null) {
                LOGGER.error("Can't get product for customer order: " + cast.getCustomerOrder());
                continue;
            }

            castWrappers.add(new CastWrapper(cast));
        }
        Collections.sort(castWrappers, CAST_WRAPPER_COMPARATOR);
        castWrappers = getCastWrappersWithGowk(castWrappers);


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

                CastingUnitProductChange castingUnitProductChange = getCastingUnitProductChange(schemaConfiguration, castWrapper, castWrapperAfter);

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
                flushCast.setCastingUnit(new CastingUnit(schemaConfiguration.getCastingUnitId()));
                flushCast.setCustomerOrder(new CustomerOrder("flush"));

                CastWrapper flushCastWrapper = new CastWrapper(flushCast);
                flushCastWrapper.setFlushCastTime(timeCast * 60 * 1000);
                flushCastWrapper.setFlushCollectorPrepareTime(castingUnitProductChange.getTimePrepareCollector() * 60 * 1000);
                flushCastWrapper.setFlushCmPrepareTime(castingUnitProductChange.getTimePrepareCastingMachine() * 60 * 1000);

                castWrappers.add(castWrappers.indexOf(castWrapper), flushCastWrapper);
            }
        }
        if (schema instanceof Schema5_6 || schema instanceof Schema9) {
            final List<CastWrapper> oneCastWrappers = Lists.newArrayList();
            final List<CastWrapper> twoCastWrappers = Lists.newArrayList();

            for (final CastWrapper castWrapper1 : castWrappers) {
                if (oneCastWrappers.size() == 0) {
                    oneCastWrappers.add(castWrapper1);

                    continue;
                }

                if (twoCastWrappers.size() == 0) {
                    twoCastWrappers.add(castWrapper1);

                    continue;
                }

                int compareResult = ObjectUtils.compare(oneCastWrappers.size(), twoCastWrappers.size());

                if (compareResult <= 0) {
                    CastWrapper oneCastWrapper = oneCastWrappers.get(oneCastWrappers.size() - 1);

                    CastingUnitProductChange castingUnitProductChange = getCastingUnitProductChange(schemaConfiguration, oneCastWrapper, castWrapper1);

                    if (castingUnitProductChange == null) {
                        oneCastWrappers.add(castWrapper1);

                        continue;
                    }

                    Integer timeCast = castingUnitProductChange.getTimeCast();
                    if (timeCast == null || timeCast == 0) {
                        Integer timePrepareCollector = castingUnitProductChange.getTimePrepareCollector();

                        if (timePrepareCollector == null || timePrepareCollector == 0) {
                            continue;
                        }

                        castWrapper1.setFlushCollectorPrepareTime(timePrepareCollector);

                        continue;
                    }

                    Cast flushCast = new Cast();
                    flushCast.setCastingUnit(new CastingUnit(schemaConfiguration.getCastingUnitId()));
                    flushCast.setCustomerOrder(new CustomerOrder("flush"));

                    CastWrapper flushCastWrapper = new CastWrapper(flushCast);
                    flushCastWrapper.setFlushCastTime(timeCast * 60 * 1000);
                    flushCastWrapper.setFlushCollectorPrepareTime(castingUnitProductChange.getTimePrepareCollector() * 60 * 1000);
                    flushCastWrapper.setFlushCmPrepareTime(castingUnitProductChange.getTimePrepareCastingMachine() * 60 * 1000);

                    oneCastWrappers.add(flushCastWrapper);
                    oneCastWrappers.add(castWrapper1);

                    continue;
                }

                CastWrapper twoCastWrapper = twoCastWrappers.get(twoCastWrappers.size() - 1);

                CastingUnitProductChange castingUnitProductChange = getCastingUnitProductChange(schemaConfiguration, twoCastWrapper, castWrapper1);

                if (castingUnitProductChange == null) {
                    twoCastWrappers.add(castWrapper1);
                    continue;
                }

                Integer timeCast = castingUnitProductChange.getTimeCast();
                if (timeCast == null || timeCast == 0) {
                    Integer timePrepareCollector = castingUnitProductChange.getTimePrepareCollector();

                    if (timePrepareCollector == null || timePrepareCollector == 0) {
                        continue;
                    }

                    castWrapper1.setFlushCollectorPrepareTime(timePrepareCollector);

                    continue;
                }

                Cast flushCast = new Cast();
                flushCast.setCastingUnit(new CastingUnit(schemaConfiguration.getCastingUnitId()));
                flushCast.setCustomerOrder(new CustomerOrder("flush"));

                CastWrapper flushCastWrapper = new CastWrapper(flushCast);
                flushCastWrapper.setFlushCastTime(timeCast * 60 * 1000);
                flushCastWrapper.setFlushCollectorPrepareTime(castingUnitProductChange.getTimePrepareCollector() * 60 * 1000);
                flushCastWrapper.setFlushCmPrepareTime(castingUnitProductChange.getTimePrepareCastingMachine() * 60 * 1000);

                twoCastWrappers.add(flushCastWrapper);
                twoCastWrappers.add(castWrapper1);
            }

            schema.getSourceOneCastWrappers().addAll(oneCastWrappers);
            schema.getSourceTwoCastWrappers().addAll(twoCastWrappers);
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

        return Lists.newArrayList(schema.getResultCastWrappers());
    }

    private CastingUnitProductChange getCastingUnitProductChange(SchemaConfiguration schemaConfiguration, CastWrapper castWrapper, CastWrapper castWrapperAfter) {
        int markId = 0;
        try {
            markId = getMarkId(castWrapper);
        } catch (Exception e) {
            LOGGER.error("Can't get mark id for customer order: " + castWrapper.getCast().getCustomerOrder(), e);

        }

        int markIdAfter = 0;
        try {
            markIdAfter = getMarkId(castWrapperAfter);
        } catch (Exception e) {
            LOGGER.error("Can't get mark id for customer order: " + castWrapperAfter.getCast().getCustomerOrder(), e);
        }

        return CastingUnitProductChangeCache.getInstance().getCastingUnitProduct(
                new ProductChangeKey(schemaConfiguration.getCastingUnitId(), markId, markIdAfter)
        );
    }

    private int getMarkId(CastWrapper castWrapper) {
        return castWrapper.getCast().getCustomerOrder().getProduct().getMark().getId();
    }

    private List<CastWrapper> getCastWrappersWithGowk(List<CastWrapper> castWrappers) {
        List<CastWrapper> castWrappersWithGowk = Lists.newArrayList();
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

                castWrappersWithGowk.add(mergeCastWrapper);

                i++;
            } else {
                castWrappersWithGowk.add(castWrapper);
            }
        }
        return castWrappersWithGowk;
    }
}
