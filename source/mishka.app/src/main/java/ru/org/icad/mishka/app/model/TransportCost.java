package ru.org.icad.mishka.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TRANSPORT_COST")
public class TransportCost implements Serializable {

    private static final long serialVersionUID = 4415117277323940256L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private Plant plant;
    @OneToOne
    private TransportDestination transportDestination;
    @OneToOne
    private ContainerType containerType;
    @OneToOne
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
