package ru.org.icad.mishka.app.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "CU_REPAIR")
public class CastingUnitRepair implements Serializable {

    private static final long serialVersionUID = 6055479591883907458L;

    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private CastingUnitCollector castingUnitCollector;
    @OneToOne
    private CastingUnitDistributor castingUnitDistributor;
    @OneToOne
    private CastingUnitCastingMachine castingUnitCastingMachine;
    @OneToOne
    private CastingUnitHomogenCuttingLine castingUnitHomogenCuttingLine;
    @Column(name = "TIME_START")
    private Date timeStart;
    @Column(name = "TIME_END")
    private Date timeEnd;

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

    public CastingUnitHomogenCuttingLine getCastingUnitHomogenCuttingLine() {
        return castingUnitHomogenCuttingLine;
    }

    public void setCastingUnitHomogenCuttingLine(CastingUnitHomogenCuttingLine castingUnitHomogenCuttingLine) {
        this.castingUnitHomogenCuttingLine = castingUnitHomogenCuttingLine;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }
}
