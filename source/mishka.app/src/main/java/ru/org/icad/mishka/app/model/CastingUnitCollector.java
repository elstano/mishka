package ru.org.icad.mishka.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CU_COLLECTOR")
public class CastingUnitCollector implements Serializable {

    private static final long serialVersionUID = 7605448809820966039L;

    @Id
    @Column(name = "COLLE_ID")
    private int id;
    @OneToOne
    private CastingUnit castingUnit;
    @Column(name = "MIXER_TONNAGE_MAX")
    private int mixerTonnageMax;
    @Column(name = "MIXER_REST_TONNAGE")
    private int mixerRestTonnage;
    @Column(name = "POUR_SPEED")
    private int pourSpeed;
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

    public int getMixerTonnageMax() {
        return mixerTonnageMax;
    }

    public void setMixerTonnageMax(int mixerTonnageMax) {
        this.mixerTonnageMax = mixerTonnageMax;
    }

    public int getMixerRestTonnage() {
        return mixerRestTonnage;
    }

    public void setMixerRestTonnage(int mixerRestTonnage) {
        this.mixerRestTonnage = mixerRestTonnage;
    }

    public int getPourSpeed() {
        return pourSpeed;
    }

    public void setPourSpeed(int pourSpeed) {
        this.pourSpeed = pourSpeed;
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
