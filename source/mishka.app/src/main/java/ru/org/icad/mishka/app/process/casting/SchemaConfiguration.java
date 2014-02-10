package ru.org.icad.mishka.app.process.casting;

public class SchemaConfiguration {

    private final int castingUnitId;
    private final int castingUnitCollectorId;
    private final int castingUnitCastingMachineId;
    private final int mouldId;


    public SchemaConfiguration(int castingUnitId, int castingUnitCollectorId, int castingUnitCastingMachineId, int mouldId) {
        this.castingUnitId = castingUnitId;
        this.castingUnitCollectorId = castingUnitCollectorId;
        this.castingUnitCastingMachineId = castingUnitCastingMachineId;
        this.mouldId = mouldId;
    }

    public int getCastingUnitId() {
        return castingUnitId;
    }

    public int getCastingUnitCollectorId() {
        return castingUnitCollectorId;
    }

    public int getCastingUnitCastingMachineId() {
        return castingUnitCastingMachineId;
    }

    public int getMouldId() {
        return mouldId;
    }
}
