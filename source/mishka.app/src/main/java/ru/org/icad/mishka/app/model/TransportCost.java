package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = TableName.TRANSPORT_COST)
public class TransportCost {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.PLANT_ID)
    private Plant plant;
    @OneToOne
    @JoinColumn(name = ColumnName.DESTINATION_ID)
    private TransportDestination transportDestination;
    @OneToOne
    @JoinColumn(name = ColumnName.CONTAINER_TYPE_ID)
    private ContainerType containerType;
    @OneToOne
    @JoinColumn(name = ColumnName.FORM_ID)
    private Form form;
    @Column(name = "COST")
    private BigDecimal cost;

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

    public TransportDestination getTransportDestination() {
        return transportDestination;
    }

    public void setTransportDestination(TransportDestination transportDestination) {
        this.transportDestination = transportDestination;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
