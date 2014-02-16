package ru.org.icad.mishka.app.process.casting.schema9.operation.prepare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class PrepareCmTwoOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareCmOneOperation.class);
    private static final int ONE_OPERATION_ID = 1;

    private final Schema schema;

    public PrepareCmTwoOperation(Schema schema, int activationMaxCount, int activationCount) {
        this.schema = schema;

        this.setActivationMaxCount(activationMaxCount);
        this.setActivationCount(activationCount);
    }

    @Override
    public void activate() {

    }
}
