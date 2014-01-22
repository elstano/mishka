package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = TableName.CAST_MACH_CLEAN)
public class CastingMachineClean {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CAST_MACH_ID)
    private CastingUnitCastingMachine castingUnitCastingMachine;
    @OneToOne
    @JoinColumn(name = ColumnName.OPERATION_ID)
    private CastingMachineOperation castingMachineOperation;
    @Column(name = "CAST_DATE")
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
