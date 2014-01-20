package ru.org.icad.mishka.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "PLANT_CONTAINERS")
public class PlantContainers implements Serializable {

    private static final long serialVersionUID = -710862832246962569L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private ContainerType containerType;
    @OneToOne
    private Plant plant;
    @Column(name = "NUM_CONTAINERS")
    private int numContainers;
    @Column(name = "DATE")
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public int getNumContainers() {
        return numContainers;
    }

    public void setNumContainers(int numContainers) {
        this.numContainers = numContainers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
