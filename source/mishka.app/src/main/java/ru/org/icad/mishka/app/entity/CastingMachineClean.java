package ru.org.icad.mishka.app.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "CAST_MACH_CLEAN")
public class CastingMachineClean implements Serializable {

    private static final long serialVersionUID = 4493673024859036116L;

    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private CastingUnitCastingMachine castingUnitCastingMachine;
    @OneToOne
    private CastingMachineOperation castingMachineOperation;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "SHIFT")
    private int shift;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastingUnitCastingMachine getCastingUnitCastingMachine() {
        return castingUnitCastingMachine;
    }

    public void setCastingUnitCastingMachine(CastingUnitCastingMachine castingUnitCastingMachine) {
        this.castingUnitCastingMachine = castingUnitCastingMachine;
    }

    public CastingMachineOperation getCastingMachineOperation() {
        return castingMachineOperation;
    }

    public void setCastingMachineOperation(CastingMachineOperation castingMachineOperation) {
        this.castingMachineOperation = castingMachineOperation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }
}
