package ru.org.icad.mishka.app.process.casting.schema4.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class CleanCollectorOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CleanCollectorOperation.class);


    private Schema schema;

    public CleanCollectorOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
        if (isNeedClean()) {
            // empty
        }

        Operation operation = schema.getOperationMap().get(OperationName.PREPARE_COLLECTOR);
        operation.setActivationDate(getActivationDate());

        schema.getOperations().add(operation);

        LOGGER.debug("Operation type: CleanCollectorOperation");
    }

    private boolean isNeedClean() {
        return false;
    }
}
