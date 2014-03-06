package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.*;

//TODO:Revise width and height
@NamedQueries({
        @NamedQuery(name = "Mould.findAll",
                query = "SELECT m FROM Mould m"),
        @NamedQuery(name = "Mould.getMouldForSlab",
                query = "SELECT m FROM Mould m, CastMachMoulds cmm, CastingUnitCastingMachine cucm" +
                        " WHERE cucm.castingUnit.id = :castingUnitId" +
                        "   AND cmm.castingUnitCastingMachine.id = cucm.id" +
                        "   AND m.id = cmm.mould.id" +
                        "   AND m.formId = :formId" +
                        "   AND m.width = :height" +
                        "   AND m.height = :width")
})
@Entity
@Customizer(OrderCustomizer.class)
@Table(name = TableName.MOULD)
public class Mould {

    @Id
    @Column(name = ColumnName.MOULD_ID)
    private int id;
    @Column(name = "CH_ID")
    private Integer castHouseId;
    @Column(name = "START_CAST_MACH_ID")
    private Integer startCastMachId;
    @Column(name = "FORM_ID")
    private int formId;
    @Column(name = "WIDTH")
    private Integer width;
    @Column(name = "HEIGHT")
    private Integer height;
    @Column(name = "DIAMETER")
    private Integer diameter;
    @Column(name = "RESOURCE_CURRENT")
    private Integer resource;
    @Column(name = "RESOURCE_MAX")
    private Integer resourceMax;
    @Column(name = "PREPARE_TIME")
    private Integer prepareTime;
    @Column(name = "NUM_BLANKS_MAX")
    private Integer numBlanksMax;
    @Column(name = "WEIGHT")
    private Integer weight;

    public Mould() {
    }

    public Mould(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCastHouseId() {
        return castHouseId;
    }

    public void setCastHouseId(Integer castHouseId) {
        this.castHouseId = castHouseId;
    }

    public Integer getStartCastMachId() {
        return startCastMachId;
    }

    public void setStartCastMachId(Integer startCastMachId) {
        this.startCastMachId = startCastMachId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public Integer getResourceMax() {
        return resourceMax;
    }

    public void setResourceMax(Integer resourceMax) {
        this.resourceMax = resourceMax;
    }

    public Integer getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(Integer prepareTime) {
        this.prepareTime = prepareTime;
    }

    public Integer getNumBlanksMax() {
        return numBlanksMax;
    }

    public void setNumBlanksMax(Integer numBlanksMax) {
        this.numBlanksMax = numBlanksMax;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mould mould = (Mould) o;

        if (id != mould.id) return false;

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
