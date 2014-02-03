package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.CUTTING_LINE)
public class CuttingLine {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.HC_LINE_ID)
    private CastingUnitHomogenCuttingLine castingUnitHomogenCuttingLine;
    @Column(name = "DIAMETER")
    private int diameter;
    @Column(name = "SPEED")
    private int speed;

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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
