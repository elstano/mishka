package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@NamedQueries({
        @NamedQuery(name = "CastingUnit.findAll",
                query = "SELECT c FROM CastingUnit c"),
        @NamedQuery(name = "CastingUnit.findByPrimaryKey",
                query = "SELECT c FROM CastingUnit c WHERE c.id = :id")
})
@Entity
@Table(name = TableName.CASTING_UNIT)
public class CastingUnit {

    @Id
    @Column(name = ColumnName.CU_ID)
    private int id;
    @OneToOne
    private CastHouse castHouse;
    @Column(name = "SCHEME")
    private int scheme;
    @Column(name = "START_TIME")
    private Date startTime;
    @Column(name = "PREVIOUS_PRODUCT_ID")
    private Integer previousProductId;
    @Column(name = "LADLE_POUR_TIME_MAX")
    private int ladlePourTimeMax;
    @Column(name = "CLEAN_COST")
    private BigDecimal cleanCost;
    @Column(name = "FE_DECREASE")
    private double feDecrease;
    @Column(name = "SI_DECREASE")
    private double siDecrease;
    @Column(name = "CU_DECREASE")
    private double cuDecrease;
    @Column(name = "MG_DECREASE")
    private double mgDecrease;
    @Column(name = "MN_DECREASE")
    private double mnDecrease;
    @Column(name = "TI_DECREASE")
    private double tiDecrease;

    public CastingUnit() {
    }

    public CastingUnit(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastHouse getCastHouse() {
        return castHouse;
    }

    public void setCastHouse(CastHouse castHouse) {
        this.castHouse = castHouse;
    }

    public int getScheme() {
        return scheme;
    }

    public void setScheme(int scheme) {
        this.scheme = scheme;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getPreviousProductId() {
        return previousProductId;
    }

    public void setPreviousProductId(Integer previousProductId) {
        this.previousProductId = previousProductId;
    }

    public int getLadlePourTimeMax() {
        return ladlePourTimeMax;
    }

    public void setLadlePourTimeMax(int ladlePourTimeMax) {
        this.ladlePourTimeMax = ladlePourTimeMax;
    }

    public BigDecimal getCleanCost() {
        return cleanCost;
    }

    public void setCleanCost(BigDecimal cleanCost) {
        this.cleanCost = cleanCost;
    }

    public double getFeDecrease() {
        return feDecrease;
    }

    public void setFeDecrease(double feDecrease) {
        this.feDecrease = feDecrease;
    }

    public double getSiDecrease() {
        return siDecrease;
    }

    public void setSiDecrease(double siDecrease) {
        this.siDecrease = siDecrease;
    }

    public double getCuDecrease() {
        return cuDecrease;
    }

    public void setCuDecrease(double cuDecrease) {
        this.cuDecrease = cuDecrease;
    }

    public double getMgDecrease() {
        return mgDecrease;
    }

    public void setMgDecrease(double mgDecrease) {
        this.mgDecrease = mgDecrease;
    }

    public double getMnDecrease() {
        return mnDecrease;
    }

    public void setMnDecrease(double mnDecrease) {
        this.mnDecrease = mnDecrease;
    }

    public double getTiDecrease() {
        return tiDecrease;
    }

    public void setTiDecrease(double tiDecrease) {
        this.tiDecrease = tiDecrease;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
