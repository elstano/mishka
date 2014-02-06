package ru.org.icad.mishka.app.process.casting.schema4.operation;

import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class PeriodicCMOperation extends Operation {

    private Schema schema;

    public PeriodicCMOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public boolean activate() {
        return false;
    }
}
