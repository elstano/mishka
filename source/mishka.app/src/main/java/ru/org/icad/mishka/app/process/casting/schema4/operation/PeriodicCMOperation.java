package ru.org.icad.mishka.app.process.casting.schema4.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class PeriodicCMOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodicCMOperation.class);


    private Schema schema;

    public PeriodicCMOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
        if (isNeedPeriodic()) {
            // empty
        }

        Operation operation = schema.getOperationMap().get(OperationName.PREPARE_CM);
        operation.setActivationDate(getActivationDate());

        schema.getOperations().add(operation);

        LOGGER.debug("Operation type: PeriodicCMOperation");
    }

    private boolean isNeedPeriodic() {
        return false;
    }
}
