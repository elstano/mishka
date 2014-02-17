package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "CastingUnitCollector.findAll",
                query = "SELECT cuc FROM CastingUnitCollector cuc"),
        @NamedQuery(name = "CastingUnitCollector.findByPrimaryKey",
                query = "SELECT cuc FROM CastingUnitCollector cuc WHERE cuc.id = :id"),
        @NamedQuery(name = "CastingUnitCollector.findByCastingUnit",
                query = "select cuc from CastingUnitCollector cuc where cuc.castingUnit.id = :castUnitId")
})
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

    public CastingUnitCollector() {
    }

    public CastingUnitCollector(int id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CastingUnitCollector that = (CastingUnitCollector) o;

        if (id != that.id) return false;

        return true;
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
