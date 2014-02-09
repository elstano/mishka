package ru.org.icad.mishka.app.process.casting.schema4.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.cache.CastHouseCache;
import ru.org.icad.mishka.app.cache.CastingUnitCache;
import ru.org.icad.mishka.app.model.PrepareTimeConst;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.util.CastUtil;
import ru.org.icad.mishka.app.util.GowkUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Queue;

public class PrepareCollectorOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareCollectorOperation.class);


    private Schema schema;

    public PrepareCollectorOperation(Schema schema) {
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
        if ("flush".equals(castWrapper.getCast().getCustomerOrder().getId())) {
            time = castWrapper.getFlushCollectorPrepareTime();
        } else {

            if (castWrapper.getFlushCollectorPrepareTime() != 0) {
                time += castWrapper.getFlushCollectorPrepareTime();
            }

            int ladlePourTimeMaxHour = CastingUnitCache.getInstance().getLadlePourTimeMax(30);

            double ladleTonnageMax = CastHouseCache.getInstance().getLadleTonnageMax(2);

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();

            int markId = castWrapper.getCast().getCustomerOrder().getProduct().getMark().getId();
            Query query = em.createNativeQuery("select * from PREPARE_TIME_CONST ptc where ptc.COLLE_ID = 49 and ptc.MARK_ID in (SELECT m.mark_id FROM MARK m where m.mark_id = " + markId + " UNION SELECT m.PARENT_MARK_ID FROM MARK m where m.mark_id = " + markId + ") and ROWNUM = 1", PrepareTimeConst.class);
            PrepareTimeConst prepareTimeConst = (PrepareTimeConst) query.getSingleResult();

            long durationTimeHour = prepareTimeConst.getDurationTime() * 60 * 1000;

            if(castWrapper.getBlankCountTwo() != null) {
                try {
                    time += durationTimeHour + ladlePourTimeMaxHour / ladleTonnageMax * GowkUtil.getCobTonnage(castWrapper) * 60 * 1000;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

            try {
                time += durationTimeHour + ladlePourTimeMaxHour / ladleTonnageMax * CastUtil.getCobTonnage(castWrapper.getCast()) * 60 * 1000;
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }

        castWrapper.setPrepareCollectorTime(time);

        Operation operation = schema.getOperationMap().get(OperationName.CAST_CM);
        if (operation.getActivationDate() == null || (getActivationDate() != null && getActivationDate().compareTo(operation.getActivationDate()) == 1)) {
            operation.setActivationDate(new Date(getActivationDate().getTime() + time));
        }

        operation.setCastWrapper(castWrapper);

        operation.setActivationCount(operation.getActivationCount() - 1);

        if (operation.getActivationCount() == 0) {
            schema.getOperations().add(operation);

            return;
        }

        LOGGER.debug("Result - Operation type: PrepareCollectorOperation startDate: " + convertTimeToString(getActivationDate().getTime()));
    }

    private String convertTimeToString(long time) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(time);
    }
}