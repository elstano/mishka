package ru.org.icad.mishka.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "MARK")
public class Mark implements Serializable {

    private static final long serialVersionUID = 3010727995864605098L;

    @Id
    @Column(name = "MARK_ID")
    private int id;
    @Column(name = "MARK_NAME")
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
