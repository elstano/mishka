package ru.org.icad.mishka.app.order;

import ru.org.icad.mishka.app.chemistry.Chemistry;

public class Order {

    private int capacity;
    private Chemistry chemistry;

    public Order(int capacity, Chemistry chemistry) {
        this.capacity = capacity;
        this.chemistry = chemistry;
    }

    public int getCapacity() {
        return capacity;
    }

    public Chemistry getChemistry() {
        return chemistry;
    }

    @Override
    public String toString() {
        return "Order{" +
                "capacity=" + capacity +
                ", chemistry=" + chemistry +
                '}';
    }
}
