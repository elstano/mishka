package ru.org.icad.mishka.app.process.casting.schema4.operation;

import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class CleanCollectorOperation extends Operation {

    private Schema schema;

    public CleanCollectorOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public boolean activate() {
        if (isNeedClean()) {

        }

        return true;
    }

    private boolean isNeedClean() {
        return false;
    }
}
