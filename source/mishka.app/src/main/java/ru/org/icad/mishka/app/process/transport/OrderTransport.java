package ru.org.icad.mishka.app.process.transport;

public class OrderTransport {

    private int orderTonnage;
    private int orderContainers;
    private int orderIngots;

    public int getOrderTonnage() {
        return orderTonnage;
    }

    public void setOrderTonnage(int orderTonnage) {
        this.orderTonnage = orderTonnage;
    }

    public int getOrderContainers() {
        return orderContainers;
    }

    public void setOrderContainers(int orderContainers) {
        this.orderContainers = orderContainers;
    }

    public int getOrderIngots() {
        return orderIngots;
    }

    public void setOrderIngots(int orderIngots) {
        this.orderIngots = orderIngots;
    }
}
