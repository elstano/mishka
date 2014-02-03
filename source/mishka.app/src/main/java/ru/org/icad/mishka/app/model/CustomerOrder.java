package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = TableName.CUSTOMER_ORDER)
public class CustomerOrder {

    @Id
    @Column(name = ColumnName.ORDER_ID)
    private String id;
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
    @JoinColumn(name = ColumnName.DESTINATION_ID)
    private TransportDestination transportDestination;
    @OneToOne
    @JoinColumn(name = ColumnName.PRODUCT_ID)
    private Product product;
    @Column(name = "DIAMETER")
    private Integer diameter;
    @Column(name = "WIDTH")
    private Integer width;
    @Column(name = "HEIGHT")
    private Integer height;
    @Column(name = "LENGTH")
    private Integer length;
    @Column(name = "WEIGHT")
    private Integer weight;
    @Column(name = "PREMIUM")
    private BigDecimal premium;
    @OneToOne
    @JoinColumn(name = ColumnName.CONTAINER_TYPE_ID)
    private ContainerType containerType;
    @Column(name = "TRANSPORT_CAPACITY")
    private Integer transportCapacity;
    @Column(name = "TIME_PRIORITY")
    private int timePriority;
    @Column(name = "TONNAGE_PRIORITY")
    private int tonnagePriority;
    @Column(name = "DIRECTIVE_DATE")
    private Date directiveDate;
    @Column(name = "DIRECTIVE_SHIFT")
    private Integer directiveShift;
    @Column(name = "CUSTOMER")
    private String customer;
    @Column(name = "PERIOD")
    private Date period;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerTypeId) {
        this.containerType = containerTypeId;
    }

    public Integer getTransportCapacity() {
        return transportCapacity;
    }

    public void setTransportCapacity(Integer transportCapacity) {
        this.transportCapacity = transportCapacity;
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

    public Integer getDirectiveShift() {
        return directiveShift;
    }

    public void setDirectiveShift(Integer directiveShift) {
        this.directiveShift = directiveShift;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
