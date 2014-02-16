package ru.org.icad.mishka.app.process.casting.schema9.operation.cast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class CastCmOneCollectorOneOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastCmTwoCollectorTwoOperation.class);

    private final Schema schema;

    public CastCmOneCollectorOneOperation(Schema schema, int activationMaxCount, int activationCount) {
        this.schema = schema;

        this.setActivationMaxCount(activationMaxCount);
        this.setActivationCount(activationCount);
    }


    @Override
    public void activate() {

    }
}
