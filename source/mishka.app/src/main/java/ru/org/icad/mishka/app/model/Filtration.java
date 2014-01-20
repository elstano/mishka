package ru.org.icad.mishka.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "FILTRATION")
public class Filtration implements Serializable {

    private static final long serialVersionUID = -3616170886374500016L;

    @Id
    @Column(name = "FILTRATION_ID")
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
