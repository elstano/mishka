package ru.org.icad.mishka.app.dev;

import com.google.common.collect.Lists;
import ru.org.icad.mishka.app.process.casting.CastingProcess;
import ru.org.icad.mishka.app.process.casting.SchemaConfiguration;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;
import ru.org.icad.mishka.app.process.casting.schema5_6.Schema5_6;

import java.util.List;

public class CastingDev {

    private static final List<Integer> SAZ_CAST_UNIT = Lists.newArrayList(22, 24, 26, 28, 30, 31, 33);

    public void castingProcess() {
        int castHouseId = 2;

        int[] collectorIds30 = new int[]{49};
        int[] castingMachineIds30 = new int[]{46};
        int mouldId30 = 32;

        CastingProcess castingProcess30 = new CastingProcess(new Schema4(new SchemaConfiguration(castHouseId, 30, collectorIds30, castingMachineIds30, mouldId30)));
        castingProcess30.castingProcess();

        int[] collectorIds33 = new int[]{52};
        int[] castingMachineIds33 = new int[]{51};
        int mouldId33 = 34;
        CastingProcess castingProcess33 = new CastingProcess(new Schema4(new SchemaConfiguration(castHouseId, 33, collectorIds33, castingMachineIds33, mouldId33)));
        castingProcess33.castingProcess();

        int[] collectorIds22 = new int[]{40, 41};
        int[] castingMachineIds22 = new int[]{41};
        int mouldId22 = 1;
        CastingProcess castingProcess22 = new CastingProcess(new Schema5_6(new SchemaConfiguration(castHouseId, 22, collectorIds22, castingMachineIds22, mouldId22)));
        castingProcess22.castingProcess();

        int[] collectorIds24 = new int[]{42, 43};
        int[] castingMachineIds24 = new int[]{42};
        int mouldId24 = 1;
        CastingProcess castingProcess24 = new CastingProcess(new Schema5_6(new SchemaConfiguration(castHouseId, 24, collectorIds24, castingMachineIds24, mouldId24)));
        castingProcess24.castingProcess();

        int[] collectorIds26 = new int[]{44, 45};
        int[] castingMachineIds26 = new int[]{43};
        int mouldId26 = 23;
        CastingProcess castingProcess26 = new CastingProcess(new Schema5_6(new SchemaConfiguration(castHouseId, 26, collectorIds26, castingMachineIds26, mouldId26)));
        castingProcess26.castingProcess();

        int[] collectorIds28 = new int[]{46, 47};
        int[] castingMachineIds28 = new int[]{44};
        int mouldId28 = 23;
        CastingProcess castingProcess28 = new CastingProcess(new Schema5_6(new SchemaConfiguration(castHouseId, 28, collectorIds28, castingMachineIds28, mouldId28)));
        castingProcess28.castingProcess();
    }
}
