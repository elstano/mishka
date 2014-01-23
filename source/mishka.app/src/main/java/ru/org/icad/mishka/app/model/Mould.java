package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = TableName.MOULD)
public class Mould {

    @Id
    @Column(name = ColumnName.MOULD_ID)
    private int id;
    @Column(name = "CH_ID")
    private int castHouseId;
    @Column(name = "START_CAST_MACH_ID")
    private int startCastMachId;
    @Column(name = "FORM_ID")
    private int formId;
    @Column(name = "WIDTH")
    private int width;
    @Column(name = "HEIGHT")
    private int height;
    @Column(name = "DIAMETER")
    private int diameter;
    @Column(name = "RESOURCE_CURRENT")
    private int resource;
    @Column(name = "RESOURCE_MAX")
    private int resourceMax;
    @Column(name = "PREPARE_TIME")
    private int prepareTime;
    @Column(name = "NUM_BLANKS_MAX")
    private int numBlanksMax;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCastHouseId() {
        return castHouseId;
    }

    public void setCastHouseId(int castHouseId) {
        this.castHouseId = castHouseId;
    }

    public int getStartCastMachId() {
        return startCastMachId;
    }

    public void setStartCastMachId(int startCastMachId) {
        this.startCastMachId = startCastMachId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getResourceMax() {
        return resourceMax;
    }

    public void setResourceMax(int resourceMax) {
        this.resourceMax = resourceMax;
    }

    public int getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }

    public int getNumBlanksMax() {
        return numBlanksMax;
    }

    public void setNumBlanksMax(int numBlanksMax) {
        this.numBlanksMax = numBlanksMax;
    }
}
