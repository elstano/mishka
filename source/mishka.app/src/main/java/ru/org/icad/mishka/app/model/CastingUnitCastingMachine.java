package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;

@Entity
@Table(name = TableName.CU_CASTING_MACHINE)
public class CastingUnitCastingMachine {

    @Id
    @Column(name = ColumnName.CAST_MACH_ID)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CU_ID)
    private CastingUnit castingUnit;
    @Column(name = "REMOULD_TIME")
    private int remouldTime;
    @Column(name = "LENGHT_BLANK_MAX")
    private int lenghtBlankMax;
    @Column(name = "NUM_SNIF_CLEANS")
    private int numSnifCleans;
    @Column(name = "SNIF_CLEAN_TIME")
    private int snifCleanTime;
    @Column(name = "NUM_PDBF_CLEANS")
    private int numPdbfCleans;
    @Column(name = "PDBF_CLEAN_TIME")
    private int pdbfCleanTime;
    @Column(name = "NUM_CRYST_CHANGES")
    private int numCrystChanges;
    @Column(name = "CRYST_CHANGE_TIME")
    private int crystChangeTime;

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

    public int getRemouldTime() {
        return remouldTime;
    }

    public void setRemouldTime(int remouldTime) {
        this.remouldTime = remouldTime;
    }

    public int getLenghtBlankMax() {
        return lenghtBlankMax;
    }

    public void setLenghtBlankMax(int lenghtBlankMax) {
        this.lenghtBlankMax = lenghtBlankMax;
    }

    public int getNumSnifCleans() {
        return numSnifCleans;
    }

    public void setNumSnifCleans(int numSnifCleans) {
        this.numSnifCleans = numSnifCleans;
    }

    public int getSnifCleanTime() {
        return snifCleanTime;
    }

    public void setSnifCleanTime(int snifCleanTime) {
        this.snifCleanTime = snifCleanTime;
    }

    public int getNumPdbfCleans() {
        return numPdbfCleans;
    }

    public void setNumPdbfCleans(int numPdbfCleans) {
        this.numPdbfCleans = numPdbfCleans;
    }

    public int getPdbfCleanTime() {
        return pdbfCleanTime;
    }

    public void setPdbfCleanTime(int pdbfCleanTime) {
        this.pdbfCleanTime = pdbfCleanTime;
    }

    public int getNumCrystChanges() {
        return numCrystChanges;
    }

    public void setNumCrystChanges(int numCrystChanges) {
        this.numCrystChanges = numCrystChanges;
    }

    public int getCrystChangeTime() {
        return crystChangeTime;
    }

    public void setCrystChangeTime(int crystChangeTime) {
        this.crystChangeTime = crystChangeTime;
    }
}
