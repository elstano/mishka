package ru.org.icad.mishka.app.process.casting.schema4.operation;

import ru.org.icad.mishka.app.cache.PrepareTimeConstCache;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class PrepareCollectorOperation extends Operation {

    private Schema schema;

    public PrepareCollectorOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public boolean activate() {
        CastWrapper castWrapper = schema.getDequeCastWrapper().poll();
        long time = getActivationDate().getTime() + PrepareTimeConstCache.getInstance().getPrepareTimeConstForCollector(49).getDurationTime();
        castWrapper.setPrepareTime(time);
        schema.getDequeCastWrapper().addFirst(castWrapper);
        schema.getOperationMap().get("castCm").activate();

        return true;
    }
}
