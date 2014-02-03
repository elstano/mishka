package ru.org.icad.mishka.app.process.casting.schema1_2.operation;

import ru.org.icad.mishka.app.cache.PrepareTimeConstCache;
import ru.org.icad.mishka.app.model.PrepareTimeConst;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;

import java.sql.Date;

public class PrepareCmOperation extends Operation {

    @Override
    public boolean activate() {
        CastWrapper castWrapper = getCastWrappers().poll();

        PrepareTimeConst prepareTimeConst
                = PrepareTimeConstCache.getPrepareTimeConstCache().getPrepareTimeConstMap().get(castWrapper.getCast().getId());

        castWrapper.getStartDate();
        return true;
    }
}
