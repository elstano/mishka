package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "HomogenizationLine.findAll",
                query = "SELECT hl FROM HomogenizationLine hl"),
        @NamedQuery(name = "HomogenizationLine.getHomogenizationLineByIdAndDiameter",
                query = "SELECT hl FROM HomogenizationLine hl WHERE hl.castingUnitHomogenCuttingLine.id = :castingUnitHomogenCuttingLineId and hl.diameter = :diameter")
})
@Entity
@Customizer(OrderCustomizer.class)
@Table(name = TableName.HOMOGENIZATION_LINE)
public class HomogenizationLine {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.HC_LINE_ID)
    private CastingUnitHomogenCuttingLine castingUnitHomogenCuttingLine;
    @Column(name = "DIAMETER")
    private int diameter;
    @Column(name = "LOAD_TIME")
    private int loadTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastingUnitHomogenCuttingLine getCastingUnitHomogenCuttingLine() {
        return castingUnitHomogenCuttingLine;
    }

    public void setCastingUnitHomogenCuttingLine(CastingUnitHomogenCuttingLine castingUnitHomogenCuttingLine) {
        this.castingUnitHomogenCuttingLine = castingUnitHomogenCuttingLine;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(int loadTime) {
        this.loadTime = loadTime;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}


