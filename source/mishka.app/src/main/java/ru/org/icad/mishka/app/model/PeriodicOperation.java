package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.sql.Date;


@NamedQueries({
        @NamedQuery(name = "PeriodicOperation.findAll",
                query = "SELECT p FROM PeriodicOperation p"),
        @NamedQuery(name = "PeriodicOperation.findAllWhereCastingUnitCollectorIsNotNull",
                query = "SELECT p FROM PeriodicOperation p WHERE p.castingUnitCollector.id IS NOT NULL")
})
@Entity
@Table(name = TableName.PERIODIC_OPERATION)
public class PeriodicOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.COLLE_ID)
    private CastingUnitCollector castingUnitCollector;
    @OneToOne
    @JoinColumn(name = ColumnName.DISTR_ID)
    private CastingUnitDistributor castingUnitDistributor;
    @OneToOne
    @JoinColumn(name = ColumnName.CAST_MACH_ID)
    private CastingUnitCastingMachine castingUnitCastingMachine;
    @OneToOne
    @JoinColumn(name = ColumnName.OPERATION_ID)
    private Operation operation;
    @Column(name = "OPERATION_DATE")
    private Date operationDate;
    @Column(name = "SHIFT")
    private int shift;
    @Column(name = "DURATION_TIME")
    private Date durationTime;

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

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public Date getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Date durationTime) {
        this.durationTime = durationTime;
    }
}
