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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
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

        if (isNeedFlush()) {
            // empty
        }

        CastWrapper castWrapper = sourceCastWrappers.poll();

        int ladlePourTimeMaxHour = CastingUnitCache.getInstance().getLadlePourTimeMax(30);

        double ladleTonnageMax = CastHouseCache.getInstance().getLadleTonnageMax(2);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        int markId = castWrapper.getCast().getCustomerOrder().getProduct().getMark().getId();
        Query query = em.createNativeQuery("select * from PREPARE_TIME_CONST ptc where ptc.COLLE_ID = 49 and ptc.MARK_ID in (SELECT m.mark_id FROM MARK m where m.mark_id = " + markId + " UNION SELECT m.PARENT_MARK_ID FROM MARK m where m.mark_id = " + markId + ") and ROWNUM = 1", PrepareTimeConst.class);
        PrepareTimeConst prepareTimeConst = (PrepareTimeConst) query.getSingleResult();

        double durationTimeHour = prepareTimeConst.getDurationTime() / 60;

        double time = 0;
        try {
            time = durationTimeHour + ladlePourTimeMaxHour / ladleTonnageMax * CastUtil.getCobTonnage(castWrapper.getCast()) / 60;
        } catch (Exception e) {
            e.printStackTrace();
        }

        castWrapper.setPrepareTime((long) (time * 3600 * 1000));

        Operation operation = schema.getOperationMap().get(OperationName.CAST_CM);
        if (operation.getActivationDate() == null || (getActivationDate() != null && getActivationDate().compareTo(operation.getActivationDate()) == 1)) {
            operation.setActivationDate(new Date(getActivationDate().getTime() + (long) (time * 3600 * 1000)));
        }

        operation.setCastWrapper(castWrapper);

        operation.setActivationCount(operation.getActivationCount() - 1);

        if (operation.getActivationCount() == 0) {
            schema.getOperations().add(operation);

            return;
        }

        LOGGER.debug("Operation type: PrepareCollectorOperation");
    }

    private boolean isNeedFlush() {
        CastWrapper doneCastWrapper = schema.getResultCastWrappers().peek();
        if (doneCastWrapper == null) {
            //ToDo Добавить проверку предущего продукта из БД
            return false;
        }
//
//        CastWrapper castWrapper = schema.getSourceCastWrappers().peek();
//
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
//        EntityManager em = emf.createEntityManager();
//
//        Query query = em.createNativeQuery("select * FROM CU_PRODUCT_CHANGE cpc where cpc.CU_ID = 30 and cpc.MARK_ID_1 = "
//                + doneCastWrapper.getCast().getCustomerOrder().getProduct().getMark().getId() + " and cpc.MARK_ID_2 = "
//                + castWrapper.getCast().getCustomerOrder().getProduct().getMark().getId(), CastingUnitProductChange.class);
//
//        CastingUnitProductChange castingUnitProductChange = (CastingUnitProductChange) query.getSingleResult();
//
//        return castingUnitProductChange != null;

        return false;
    }
}