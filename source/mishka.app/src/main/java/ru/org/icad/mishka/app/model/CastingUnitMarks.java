package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.*;

@Entity
@Customizer(OrderCustomizer.class)
@Table(name = TableName.CU_MARKS)
public class CastingUnitMarks {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CU_ID)
    private CastingUnit castingUnit;
    @OneToOne
    @JoinColumn(name = ColumnName.MARK_ID)
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

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
