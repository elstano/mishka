package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.Form;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

import java.sql.Date;
import java.util.Queue;

public class RemouldCmOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemouldCmOperation.class);

    private final Schema schema;

    public RemouldCmOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {

        if (isRemouldNeed()) {

        }

        final Operation operation = schema.getOperationMap().get(OperationName.PREPARE_CM);
        if (operation.getActivationDate() == null || (getActivationDate() != null && getActivationDate().compareTo(operation.getActivationDate()) == 1)) {
            operation.setActivationDate(new Date(getActivationDate().getTime()));
        }

        operation.setActivationCount(operation.getActivationCount() - 1);
        operation.setNextId(getNextId());

        if (operation.getActivationCount() == 0) {
            schema.getOperations().add(operation);
        }
    }


    private boolean isRemouldNeed() {
        final CastWrapper resultCastWrapper = schema.getResultCastWrappers().peek();
        if (resultCastWrapper == null) {
            return false;
        }

        final Queue<CastWrapper> currentSourceCastWrapper = getNextId() == 1
                ? schema.getSourceOneCastWrappers() : schema.getSourceTwoCastWrappers();
        final CastWrapper sourceCastWrapper = currentSourceCastWrapper.peek();
        if (sourceCastWrapper == null) {
            return false;
        }

        final int sourceFormId = sourceCastWrapper.getCast().getCustomerOrder().getProduct().getForm().getId();
        if (sourceFormId != resultCastWrapper.getCast().getCustomerOrder().getProduct().getForm().getId()) {
            LOGGER.error("Cast have different form!");
        }

        if (Form.INGOT == sourceFormId) {
            return !ObjectUtils.equals(
                    sourceCastWrapper.getCast().getCustomerOrder().getWeight(),
                    resultCastWrapper.getCast().getCustomerOrder().getWeight()
            );
        }

        if (Form.SLAB == sourceFormId) {
            return !ObjectUtils.equals(sourceCastWrapper.getCast().getCustomerOrder().getWidth(),
                    resultCastWrapper.getCast().getCustomerOrder().getWidth()) ||
                    !ObjectUtils.equals(sourceCastWrapper.getCast().getCustomerOrder().getHeight(),
                            resultCastWrapper.getCast().getCustomerOrder().getHeight());
        }


        return Form.BILLET == sourceFormId
                && !ObjectUtils.equals(
                sourceCastWrapper.getCast().getCustomerOrder().getDiameter(),
                resultCastWrapper.getCast().getCustomerOrder().getDiameter()
        );
    }
}
