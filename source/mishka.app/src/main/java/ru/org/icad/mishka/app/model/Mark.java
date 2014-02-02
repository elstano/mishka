package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = TableName.MARK)
public class Mark {

    @Id
    @Column(name = ColumnName.MARK_ID)
    private int id;
    @Column(name = "MARK_NAME")
    private String name;
    @Column(name = "PARENT_MARK_ID")
    private int parentMarkId;

    public Mark() {
    }

    public Mark(int id) {
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

    public int getParentMarkId() {
        return parentMarkId;
    }

    public void setParentMarkId(int parentMarkId) {
        this.parentMarkId = parentMarkId;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
