package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

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

public class PrepareCmOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareCmOperation.class);
    private static final int ONE_OPERATION_ID = 1;

    private final Schema schema;

    public PrepareCmOperation(Schema schema, int activationMaxCount, int activationCount) {
        this.schema = schema;

        this.setActivationMaxCount(activationMaxCount);
        this.setActivationCount(activationCount);
    }

    @Override
    public void activate() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        Query query = em.createNativeQuery("select * from PREPARE_TIME_CONST ptc where ptc.CAST_MACH_ID = " + schema.getSchemaConfiguration().getCastingUnitCastingMachineIds()[0]
                + " and ROWNUM = 1", PrepareTimeConst.class);

        PrepareTimeConst prepareTimeConst = (PrepareTimeConst) query.getSingleResult();
        double durationTimeHour = prepareTimeConst.getDurationTime() / 60;

        String nextOperationName = getNextId() == ONE_OPERATION_ID ? OperationName.CAST_CM_COLLECTOR_ONE : OperationName.CAST_CM_COLLECTOR_TWO;

        Operation operationOne = schema.getOperationMap().get(nextOperationName);
        if (operationOne.getActivationDate() == null || (getActivationDate() != null && getActivationDate().compareTo(operationOne.getActivationDate()) == 1)) {
            operationOne.setActivationDate(new Date(getActivationDate().getTime() + (long) (durationTimeHour * 3600 * 1000)));
        }

        operationOne.setActivationCount(operationOne.getActivationCount() - 1);

        if (operationOne.getActivationCount() == 0) {
            schema.getOperations().add(operationOne);
        }

        setActivationCount(getActivationMaxCount());

        LOGGER.debug("Result - customUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                + ", Operation type: PrepareCmOperation startDate: "
                + TimeUtil.convertTimeToString(getActivationDate().getTime()));
    }
}
