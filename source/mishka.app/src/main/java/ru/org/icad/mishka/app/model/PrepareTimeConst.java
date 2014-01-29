package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.PREPARE_TIME_CONST)
public class PrepareTimeConst {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private CastingUnitCollector castingUnitCollector;
    @OneToOne
    @JoinColumn(name = ColumnName.DISTR_ID)
    private CastingUnitDistributor castingUnitDistributor;
    @OneToOne
    @JoinColumn(name = ColumnName.CAST_MACH_ID)
    private CastingUnitCastingMachine castingUnitCastingMachine;
    @OneToOne
    @JoinColumn(name = ColumnName.MARK_ID)
    private Mark mark;
    @Column(name = "DURATION_TIME")
    private int durationTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastingUnitCollector getCastingUnitCollector() {
        return castingUnitCollector;
    }

    public void setCastingUnitCollector(CastingUnitCollector castingUnitCollector) {
        this.castingUnitCollector = castingUnitCollector;
    }

    public CastingUnitDistributor getCastingUnitDistributor() {
        return castingUnitDistributor;
    }

    public void setCastingUnitDistributor(CastingUnitDistributor castingUnitDistributor) {
        this.castingUnitDistributor = castingUnitDistributor;
    }

    public CastingUnitCastingMachine getCastingUnitCastingMachine() {
        return castingUnitCastingMachine;
    }

    public void setCastingUnitCastingMachine(CastingUnitCastingMachine castingUnitCastingMachine) {
        this.castingUnitCastingMachine = castingUnitCastingMachine;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
