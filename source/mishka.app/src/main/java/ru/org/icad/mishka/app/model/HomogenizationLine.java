package ru.org.icad.mishka.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "HOMOGENIZATION_LINE")
public class HomogenizationLine implements Serializable {

    private static final long serialVersionUID = -1038131195448576635L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
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
}
