package ru.org.icad.mishka.app.dev;

import com.google.common.collect.Lists;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.process.casting.CastingUnitWrapper;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;

import java.util.List;

public class CastingDev {

    private static final List<Integer> SAZ_CAST_UNIT = Lists.newArrayList(22, 24, 26, 28, 30, 31, 33);

    public void castingProcess() {
        CastingUnitWrapper castingUnitWrapper = new CastingUnitWrapper(new CastingUnit(30), new Schema4());
        castingUnitWrapper.castingProcess();
    }
}
