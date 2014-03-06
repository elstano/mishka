package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "CastingUnitProductChange.findAll",
                query = "SELECT cupc FROM CastingUnitProductChange cupc"),
        @NamedQuery(name = "CastingUnitProductChange.findByPrimaryKey",
                query = "SELECT cupc FROM CastingUnitProductChange cupc WHERE cupc.id = :id"),
        @NamedQuery(name = "CastingUnitProductChange.findByCastingUnitIdAndMarks",
                query = "SELECT cupc FROM CastingUnitProductChange cupc WHERE cupc.castingUnit.id = :castingUnitId AND cupc.markId1 = :markId1 AND cupc.markId2 = :markId2")
})
@Entity
@Customizer(OrderCustomizer.class)
@Table(name = TableName.CU_PRODUCT_CHANGE)
public class CastingUnitProductChange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CU_ID)
    private CastingUnit castingUnit;
    @Column(name = "MARK_ID_1")
    private int markId1;
    @Column(name = "MARK_ID_2")
    private int markId2;
    @Column(name = "TONNAGE")
    private Integer tonnage;
    @Column(name = "CLEAN_NECESSITY")
    private String cleanNecessity;
    @Column(name = "TIME_PREPARE_C")
    private Integer timePrepareCollector;
    @Column(name = "TIME_POUR_C_D")
    private Integer timePourCollectorDistributor;
    @Column(name = "TIME_PREPARE_D")
    private Integer timePrepareDistributor;
    @Column(name = "TIME_PREPARE_CM")
    private Integer timePrepareCastingMachine;
    @Column(name = "TIME_CAST")
    private Integer timeCast;
    @Column(name = "TIME_POUR_C_C2")
    private Integer timePourCollectorTwo;
    @Column(name = "TIME_PREPARE_C2")
    private Integer timePrepareCollectorTwo;

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

    public int getMarkId1() {
        return markId1;
    }

    public void setMarkId1(int markId1) {
        this.markId1 = markId1;
    }

    public int getMarkId2() {
        return markId2;
    }

    public void setMarkId2(int markId2) {
        this.markId2 = markId2;
    }

    public Integer getTonnage() {
        return tonnage;
    }

    public void setTonnage(Integer tonnage) {
        this.tonnage = tonnage;
    }

    public String getCleanNecessity() {
        return cleanNecessity;
    }

    public void setCleanNecessity(String cleanNecessity) {
        this.cleanNecessity = cleanNecessity;
    }

    public Integer getTimePrepareCollector() {
        return timePrepareCollector;
    }

    public void setTimePrepareCollector(Integer timePrepareCollector) {
        this.timePrepareCollector = timePrepareCollector;
    }

    public Integer getTimePourCollectorDistributor() {
        return timePourCollectorDistributor;
    }

    public void setTimePourCollectorDistributor(Integer timePourCollectorDistributor) {
        this.timePourCollectorDistributor = timePourCollectorDistributor;
    }

    public Integer getTimePrepareDistributor() {
        return timePrepareDistributor;
    }

    public void setTimePrepareDistributor(Integer timePrepareDistributor) {
        this.timePrepareDistributor = timePrepareDistributor;
    }

    public Integer getTimePrepareCastingMachine() {
        return timePrepareCastingMachine;
    }

    public void setTimePrepareCastingMachine(Integer timePrepareCastingMachine) {
        this.timePrepareCastingMachine = timePrepareCastingMachine;
    }

    public Integer getTimeCast() {
        return timeCast;
    }

    public void setTimeCast(Integer timeCast) {
        this.timeCast = timeCast;
    }

    public Integer getTimePourCollectorTwo() {
        return timePourCollectorTwo;
    }

    public void setTimePourCollectorTwo(Integer timePourCollectorTwo) {
        this.timePourCollectorTwo = timePourCollectorTwo;
    }

    public Integer getTimePrepareCollectorTwo() {
        return timePrepareCollectorTwo;
    }

    public void setTimePrepareCollectorTwo(Integer timePrepareCollectorTwo) {
        this.timePrepareCollectorTwo = timePrepareCollectorTwo;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
