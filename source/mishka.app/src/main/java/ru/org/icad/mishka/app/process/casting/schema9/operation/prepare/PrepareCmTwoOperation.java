package ru.org.icad.mishka.app.process.casting.schema9.operation.prepare;

import org.apache.commons.lang3.ObjectUtils;
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

public class PrepareCmTwoOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareCmOneOperation.class);
    private static final int ONE_OPERATION_ID = 1;

    private final Schema schema;

    public PrepareCmTwoOperation(Schema schema, int activationMaxCount, int activationCount) {
        this.schema = schema;

        this.setActivationMaxCount(activationMaxCount);
        this.setActivationCount(activationCount);
    }

    @Override
    public void activate() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        Query query = em.createNativeQuery("select * from PREPARE_TIME_CONST ptc where ptc.CAST_MACH_ID = " + schema.getSchemaConfiguration().getCastingUnitCastingMachineIds()[1]
                + " and ROWNUM = 1", PrepareTimeConst.class);

        PrepareTimeConst prepareTimeConst = (PrepareTimeConst) query.getSingleResult();
        long durationTimeHour = prepareTimeConst.getDurationTime() * 60 * 1000;

        String nextOperationName = getNextId() == ONE_OPERATION_ID ? OperationName.CAST_CM_TWO_COLLECTOR_ONE : OperationName.CAST_CM_TWO_COLLECTOR_TWO;

        Operation operation = schema.getOperationMap().get(nextOperationName);
        if (ObjectUtils.compare(getActivationDate(), operation.getActivationDate()) == 1) {
            operation.setActivationDate(new Date(getActivationDate().getTime() + durationTimeHour));
        }

        operation.setActivationCount(operation.getActivationCount() - 1);

        if (operation.getActivationCount() == 0) {
            schema.getOperations().add(operation);
        }

        setActivationCount(getActivationMaxCount());

        LOGGER.debug("Result - castingUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                + ", Operation type: PrepareCmTwoOperation startDate: "
                + TimeUtil.convertTimeToString(getActivationDate().getTime()));
    }
}
