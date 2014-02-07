package ru.org.icad.mishka.app.process.casting.schema4.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

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
        schema.getResultCastWrappers().add(getCastWrapper());

        Operation cleanCollectorOperation = schema.getOperationMap().get(OperationName.CLEAN_COLLECTOR);
        cleanCollectorOperation.setActivationDate(getActivationDate());

        Operation periodicCmOperation = schema.getOperationMap().get(OperationName.PERIODIC_CM);
        periodicCmOperation.setActivationDate(getActivationDate());

        schema.getOperations().add(cleanCollectorOperation);
        schema.getOperations().add(periodicCmOperation);

        setActivationCount(getActivationMaxCount());

        LOGGER.debug("Operation type: CastCmOperation, customer order id: " + getCastWrapper().getCast().getCustomerOrder().getId());
    }
}
