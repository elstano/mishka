package ru.org.icad.mishka.app.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "CAST")
public class Cast {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @OneToOne
    private CastingUnit castingUnit;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "SHIFT")
    private int shift;
    @Column(name = "CAST_NUMBER")
    private int castNumber;
    @OneToOne
    private Order order;
    @Column(name = "INGOT_COUNT")
    private int ingotCount;
    @Column(name = "INGOT_IN_BLANK_COUNT")
    private int ingotInBlankCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastingUnit getCastingUnit() {
        return castingUnit;
    }

    public void setCastingUnit(CastingUnit castingUnit) {
        this.castingUnit = castingUnit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getCastNumber() {
        return castNumber;
    }

    public void setCastNumber(int castNumber) {
        this.castNumber = castNumber;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getIngotCount() {
        return ingotCount;
    }

    public void setIngotCount(int ingotCount) {
        this.ingotCount = ingotCount;
    }

    public int getIngotInBlankCount() {
        return ingotInBlankCount;
    }

    public void setIngotInBlankCount(int ingotInBlankCount) {
        this.ingotInBlankCount = ingotInBlankCount;
    }
}
