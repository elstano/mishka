package ru.org.icad.mishka.app.equipment;

import ru.org.icad.mishka.app.chemistry.Chemistry;

@Deprecated
public class Electrolyzer implements Comparable<Electrolyzer> {

    private int number;
    private int capacity;
    private Chemistry chemistry;
    private int shift;

    public Electrolyzer(
            int number,
            int capacity,
            Chemistry chemistry,
            int shift
    ) {
        this.number = number;
        this.capacity = capacity;
        this.chemistry = chemistry;
        this.shift = shift;
    }

    public int getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }

    public Chemistry getChemistry() {
        return chemistry;
    }

    public int getShift() {
        return shift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Electrolyzer that = (Electrolyzer) o;

        return capacity == that.capacity
                && number == that.number
                && shift == that.shift
                && !(chemistry != null ? !chemistry.equals(that.chemistry) : that.chemistry != null);

    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + capacity;
        result = 31 * result + (chemistry != null ? chemistry.hashCode() : 0);
        result = 31 * result + shift;

        return result;
    }

    @Override
    public String toString() {
        return "Electrolyzer{" +
                "number=" + number +
                ", capacity=" + capacity +
                ", chemistry=" + chemistry +
                ", shift=" + shift +
                '}';
    }

    @Override
    public int compareTo(Electrolyzer electrolyzer) {
        if (number > electrolyzer.getNumber()
                && capacity > electrolyzer.getCapacity()
                && chemistry.compareTo(electrolyzer.getChemistry()) == 1
                && shift > electrolyzer.getShift()) {
            return 1;
        }

        if (number == electrolyzer.getNumber()
                && capacity == electrolyzer.getCapacity()
                && chemistry.compareTo(electrolyzer.getChemistry()) == 0
                && shift == electrolyzer.getShift()) {
            return 0;
        }

        return -1;
    }
}
