package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.cache.CastHouseCache;
import ru.org.icad.mishka.app.cache.CastingUnitCache;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.PrepareTimeConst;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.process.casting.SchemaConfiguration;
import ru.org.icad.mishka.app.util.CastUtil;
import ru.org.icad.mishka.app.util.GowkCastUtil;
import ru.org.icad.mishka.app.util.TimeUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Queue;

public class PrepareCollectorOneOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CleanCollectorOneOperation.class);

    private final Schema schema;

    public PrepareCollectorOneOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
        final Queue<CastWrapper> sourceCastWrappers = schema.getSourceCastWrappers();
        if (sourceCastWrappers.isEmpty()) {
            return;
        }

        CastWrapper castWrapper = sourceCastWrappers.poll();

        long time = 0;
        final Cast cast = castWrapper.getCast();
        final SchemaConfiguration schemaConfiguration = schema.getSchemaConfiguration();
        if ("flush".equals(cast.getCustomerOrder().getId())) {
            time = castWrapper.getFlushCollectorPrepareTime();
        } else {

            if (castWrapper.getFlushCollectorPrepareTime() != 0) {
                time += castWrapper.getFlushCollectorPrepareTime();
            }

            int ladlePourTimeMaxHour = CastingUnitCache.getInstance().getLadlePourTimeMax(schemaConfiguration.getCastingUnitId());

            double ladleTonnageMax = CastHouseCache.getInstance().getLadleTonnageMax(schemaConfiguration.getCastHouseId());

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();

            int markId = cast.getCustomerOrder().getProduct().getMark().getId();
            Query prepareTimeConstQuery = em.createNativeQuery("select * from PREPARE_TIME_CONST ptc where ptc.COLLE_ID = " + schemaConfiguration.getCastingUnitCollectorIds()[0]
                    + " and ptc.MARK_ID in (SELECT m.mark_id FROM MARK m where m.mark_id = " + markId
                    + " UNION SELECT m.PARENT_MARK_ID FROM MARK m where m.mark_id = " + markId + ") and ROWNUM = 1", PrepareTimeConst.class);

            PrepareTimeConst prepareTimeConst = (PrepareTimeConst) prepareTimeConstQuery.getSingleResult();

            long durationTimeHour = prepareTimeConst.getDurationTime() * 60 * 1000;

            if (castWrapper.getBlankCountTwo() != null) {
                try {
                    time += durationTimeHour + ladlePourTimeMaxHour * Precision.round(GowkCastUtil.getCobTonnage(castWrapper) / ladleTonnageMax, BigDecimal.ROUND_UP) * 60 * 1000;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                try {
                    time += durationTimeHour + ladlePourTimeMaxHour * Precision.round(CastUtil.getCobTonnage(cast) / ladleTonnageMax, BigDecimal.ROUND_UP) * 60 * 1000;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        castWrapper.setPrepareCollectorTime(time);

        Operation operation = schema.getOperationMap().get(OperationName.CAST_CM_COLLECTOR_ONE);
        if (operation.getActivationDate() == null || (getActivationDate() != null && getActivationDate().compareTo(operation.getActivationDate()) == 1)) {
            operation.setActivationDate(new Date(getActivationDate().getTime() + time));
        }

        operation.setCastWrapper(castWrapper);

        operation.setActivationCount(operation.getActivationCount() - 1);

        if (operation.getActivationCount() == 0) {
            schema.getOperations().add(operation);
        }

        LOGGER.debug("Result - customUnitId: " + schemaConfiguration.getCastingUnitId()
                + ", Operation type: PrepareCollectorOneOperation startDate: "
                + TimeUtil.convertTimeToString(getActivationDate().getTime()));
    }
}
