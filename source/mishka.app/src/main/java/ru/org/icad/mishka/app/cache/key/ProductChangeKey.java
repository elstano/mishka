package ru.org.icad.mishka.app.cache.key;

public class ProductChangeKey {

    private final int castingUnitId;
    private final int markId1;
    private final int markId2;

    public ProductChangeKey(int castingUnitId, int markId1, int markId2) {
        this.castingUnitId = castingUnitId;
        this.markId1 = markId1;
        this.markId2 = markId2;
    }

    public int getCastingUnitId() {
        return castingUnitId;
    }

    public int getMarkId1() {
        return markId1;
    }

    public int getMarkId2() {
        return markId2;
    }
}
