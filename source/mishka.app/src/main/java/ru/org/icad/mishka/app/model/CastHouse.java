package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;

@Entity
@Table(name = TableName.CAST_HOUSE)
public class CastHouse {

    public CastHouse() {
    }

    public CastHouse(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "CH_ID")
    private int id;
    @OneToOne
    private Plant plant;
    @Column(name = "LADLE_TONNAGE_MAX")
    private int ladleTonnageMax;
    @Column(name = "BLANK_WEIGHT_MAX")
    private int blankWeightMax;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public int getLadleTonnageMax() {
        return ladleTonnageMax;
    }

    public void setLadleTonnageMax(int ladleTonnageMax) {
        this.ladleTonnageMax = ladleTonnageMax;
    }

    public int getBlankWeightMax() {
        return blankWeightMax;
    }

    public void setBlankWeightMax(int blankWeightMax) {
        this.blankWeightMax = blankWeightMax;
    }
}
