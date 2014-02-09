package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.sql.Date;

@NamedQueries({
        @NamedQuery(name = "Cast.findAll",
                query = "SELECT c FROM Cast c"),
        @NamedQuery(name = "Cast.getCastsForCastingUnitBetweenDate",
                query = "SELECT c FROM Cast c  WHERE c.castDate >= :startDate AND c.castDate < :endDate AND c.castingUnit.id = :castingUnitId")
})
@Entity
@Table(name = TableName.CAST)
public class Cast {

    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Id
    @OneToOne
    @JoinColumn(name = ColumnName.CU_ID)
    private CastingUnit castingUnit;
    @Id
    @Column(name = "CAST_DATE")
    private Date castDate;
    @Id
    @Column(name = "SHIFT")
    private int shift;
    @Id
    @Column(name = "CAST_NUMBER")
    private int castNumber;
    @Id
    @OneToOne
    @JoinColumn(name = ColumnName.ORDER_ID)
    private CustomerOrder customerOrder;
    @Column(name = "BLANK_COUNT")
    private int blankCount;
    @Column(name = "INGOT_IN_BLANK_COUNT")
    private int ingotInBlankCount;
    @Column(name = "INGOT_COUNT")
    private int ingotCount;

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

    public void setCastDate(Date castDate) {
        this.castDate = castDate;
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

    public int getBlankCount() {
        return blankCount;
    }

    public void setBlankCount(int blankCount) {
        this.blankCount = blankCount;
    }

    public int getIngotInBlankCount() {
        return ingotInBlankCount;
    }

    public void setIngotInBlankCount(int ingotInBlankCount) {
        this.ingotInBlankCount = ingotInBlankCount;
    }

    public int getIngotCount() {
        return ingotCount;
    }

    public void setIngotCount(int ingotCount) {
        this.ingotCount = ingotCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cast cast = (Cast) o;

        if (blankCount != cast.blankCount) return false;
        if (castNumber != cast.castNumber) return false;
        if (ingotInBlankCount != cast.ingotInBlankCount) return false;
        if (shift != cast.shift) return false;
        if (!castDate.equals(cast.castDate)) return false;
        if (!castingUnit.equals(cast.castingUnit)) return false;
        if (!customerOrder.equals(cast.customerOrder)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = castingUnit.hashCode();
        result = 31 * result + castDate.hashCode();
        result = 31 * result + shift;
        result = 31 * result + castNumber;
        result = 31 * result + customerOrder.hashCode();
        result = 31 * result + blankCount;
        result = 31 * result + ingotInBlankCount;
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
