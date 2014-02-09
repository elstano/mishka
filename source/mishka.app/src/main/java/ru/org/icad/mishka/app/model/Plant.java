package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = TableName.PLANT)
@Customizer(OrderCustomizer.class)
public class Plant {

    @Id
    @Column(name = ColumnName.PLANT_ID)
    private int id;
    @Column(name = "PLANT_NAME")
    private String name;
    @Column(name = "PREMIUM_A7")
    private BigDecimal premiumA7;
    @Column(name = "ADD_COST")
    private BigDecimal addCost;
    @Column(name = "MELT_LOSS")
    private BigDecimal meltLoss;

    public Plant() {
    }

    public Plant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPremiumA7() {
        return premiumA7;
    }

    public void setPremiumA7(BigDecimal premiumA7) {
        this.premiumA7 = premiumA7;
    }

    public BigDecimal getAddCost() {
        return addCost;
    }

    public void setAddCost(BigDecimal clipAddCost) {
        this.addCost = clipAddCost;
    }

    public BigDecimal getMeltLoss() {
        return meltLoss;
    }

    public void setMeltLoss(BigDecimal clipMeltLoss) {
        this.meltLoss = clipMeltLoss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plant plant = (Plant) o;

        if (id != plant.id) return false;

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
