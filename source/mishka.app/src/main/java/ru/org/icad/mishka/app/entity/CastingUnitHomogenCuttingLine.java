package ru.org.icad.mishka.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "CU_HOMOGEN_CUTTING_LINE")
public class CastingUnitHomogenCuttingLine implements Serializable {

    private static final long serialVersionUID = -510917667272422440L;

    @Id
    @Column(name = "HC_LINE_ID")
    private int homogenCuttingLineId;
    @Column(name = "LENGTH_BLANK_MIN")
    private int lengthBlankMin;
    @Column(name = "LENGTH_BLANK_MAX")
    private int lengthBlankMax;

    public int getHomogenCuttingLineId() {
        return homogenCuttingLineId;
    }

    public void setHomogenCuttingLineId(int homogenCuttingLineId) {
        this.homogenCuttingLineId = homogenCuttingLineId;
    }

    public int getLengthBlankMin() {
        return lengthBlankMin;
    }

    public void setLengthBlankMin(int lengthBlankMin) {
        this.lengthBlankMin = lengthBlankMin;
    }

    public int getLengthBlankMax() {
        return lengthBlankMax;
    }

    public void setLengthBlankMax(int lengthBlankMax) {
        this.lengthBlankMax = lengthBlankMax;
    }
}
