package ru.org.icad.mishka.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "CM_OPERATION")
public class CastingMachineOperation implements Serializable {

    private static final long serialVersionUID = 4636961298011180426L;

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
