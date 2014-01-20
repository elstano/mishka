package ru.org.icad.mishka.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PRODUCED_PRODUCT")
public class ProducedProduct implements Serializable {

    private static final long serialVersionUID = -3015559876845171831L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private Plant plant;
    @OneToOne
    private Product product;
    @Column(name = "TONNAGE")
    private int tonnage;
    @Column(name = "LENGTH")
    private int length;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTonnage() {
        return tonnage;
    }

    public void setTonnage(int tonnage) {
        this.tonnage = tonnage;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
