package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Customizer(OrderCustomizer.class)
@Table(name = TableName.TRANSPORT_DESTINATION)
public class TransportDestination {

    @Id
    @Column(name = ColumnName.DESTINATION_ID)
    private int id;
    @Column(name = "DESTINATION_NAME")
    private String name;

    public TransportDestination() {
    }

    public TransportDestination(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransportDestination that = (TransportDestination) o;

        if (id != that.id) return false;

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
