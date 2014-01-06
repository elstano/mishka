package ru.org.icad.mishka.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TRANSPORT_DESTINATION")
public class TransportDestination implements Serializable {

    private static final long serialVersionUID = 395587670788451859L;

    @Id
    @Column(name = "DESTINATION_ID")
    private int id;
    @Column(name = "DESTINATION_NAME")
    private String name;

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
