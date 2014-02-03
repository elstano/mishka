package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = TableName.CAST)
public class Cast {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CU_ID)
    private CastingUnit castingUnit;
    @Column(name = "CAST_DATE")
    private Date castDate;
    @Column(name = "SHIFT")
    private int shift;
    @Column(name = "CAST_NUMBER")
    private int castNumber;
    @OneToOne
    @JoinColumn(name = ColumnName.ORDER_ID)
    private CustomerOrder customerOrder;
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

    public Date getCastDate() {
        return castDate;
    }

    public void setCastDate(Date date) {
        this.castDate = date;
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

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
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

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
