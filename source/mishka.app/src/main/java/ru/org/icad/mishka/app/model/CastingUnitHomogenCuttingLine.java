package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Customizer(OrderCustomizer.class)
@Table(name = TableName.CU_HOMOGEN_CUTTING_LINE)
public class CastingUnitHomogenCuttingLine {

    public CastingUnitHomogenCuttingLine() {
    }

    public CastingUnitHomogenCuttingLine(int id) {
        this.id = id;
    }

    @Id
    @Column(name = ColumnName.HC_LINE_ID)
    private int id;
    @Column(name = "LENGTH_BLANK_MIN")
    private int lengthBlankMin;
    @Column(name = "LENGTH_BLANK_MAX")
    private int lengthBlankMax;

    public int getId() {
        return id;
    }

    public void setId(int homogenCuttingLineId) {
        this.id = homogenCuttingLineId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CastingUnitHomogenCuttingLine that = (CastingUnitHomogenCuttingLine) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
