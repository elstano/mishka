package ru.org.icad.mishka.app.process.casting.schema9.operation.prepare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.PrepareTimeConst;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.util.TimeUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;

public class PrepareCmOneOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareCmOneOperation.class);
    private static final int ONE_OPERATION_ID = 1;

    private final Schema schema;

    public PrepareCmOneOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        Query query = em.createNativeQuery("select * from PREPARE_TIME_CONST ptc where ptc.CAST_MACH_ID = " + schema.getSchemaConfiguration().getCastingUnitCastingMachineIds()[0]
                + " and ROWNUM = 1", PrepareTimeConst.class);

        PrepareTimeConst prepareTimeConst = (PrepareTimeConst) query.getSingleResult();
        double durationTimeHour = prepareTimeConst.getDurationTime() / 60;


        Operation operation = schema.getOperationMap().get(OperationName.CAST_CM_ONE_COLLECTOR_ONE);
        if (operation.getActivationDate() == null || (getActivationDate() != null && getActivationDate().compareTo(operation.getActivationDate()) == 1)) {
            operation.setActivationDate(new Date(getActivationDate().getTime() + (long) (durationTimeHour * 3600 * 1000)));
        }

        operation.setActivationCount(operation.getActivationCount() - 1);

        if (operation.getActivationCount() == 0) {
            schema.getOperations().add(operation);
        }

        LOGGER.debug("Result - castingUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                + ", Operation type: PrepareCmOneOperation startDate: "
                + TimeUtil.convertTimeToString(getActivationDate().getTime()));
    }
}
