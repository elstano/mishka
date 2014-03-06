package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.*;

@Entity
@Customizer(OrderCustomizer.class)
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
    private Integer diameter;
    @Column(name = "WIDTH")
    private Integer width;
    @Column(name = "HEIGHT")
    private Integer height;
    @Column(name = "WEIGHT")
    private Integer weight;
    @Column(name = "LENGTH_MIN")
    private int lengthMin;
    @Column(name = "LENGTH_MAX")
    private int lengthMax;
    @Column(name = "SCHEME")
    private String scheme;
    @Column(name = "NUM_INGOTS_MIN")
    private Integer numIngotsMin;
    @Column(name = "NUM_INGOTS_MAX")
    private Integer numIngotsMax;
    @Column(name = "TONNAGE_MIN")
    private Integer tonnageMin;
    @Column(name = "TONNAGE_MAX")
    private Integer tonnageMax;

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

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
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

    public Integer getNumIngotsMin() {
        return numIngotsMin;
    }

    public void setNumIngotsMin(Integer numIngotsMin) {
        this.numIngotsMin = numIngotsMin;
    }

    public Integer getNumIngotsMax() {
        return numIngotsMax;
    }

    public void setNumIngotsMax(Integer numIngotsMax) {
        this.numIngotsMax = numIngotsMax;
    }

    public Integer getTonnageMin() {
        return tonnageMin;
    }

    public void setTonnageMin(Integer tonnageMin) {
        this.tonnageMin = tonnageMin;
    }

    public Integer getTonnageMax() {
        return tonnageMax;
    }

    public void setTonnageMax(Integer tonnageMax) {
        this.tonnageMax = tonnageMax;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}


