package ru.org.icad.mishka.app.process.casting.schema4.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.cache.PrepareTimeConstCache;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

import java.util.Date;

public class PrepareCollectorOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareCollectorOperation.class);


    private Schema schema;

    public PrepareCollectorOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public boolean activate() {

        CastWrapper castWrapper = schema.getDequeCastWrapper().pop();
        long time = getActivationDate().getTime() + PrepareTimeConstCache.getInstance().getPrepareTimeConstForCollector(49).getDurationTime();
        castWrapper.setPrepareTime(time);
        Operation operation = schema.getOperationMap().get("castCm");
        operation.setCastWrapper(castWrapper);

        if(operation.getActivationCount() == 0) {

        }


        LOGGER.debug("Operation type: PrepareCollectorOperation: " + castWrapper.getCastId() + " ,time: " + new Date(time));

        return true;
    }
}
