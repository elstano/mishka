package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = TableName.ORDER)
public class Order implements Serializable {

    private static final long serialVersionUID = 8668045087203626660L;

    @Id
    @Column(name = "ORDER_ID")
    private int id;
    @Column(name = "TONNAGE")
    private int tonnage;
    @Column(name = "TOLERANCE_MINUS")
    private int toleranceMinus;
    @Column(name = "TOLERANCE_PLUS")
    private int tolerancePlus;
    @Column(name = "DUE_DATE")
    private Date dueDate;
    @Column(name = "SHIPPING_DATE")
    private Date shippingDate;
    @OneToOne
    private TransportDestination transportDestination;
    @OneToOne
    private Product product;
    @Column(name = "LENGTH")
    private int length;
    @Column(name = "PREMIUM")
    private BigDecimal premium;
    @Column(name = "CONTAINER_TYPE_DIRECTIVE")
    private int containerTypeDirective;
    @Column(name = "TIME_PRIORITY")
    private int timePriority;
    @Column(name = "TONNAGE_PRIORITY")
    private int tonnagePriority;
    @Column(name = "DIRECTIVE_DATE")
    private Date directiveDate;
    @Column(name = "DIRECTIVE_SHIFT")
    private int directiveShift;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTonnage() {
        return tonnage;
    }

    public void setTonnage(int tonnage) {
        this.tonnage = tonnage;
    }

    public int getToleranceMinus() {
        return toleranceMinus;
    }

    public void setToleranceMinus(int toleranceMinus) {
        this.toleranceMinus = toleranceMinus;
    }

    public int getTolerancePlus() {
        return tolerancePlus;
    }

    public void setTolerancePlus(int tolerancePlus) {
        this.tolerancePlus = tolerancePlus;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public TransportDestination getTransportDestination() {
        return transportDestination;
    }

    public void setTransportDestination(TransportDestination transportDestination) {
        this.transportDestination = transportDestination;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public int getContainerTypeDirective() {
        return containerTypeDirective;
    }

    public void setContainerTypeDirective(int containerTypeDirective) {
        this.containerTypeDirective = containerTypeDirective;
    }

    public int getTimePriority() {
        return timePriority;
    }

    public void setTimePriority(int timePriority) {
        this.timePriority = timePriority;
    }

    public int getTonnagePriority() {
        return tonnagePriority;
    }

    public void setTonnagePriority(int tonnagePriority) {
        this.tonnagePriority = tonnagePriority;
    }

    public Date getDirectiveDate() {
        return directiveDate;
    }

    public void setDirectiveDate(Date directiveDate) {
        this.directiveDate = directiveDate;
    }

    public int getDirectiveShift() {
        return directiveShift;
    }

    public void setDirectiveShift(int directiveShift) {
        this.directiveShift = directiveShift;
    }
}
