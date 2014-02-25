package ru.org.icad.mishka.app.process.casting.schema9.operation.cast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.util.CastUtil;
import ru.org.icad.mishka.app.util.GowkCastUtil;
import ru.org.icad.mishka.app.util.TimeUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.util.List;
import java.util.Queue;

public class CastCmTwoCollectorTwoOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastCmTwoCollectorTwoOperation.class);

    private final Schema schema;

    public CastCmTwoCollectorTwoOperation(Schema schema, int activationMaxCount, int activationCount) {
        this.schema = schema;

        this.setActivationMaxCount(activationMaxCount);
        this.setActivationCount(activationCount);
    }

    @Override
    public void activate() {
        final CastWrapper castWrapper = getCastWrapper();
        schema.getResultCastWrappers().add(castWrapper);

        long time = 0;
        final Cast cast = castWrapper.getCast();
        final CustomerOrder customerOrder = cast.getCustomerOrder();
        if ("flush".equals(customerOrder.getId())) {
            time = castWrapper.getFlushCastTime();
        } else {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();

            final Product product = customerOrder.getProduct();
            int markId = product.getMark().getId();
            Query castingSpeedQuery = em.createNativeQuery("SELECT * FROM CASTING_SPEED cs WHERE cs.MOULD_ID = " + schema.getSchemaConfiguration().getMouldIds()[1]
                    + " and cs.MARK_ID in (SELECT m.mark_id FROM MARK m "
                    + " CONNECT BY PRIOR m.parent_mark_id = m.mark_id START WITH m.mark_id = " + markId
                    + ") and ROWNUM = 1", CastingSpeed.class);

            CastingSpeed castingSpeed = (CastingSpeed) castingSpeedQuery.getSingleResult();

            if (castWrapper.getBlankCountTwo() != null) {
                double tonnageBoth = 0;
                try {
                    tonnageBoth = GowkCastUtil.getTonnage(castWrapper);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                time = (long) (tonnageBoth / castingSpeed.getSpeed() * 60 * 1000);
            } else {
                final int formId = product.getForm().getId();
                if (Form.INGOT == formId) {
                    try {
                        time = (long) (CastUtil.getTonnage(cast) / castingSpeed.getSpeed() * 60 * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();
        Query castingUnitRepairsQuery = em.createNativeQuery("SELECT * from CU_REPAIR cur where cur.CAST_MACH_ID = "
                +  schema.getSchemaConfiguration().getCastingUnitCastingMachineIds()[1] , CastingUnitRepair.class);
        List castingUnitRepairs = castingUnitRepairsQuery.getResultList();
        if(castingUnitRepairs != null) {
            for(Object object : castingUnitRepairs) {
                CastingUnitRepair castingUnitRepair = (CastingUnitRepair )object;
                if(castingUnitRepair.getTimeEnd().before(getActivationDate())) {
                    continue;
                }

                if(castingUnitRepair.getTimeStart().after(new Date(getActivationDate().getTime() + time /2))) {
                    continue;
                }

                setActivationDate(castingUnitRepair.getTimeEnd());

                LOGGER.debug("Result - castingUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                        + ", Operation type: CastCmTwoCollectorTwoOperation Repair, customer order id: " + customerOrder.getId());
            }
        }

        castWrapper.setCastTime(time);

        final Date startCastDate = getActivationDate();
        final Date endCastDate = new Date(startCastDate.getTime() + time);

        Operation cleanCollectorTwoOperation = schema.getOperationMap().get(OperationName.CLEAN_COLLECTOR_TWO);
        cleanCollectorTwoOperation.setActivationDate(endCastDate);
        Operation periodicCmOperation = schema.getOperationMap().get(OperationName.PERIODIC_CM_TWO);
        periodicCmOperation.setActivationDate(endCastDate);

        Operation remouldCmOperation = schema.getOperationMap().get(OperationName.REMOULD_CM_TWO);
        remouldCmOperation.setActivationDate(endCastDate);
        remouldCmOperation.setNextId(1);

        final Queue<Operation> operations = schema.getOperations();
        operations.add(remouldCmOperation);
        operations.add(periodicCmOperation);
        operations.add(cleanCollectorTwoOperation);

        setActivationCount(getActivationMaxCount());

        LOGGER.debug("Result - castingUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                + ", Operation type: CastCmTwoCollectorTwoOperation, customer order id: " + customerOrder.getId()
                + ", startDate: " + TimeUtil.convertTimeToString(startCastDate.getTime() - castWrapper.getPrepareCollectorTime())
                + ", startCastDate: " + TimeUtil.convertTimeToString(startCastDate.getTime())
                + ", endCastDate: " + TimeUtil.convertTimeToString(endCastDate.getTime())
                + ", prepareTime: " + castWrapper.getPrepareCollectorTime() / 60 / 1000
                + ", castTime: " + castWrapper.getCastTime() / 60 / 1000
                + (isGowk(castWrapper) ? ", gowk: " + isGowk(castWrapper): "")
        );
    }

    private boolean isGowk(CastWrapper castWrapper) {
        return castWrapper.getLengthTwo() != null
                || castWrapper.getBlankCountTwo() != null
                || castWrapper.getIngotInBlankCountTwo() != null;
    }
}
