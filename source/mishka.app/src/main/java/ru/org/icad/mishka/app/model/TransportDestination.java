package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
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
}
