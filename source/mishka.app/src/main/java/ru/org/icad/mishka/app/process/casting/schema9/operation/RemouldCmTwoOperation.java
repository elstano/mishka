package ru.org.icad.mishka.app.process.casting.schema9.operation;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.CastMachMoulds;
import ru.org.icad.mishka.app.model.CastingUnitCastingMachine;
import ru.org.icad.mishka.app.model.Form;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.util.TimeUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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

            TypedQuery<CastingUnitCastingMachine> castingUnitCastingMachineTypedQuery
                    = em.createNamedQuery("CastingUnitCastingMachine.findByPrimaryKey", CastingUnitCastingMachine.class);
            castingUnitCastingMachineTypedQuery.setParameter("id", schema.getSchemaConfiguration().getCastingUnitCastingMachineIds()[1]);
            CastingUnitCastingMachine castingUnitCastingMachine = castingUnitCastingMachineTypedQuery.getSingleResult();

            Operation operation = getNextId() == 1
                    ? schema.getOperationMap().get(OperationName.CAST_CM_TWO_COLLECTOR_ONE) :
                    schema.getOperationMap().get(OperationName.CAST_CM_TWO_COLLECTOR_TWO);

            final CastWrapper sourceCastWrapper = operation.getCastWrapper();
            final int sourceFormId = sourceCastWrapper.getCast().getCustomerOrder().getProduct().getForm().getId();

            int previousMouldId = schema.getSchemaConfiguration().getMouldIds()[1];
            for (CastMachMoulds castMachMoulds : castingUnitCastingMachine.getMoulds()) {
                if (castMachMoulds.getMould().getFormId() != sourceFormId) {
                    continue;
                }


                if (Form.INGOT == sourceFormId) {
                    if (ObjectUtils.equals(castMachMoulds.getMould().getWeight(), sourceCastWrapper.getCast().getCustomerOrder().getWeight())) {
                        schema.getSchemaConfiguration().setMouldIds(new int[]{schema.getSchemaConfiguration().getMouldIds()[0], castMachMoulds.getMould().getId(),  schema.getSchemaConfiguration().getMouldIds()[2]});

                        break;
                    }
                }
            }

            if ((previousMouldId == schema.getSchemaConfiguration().getMouldIds()[1])) {
                LOGGER.error("Error can't found mould for order id : " + sourceCastWrapper.getCast().getCustomerOrder().getId());
            }

            time += castingUnitCastingMachine.getRemouldTime() * 60 * 1000;

            LOGGER.debug("Result - castingUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                    + ", Operation type: RemouldCmOperation startDate: "
                    + TimeUtil.convertTimeToString(getActivationDate().getTime()) + ", remouldTime: " + time / 60 / 1000);
        }

        final long completeTime = getActivationDate().getTime() + time;

        final Operation operation = schema.getOperationMap().get(OperationName.PREPARE_CM_TWO);
        if (ObjectUtils.compare(getActivationDate(), operation.getActivationDate()) == 1) {
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
