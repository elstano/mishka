package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

import java.sql.Date;

public class RemouldCmOperation extends Operation {

    private final Schema schema;

    public RemouldCmOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {

        Operation operation = schema.getOperationMap().get(OperationName.PREPARE_CM);
        if (operation.getActivationDate() == null || (getActivationDate() != null && getActivationDate().compareTo(operation.getActivationDate()) == 1)) {
            operation.setActivationDate(new Date(getActivationDate().getTime()));
        }

        operation.setActivationCount(operation.getActivationCount() - 1);

        if (operation.getActivationCount() == 0) {
           schema.getOperations().add(operation);
        }
    }
}
