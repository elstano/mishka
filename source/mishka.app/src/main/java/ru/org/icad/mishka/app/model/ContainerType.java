package ru.org.icad.mishka.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "CONTAINER_TYPE")
public class ContainerType implements Serializable {

    private static final long serialVersionUID = 3650618609249331003L;

    @Id
    @Column(name = "CONTAINER_TYPE_ID")
    private int id;
    @Column(name = "CONTAINERS_TYPE_NAME")
    private String name;
    @Column(name = "CAPACITY")
    private int capacity;

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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
