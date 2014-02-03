package ru.org.icad.mishka.app.process.casting.schema1_2.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.cache.PrepareTimeConstCache;
import ru.org.icad.mishka.app.model.PrepareTimeConst;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;

public class PrepareCmOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareCmOperation.class);


    @Override
    public boolean activate() {
        LOGGER.info("Activate: " + PrepareCmOperation.class.getName());
        CastWrapper castWrapper = getCastWrappers().poll();

        PrepareTimeConst prepareTimeConst
                = PrepareTimeConstCache.getPrepareTimeConstCache().getPrepareTimeConstMap().get(castWrapper.getCast().getId());

        castWrapper.getStartDate();
        LOGGER.info("Activate: " + PrepareCmOperation.class.getName() + " time: " + prepareTimeConst.getDurationTime());

        return true;
    }
}
