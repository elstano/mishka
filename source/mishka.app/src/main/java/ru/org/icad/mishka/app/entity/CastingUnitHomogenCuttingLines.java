package ru.org.icad.mishka.app.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CU_CH_LINES")
public class CastingUnitHomogenCuttingLines implements Serializable {

    private static final long serialVersionUID = -7974075454531038361L;

    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private CastingUnit castingUnit;
    @OneToOne
    private CastingUnitHomogenCuttingLine castingUnitHomogenCuttingLine;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastingUnit getCastingUnit() {
        return castingUnit;
    }

    public void setCastingUnit(CastingUnit castingUnit) {
        this.castingUnit = castingUnit;
    }

    public CastingUnitHomogenCuttingLine getCastingUnitHomogenCuttingLine() {
        return castingUnitHomogenCuttingLine;
    }

    public void setCastingUnitHomogenCuttingLine(CastingUnitHomogenCuttingLine castingUnitHomogenCuttingLine) {
        this.castingUnitHomogenCuttingLine = castingUnitHomogenCuttingLine;
    }
}
