package ru.org.icad.mishka.app.process.casting;

public class SchemaConfiguration {

    private final int castHouseId;
    private final int castingUnitId;
    private final int[] castingUnitCollectorIds;
    private final int[] castingUnitCastingMachineIds;
    private final int[] mouldIds;

    public SchemaConfiguration(
            int castHouseId,
            int castingUnitId,
            int[] castingUnitCollectorIds,
            int[] castingUnitCastingMachineIds,
            int[] mouldIds
    ) {
        this.castHouseId = castHouseId;
        this.castingUnitId = castingUnitId;
        this.castingUnitCollectorIds = castingUnitCollectorIds;
        this.castingUnitCastingMachineIds = castingUnitCastingMachineIds;
        this.mouldIds = mouldIds;
    }

    public int getCastHouseId() {
        return castHouseId;
    }

    public int getCastingUnitId() {
        return castingUnitId;
    }

    public int[] getCastingUnitCollectorIds() {
        return castingUnitCollectorIds;
    }

    public int[] getCastingUnitCastingMachineIds() {
        return castingUnitCastingMachineIds;
    }

    public int[] getMouldIds() {
        return mouldIds;
    }
}
