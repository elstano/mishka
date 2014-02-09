package ru.org.icad.mishka.app.process.casting.schema4.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.CastingSpeed;
import ru.org.icad.mishka.app.model.Form;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.util.CastUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CastCmOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastCmOperation.class);


    private Schema schema;

    public CastCmOperation(Schema schema, int activationMaxCount) {
        this.schema = schema;

        this.setActivationMaxCount(activationMaxCount);
        this.setActivationCount(activationMaxCount);
    }

    @Override
    public void activate() {
        final CastWrapper castWrapper = getCastWrapper();
        schema.getResultCastWrappers().add(castWrapper);

        long time = 0;
        if ("flush".equals(castWrapper.getCast().getCustomerOrder().getId())) {
            time = castWrapper.getFlushCastTime();
        } else {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();

            int markId = castWrapper.getCast().getCustomerOrder().getProduct().getMark().getId();
            Query query = em.createNativeQuery("SELECT * from CASTING_SPEED cs where cs.MOULD_ID = 32 and cs.MARK_ID in (SELECT m.mark_id FROM MARK m where m.mark_id = " + markId + " UNION SELECT m.PARENT_MARK_ID FROM MARK m where m.mark_id = 191) and ROWNUM = 1", CastingSpeed.class);
            CastingSpeed castingSpeed = (CastingSpeed) query.getSingleResult();

            if (Form.INGOT == castWrapper.getCast().getCustomerOrder().getProduct().getForm().getId()) {
                try {
                    time = (long) (CastUtil.getTonnage(castWrapper.getCast()) / castingSpeed.getSpeed() * 60 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (Form.SLAB == castWrapper.getCast().getCustomerOrder().getProduct().getForm().getId() || Form.BILLET == castWrapper.getCast().getCustomerOrder().getProduct().getForm().getId()) {
                time = (long) (CastUtil.getLengthBlank(castWrapper.getCast()) / castingSpeed.getSpeed() * 60 * 1000);
            }
        }

        castWrapper.setCastTime(time);

        final Date startCastDate = getActivationDate();
        final Date endCastDate = new Date(startCastDate.getTime() + time);

        Operation cleanCollectorOperation = schema.getOperationMap().get(OperationName.CLEAN_COLLECTOR);
        cleanCollectorOperation.setActivationDate(endCastDate);
        Operation periodicCmOperation = schema.getOperationMap().get(OperationName.PERIODIC_CM);
        periodicCmOperation.setActivationDate(endCastDate);

        schema.getOperations().add(cleanCollectorOperation);
        schema.getOperations().add(periodicCmOperation);

        setActivationCount(getActivationMaxCount());

        LOGGER.debug("Result - Operation type: CastCmOperation, customer order id: " + castWrapper.getCast().getCustomerOrder().getId()
                + ", startDate: " + convertTimeToString(startCastDate.getTime() - castWrapper.getPrepareCollectorTime())
                + ", startCastDate: " + convertTimeToString(startCastDate.getTime())
                + ", endCastDate: " + convertTimeToString(endCastDate.getTime())
                + ", prepareTime: " + castWrapper.getPrepareCollectorTime() / 60 / 1000
                + ", castTime: " + castWrapper.getCastTime() / 60 / 1000
        );
    }

    private String convertTimeToString(long time) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(time);
    }
}
