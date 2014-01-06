package ru.org.icad.mishka.app.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CU_MARKS")
public class CastingUnitMarks implements Serializable {

    private static final long serialVersionUID = -505049715810229731L;

    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private CastingUnit castingUnit;
    @OneToOne
    private Mark mark;

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

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}
