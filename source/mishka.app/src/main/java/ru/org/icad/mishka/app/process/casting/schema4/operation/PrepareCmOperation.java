package ru.org.icad.mishka.app.process.casting.schema4.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.PrepareTimeConst;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PrepareCmOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareCmOperation.class);


    private Schema schema;

    public PrepareCmOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        Query query = em.createNativeQuery("select * from PREPARE_TIME_CONST ptc where ptc.CAST_MACH_ID = " + schema.getSchemaConfiguration().getCastingUnitCastingMachineId()
                + " and ROWNUM = 1", PrepareTimeConst.class);

        PrepareTimeConst prepareTimeConst = (PrepareTimeConst) query.getSingleResult();
        double durationTimeHour = prepareTimeConst.getDurationTime() / 60;


        Operation operation = schema.getOperationMap().get(OperationName.CAST_CM);
        if (operation.getActivationDate() == null || (getActivationDate() != null && getActivationDate().compareTo(operation.getActivationDate()) == 1)) {
            operation.setActivationDate(new Date(getActivationDate().getTime() + (long) (durationTimeHour * 3600 * 1000)));
        }

        operation.setActivationCount(operation.getActivationCount() - 1);

        if (operation.getActivationCount() == 0) {
            schema.getOperations().add(operation);

            return;
        }

        LOGGER.debug("Result - customUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                + ", Operation type: PrepareCmOperation startDate: "
                + convertTimeToString(getActivationDate().getTime()));
    }

    private String convertTimeToString(long time) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(time);
    }
}
