package ru.org.icad.mishka.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CM_OPERATION")
public class CastingMachineOperation {

    @Id
    @Column(name = "OPERATION_ID")
    private int id;
    @Column(name = "TYPE")
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
