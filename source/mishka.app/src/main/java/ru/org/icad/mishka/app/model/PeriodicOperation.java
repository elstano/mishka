package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.*;
import java.sql.Date;


@NamedQueries({
        @NamedQuery(name = "PeriodicOperation.findAll",
                query = "SELECT p FROM PeriodicOperation p"),
        @NamedQuery(name = "PeriodicOperation.findAllWhereCastingUnitCollectorIsNotNull",
                query = "SELECT p FROM PeriodicOperation p WHERE p.castingUnitCollector.id IS NOT NULL"),
        @NamedQuery(name = "PeriodicOperation.findCleanOperationForCollectorBetweenDate",
                query = "SELECT p FROM PeriodicOperation p WHERE p.castingUnitCollector.id IN :castingUnitCollectorIds AND p.operation.id = 3 AND p.operationDate >= :startDate AND p.operationDate < :endDate"),
        @NamedQuery(name = "PeriodicOperation.findPeriodicOperationForCastingMachineBetweenDate",
                query = "SELECT p FROM PeriodicOperation p WHERE p.castingUnitCastingMachine.id IN :castingUnitCastingMachineIds AND p.operationDate >= :startDate AND p.operationDate < :endDate")
})
@Entity
@Customizer(OrderCustomizer.class)
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
    private double durationTime;

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

    public double getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(double durationTime) {
        this.durationTime = durationTime;
    }
}
