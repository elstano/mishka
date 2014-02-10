package ru.org.icad.mishka.app.dev;

import com.google.common.collect.Lists;
import ru.org.icad.mishka.app.process.casting.CastingProcess;
import ru.org.icad.mishka.app.process.casting.SchemaConfiguration;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;

import java.util.List;

public class CastingDev {

    private static final List<Integer> SAZ_CAST_UNIT = Lists.newArrayList(22, 24, 26, 28, 30, 31, 33);

    public void castingProcess() {
        CastingProcess castingProcess30 = new CastingProcess(new Schema4(new SchemaConfiguration(30, 49, 46, 32)));
        castingProcess30.castingProcess();

        CastingProcess castingProcess33 = new CastingProcess(new Schema4(new SchemaConfiguration(33, 52, 51, 34)));
        castingProcess33.castingProcess();
    }
}
