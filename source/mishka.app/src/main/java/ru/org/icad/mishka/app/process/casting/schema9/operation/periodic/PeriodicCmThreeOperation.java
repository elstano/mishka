package ru.org.icad.mishka.app.process.casting.schema9.operation.periodic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class PeriodicCmThreeOperation extends Operation{
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodicCmThreeOperation.class);

    private final Schema schema;

    public PeriodicCmThreeOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {

    }
}
