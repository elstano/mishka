package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = TableName.PRODUCT_COST)
public class ProductCost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.PLANT_ID)
    private Plant plant;
    @OneToOne
    @JoinColumn(name = ColumnName.MARK_ID)
    private Mark mark;
    @OneToOne
    @JoinColumn(name = ColumnName.FORM_ID)
    private Form form;
    @Column(name = "COST")
    private BigDecimal cost;
    @Column(name = "PERIOD")
    private Date period;

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

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
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

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date date) {
        this.period = date;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
