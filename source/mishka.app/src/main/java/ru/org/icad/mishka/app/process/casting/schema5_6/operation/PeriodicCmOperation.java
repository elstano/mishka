package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class PeriodicCmOperation extends Operation {

    private final Schema schema;

    public PeriodicCmOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
//        Operation castCmOneOperation = new CastCmOneOperation();
//        this.getSchema().getOperations().add(castCmOneOperation);

    }
}
