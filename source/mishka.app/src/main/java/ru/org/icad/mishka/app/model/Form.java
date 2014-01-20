package ru.org.icad.mishka.app.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "FORM")
public class Form implements Serializable {

    private static final long serialVersionUID = -776156454429618074L;

    @Id
    @Column(name = "FORM_ID")
    private int id;
    @Column(name = "FORM_NAME")
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
