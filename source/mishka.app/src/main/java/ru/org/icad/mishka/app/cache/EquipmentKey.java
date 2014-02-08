package ru.org.icad.mishka.app.cache;

public class EquipmentKey {

    private final int equipmentId;
    private final int markId;

    public EquipmentKey(int equipmentId, int markId) {
        this.equipmentId = equipmentId;
        this.markId = markId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentKey that = (EquipmentKey) o;

        return equipmentId == that.equipmentId
                && markId == that.markId;

    }

    @Override
    public int hashCode() {
        int result = equipmentId;
        result = 31 * result + markId;
        return result;
    }
}
