package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.CU_DISTRIBUTOR)
public class CastingUnitDistributor {

    @Id
    @Column(name = ColumnName.DISTR_ID)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CU_ID)
    private CastingUnit castingUnit;
    @Column(name = "NUM_CLEANS")
    private int numCleans;
    @Column(name = "CLEAN_TIME")
    private int cleanTime;

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

    public int getNumCleans() {
        return numCleans;
    }

    public void setNumCleans(int numCleans) {
        this.numCleans = numCleans;
    }

    public int getCleanTime() {
        return cleanTime;
    }

    public void setCleanTime(int cleanTime) {
        this.cleanTime = cleanTime;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
