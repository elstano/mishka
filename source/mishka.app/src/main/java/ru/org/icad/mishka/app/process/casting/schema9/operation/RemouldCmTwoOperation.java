package ru.org.icad.mishka.app.process.casting.schema9.operation;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.CastingUnitCastingMachine;
import ru.org.icad.mishka.app.model.Form;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.util.TimeUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;

public class RemouldCmTwoOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemouldCmTwoOperation.class);

    private final Schema schema;

    public RemouldCmTwoOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
        long time = 0;
        if (isRemouldNeed()) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();

            Query castingUnitCastingMachineQuery = em.createNativeQuery("SELECT * FROM CU_CASTING_MACHINE ccm WHERE ccm.CAST_MACH_ID = "
                    + schema.getSchemaConfiguration().getCastingUnitCastingMachineIds()[0],
                    CastingUnitCastingMachine.class);

            CastingUnitCastingMachine castingUnitCastingMachine = (CastingUnitCastingMachine) castingUnitCastingMachineQuery.getSingleResult();

            time += castingUnitCastingMachine.getRemouldTime() * 60 * 1000;

            LOGGER.debug("Result - castingUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                    + ", Operation type: RemouldCmOperation startDate: "
                    + TimeUtil.convertTimeToString(getActivationDate().getTime()) + ", remouldTime: " + time / 60 / 1000);
        }

        final long completeTime = getActivationDate().getTime() + time;

        final Operation operation = schema.getOperationMap().get(OperationName.PREPARE_CM_TWO);
        if (operation.getActivationDate() == null || (getActivationDate() != null && completeTime > operation.getActivationDate().getTime())) {
            operation.setActivationDate(new Date(completeTime));
        }

        operation.setActivationCount(operation.getActivationCount() - 1);
        operation.setNextId(getNextId());

        if (operation.getActivationCount() == 0) {
            schema.getOperations().add(operation);
        }
    }


    private boolean isRemouldNeed() {
        final CastWrapper resultCastWrapper = schema.getResultCastWrappers().peekLast();
        if (resultCastWrapper == null) {
            return false;
        }

        Operation operation = getNextId() == 1
                ? schema.getOperationMap().get(OperationName.CAST_CM_TWO_COLLECTOR_ONE) :
                schema.getOperationMap().get(OperationName.CAST_CM_TWO_COLLECTOR_TWO);

        final CastWrapper sourceCastWrapper = operation.getCastWrapper();

        if (sourceCastWrapper == null) {
            return false;
        }

        final int sourceFormId = sourceCastWrapper.getCast().getCustomerOrder().getProduct().getForm().getId();
        if (sourceFormId != resultCastWrapper.getCast().getCustomerOrder().getProduct().getForm().getId()) {
            LOGGER.error("Cast have different form!");
        }

        return Form.INGOT == sourceFormId &&
                !ObjectUtils.equals(
                        sourceCastWrapper.getCast().getCustomerOrder().getWeight(),
                        resultCastWrapper.getCast().getCustomerOrder().getWeight()
                );
    }
}
