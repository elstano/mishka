package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class CleanCollectorOneOperation extends Operation {

    private final Schema schema;

    public CleanCollectorOneOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
    }
}
