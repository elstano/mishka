package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "CastHouse.findAll",
                query = "SELECT c FROM CastHouse c"),
        @NamedQuery(name = "CastHouse.findByPrimaryKey",
                query = "SELECT c FROM CastHouse c WHERE c.id = :id")
})
@Entity
@Customizer(OrderCustomizer.class)
@Table(name = TableName.CAST_HOUSE)
public class CastHouse {

    @Id
    @Column(name = ColumnName.CH_ID)
    private int id;
    @Column(name = ColumnName.CH_NAME)
    private String castHouseName;
    @OneToOne
    @JoinColumn(name = ColumnName.PLANT_ID)
    private Plant plant;
    @Column(name = "LADLE_TONNAGE_MAX")
    private double ladleTonnageMax;
    @Column(name = "BLANK_WEIGHT_MAX")
    private int blankWeightMax;

    public CastHouse() {
    }

    public CastHouse(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCastHouseName() {
        return castHouseName;
    }

    public void setCastHouseName(String castHouseName) {
        this.castHouseName = castHouseName;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public double getLadleTonnageMax() {
        return ladleTonnageMax;
    }

    public void setLadleTonnageMax(double ladleTonnageMax) {
        this.ladleTonnageMax = ladleTonnageMax;
    }

    public int getBlankWeightMax() {
        return blankWeightMax;
    }

    public void setBlankWeightMax(int blankWeightMax) {
        this.blankWeightMax = blankWeightMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CastHouse castHouse = (CastHouse) o;

        return id == castHouse.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
