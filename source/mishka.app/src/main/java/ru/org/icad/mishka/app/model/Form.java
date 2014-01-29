package ru.org.icad.mishka.app.model;


import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = TableName.FORM)
public class Form {

    @Id
    @Column(name = ColumnName.FORM_ID)
    private int id;
    @Column(name = "FORM_NAME")
    private String name;

    public Form() {
    }

    public Form(int id) {
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
    public String toString() {
        return String.valueOf(getId());
    }
}
