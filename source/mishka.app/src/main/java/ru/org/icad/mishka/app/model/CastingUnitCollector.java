package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;

@Entity
@Table(name = TableName.CU_COLLECTOR)
public class CastingUnitCollector {

    @Id
    @Column(name = ColumnName.COLLE_ID)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CU_ID)
    private CastingUnit castingUnit;
    @Column(name = "MIXER_TONNAGE_MAX")
    private int mixerTonnageMax;
    @Column(name = "MIXER_REST_TONNAGE")
    private int mixerRestTonnage;
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
