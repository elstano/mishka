package ru.org.icad.mishka.app.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CU_DISTRIBUTOR")
public class CastingUnitDistributor implements Serializable {

    private static final long serialVersionUID = 3077865487864970515L;

    @Id
    @Column(name = "DISTR_ID")
    private int id;
    @OneToOne
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
}
