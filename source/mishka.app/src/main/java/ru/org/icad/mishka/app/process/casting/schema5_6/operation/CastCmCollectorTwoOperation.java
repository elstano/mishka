package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

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
import java.util.Queue;

public class CastCmCollectorTwoOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastCmCollectorTwoOperation.class);


    private final Schema schema;

    public CastCmCollectorTwoOperation(Schema schema, int activationMaxCount, int activationCount) {
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
            Query castingSpeedQuery = em.createNativeQuery("SELECT * from CASTING_SPEED cs where cs.MOULD_ID = " + schema.getSchemaConfiguration().getMouldId()
                    + " and cs.MARK_ID in (SELECT m.mark_id FROM MARK m where m.mark_id = " + markId
                    + " UNION SELECT m.PARENT_MARK_ID FROM MARK m where m.mark_id = " + markId
                    + ") and ROWNUM = 1", CastingSpeed.class);

            CastingSpeed castingSpeed = (CastingSpeed) castingSpeedQuery.getSingleResult();

            if (castWrapper.getBlankCountTwo() != null) {
                double lengthBlank = 0;
                try {
                    lengthBlank = GowkCastUtil.getLengthBlank(castWrapper);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                time = (long) (lengthBlank / castingSpeed.getSpeed() * 60 * 1000);
            } else {
                final int formId = product.getForm().getId();
                if (Form.SLAB == formId || Form.BILLET == formId) {
                    time = (long) (CastUtil.getLengthBlank(cast) / castingSpeed.getSpeed() * 60 * 1000);
                }
            }
        }

        castWrapper.setCastTime(time);

        final Date startCastDate = getActivationDate();
        final Date endCastDate = new Date(startCastDate.getTime() + time);

        Operation cleanCollectorTwoOperation = schema.getOperationMap().get(OperationName.CLEAN_COLLECTOR_TWO);
        cleanCollectorTwoOperation.setActivationDate(endCastDate);
        Operation periodicCmOperation = schema.getOperationMap().get(OperationName.PERIODIC_CM);
        periodicCmOperation.setActivationDate(endCastDate);

        Operation remouldCmOperation = schema.getOperationMap().get(OperationName.REMOULD_CM);
        remouldCmOperation.setActivationDate(endCastDate);
        remouldCmOperation.setNextId(1);

        final Queue<Operation> operations = schema.getOperations();
        operations.add(cleanCollectorTwoOperation);
        operations.add(periodicCmOperation);
        operations.add(remouldCmOperation);

        setActivationCount(getActivationMaxCount());

        LOGGER.debug("Result - customUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                + ", Operation type: CastCmCollectorTwoOperation, customer order id: " + customerOrder.getId()
                + ", startDate: " + TimeUtil.convertTimeToString(startCastDate.getTime() - castWrapper.getPrepareCollectorTime())
                + ", startCastDate: " + TimeUtil.convertTimeToString(startCastDate.getTime())
                + ", endCastDate: " + TimeUtil.convertTimeToString(endCastDate.getTime())
                + ", prepareTime: " + castWrapper.getPrepareCollectorTime() / 60 / 1000
                + ", castTime: " + castWrapper.getCastTime() / 60 / 1000
        );
    }
}
