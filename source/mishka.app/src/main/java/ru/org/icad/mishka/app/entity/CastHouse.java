package ru.org.icad.mishka.app.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CAST_HOUSE")
public class CastHouse implements Serializable {

    private static final long serialVersionUID = 8463781688757995540L;

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
