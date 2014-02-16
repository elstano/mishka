package ru.org.icad.mishka.app.process.casting.schema9.operation.periodic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class PeriodicCmTwoOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodicCmTwoOperation.class);

    private final Schema schema;

    public PeriodicCmTwoOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {

    }
}
