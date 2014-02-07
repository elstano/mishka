package ru.org.icad.mishka.app.process.casting.schema4.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class PrepareCmOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareCmOperation.class);


    private Schema schema;

    public PrepareCmOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
        Operation operation = schema.getOperationMap().get(OperationName.CAST_CM);
        if (operation.getActivationDate() == null || (getActivationDate() != null && getActivationDate().compareTo(operation.getActivationDate()) == 1)) {
            operation.setActivationDate(getActivationDate());
        }

        operation.setActivationCount(operation.getActivationCount() - 1);

        if (operation.getActivationCount() == 0) {
            schema.getOperations().add(operation);

            return;
        }

        LOGGER.debug("Operation type: PrepareCmOperation");
    }
}
