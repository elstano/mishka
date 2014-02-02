package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;

@Entity
@Table(name = TableName.TRANSPORT_LOAD)
public class TransportLoad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.PLANT_ID)
    private Plant plant;
    @OneToOne
    @JoinColumn(name = ColumnName.CONTAINER_TYPE_ID)
    private ContainerType containerType;
    @OneToOne
    @JoinColumn(name = ColumnName.FORM_ID)
    private Form form;
    @Column(name = "DIAMETER")
    private int diameter;
    @Column(name = "WIDTH")
    private int width;
    @Column(name = "HEIGHT")
    private int height;
    @Column(name = "WEIGHT")
    private int weight;
    @Column(name = "LENGTH_MIN")
    private int lengthMin;
    @Column(name = "LENGTH_MAX")
    private int lengthMax;
    @Column(name = "SCHEME")
    private String scheme;
    @Column(name = "NUM_INGOTS_MIN")
    private int numIngotsMin;
    @Column(name = "NUM_INGOTS_MAX")
    private int numIngotsMax;
    @Column(name = "TONNAGE_MIN")
    private int tonnageMin;
    @Column(name = "TONNAGE_MAX")
    private int tonnageMax;

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

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLengthMin() {
        return lengthMin;
    }

    public void setLengthMin(int lengthMin) {
        this.lengthMin = lengthMin;
    }

    public int getLengthMax() {
        return lengthMax;
    }

    public void setLengthMax(int lengthMax) {
        this.lengthMax = lengthMax;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public int getNumIngotsMin() {
        return numIngotsMin;
    }

    public void setNumIngotsMin(int numIngotsMin) {
        this.numIngotsMin = numIngotsMin;
    }

    public int getNumIngotsMax() {
        return numIngotsMax;
    }

    public void setNumIngotsMax(int numIngotsMax) {
        this.numIngotsMax = numIngotsMax;
    }

    public int getTonnageMin() {
        return tonnageMin;
    }

    public void setTonnageMin(int tonnageMin) {
        this.tonnageMin = tonnageMin;
    }

    public int getTonnageMax() {
        return tonnageMax;
    }

    public void setTonnageMax(int tonnageMax) {
        this.tonnageMax = tonnageMax;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}


