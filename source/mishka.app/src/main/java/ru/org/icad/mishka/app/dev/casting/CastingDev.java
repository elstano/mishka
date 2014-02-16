package ru.org.icad.mishka.app.dev.casting;

import com.google.common.collect.ImmutableMap;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.process.casting.SchemaConfiguration;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;
import ru.org.icad.mishka.app.process.casting.schema5_6.Schema5_6;
import ru.org.icad.mishka.app.process.casting.schema9.Schema9;

import java.util.Map;

public class CastingDev {

    public static final Map<Integer, Schema> SCHEMA_MAP = ImmutableMap.<Integer, Schema>builder()
            .put(30, new Schema4(new SchemaConfiguration(2, 30, new int[]{49}, new int[]{46}, 32)))
            .put(33, new Schema4(new SchemaConfiguration(2, 33, new int[]{52}, new int[]{51}, 33)))
            .put(22, new Schema5_6(new SchemaConfiguration(2, 22, new int[]{40, 41}, new int[]{41}, 1)))
            .put(24, new Schema5_6(new SchemaConfiguration(2, 24, new int[]{42, 43}, new int[]{42}, 1)))
            .put(26, new Schema5_6(new SchemaConfiguration(2, 26, new int[]{44, 45}, new int[]{43}, 23)))
            .put(28, new Schema5_6(new SchemaConfiguration(2, 28, new int[]{46, 47}, new int[]{44}, 23)))
            .put(31, new Schema9(new SchemaConfiguration(2, 31, new int[]{50, 51}, new int[]{48, 50, 49}, 30)))
            .build();

    public void castingProcess() {
        CastingProcess castingProcess30 = new CastingProcess(SCHEMA_MAP.get(30));
        castingProcess30.castingProcess();

        CastingProcess castingProcess33 = new CastingProcess(SCHEMA_MAP.get(33));
        castingProcess33.castingProcess();

        CastingProcess castingProcess22 = new CastingProcess(SCHEMA_MAP.get(22));
        castingProcess22.castingProcess();

        CastingProcess castingProcess24 = new CastingProcess(SCHEMA_MAP.get(24));
        castingProcess24.castingProcess();

        CastingProcess castingProcess26 = new CastingProcess(SCHEMA_MAP.get(26));
        castingProcess26.castingProcess();

        CastingProcess castingProcess28 = new CastingProcess(SCHEMA_MAP.get(28));
        castingProcess28.castingProcess();

        CastingProcess castingProcess31 = new CastingProcess(SCHEMA_MAP.get(31));
        castingProcess31.castingProcess();
    }
}
