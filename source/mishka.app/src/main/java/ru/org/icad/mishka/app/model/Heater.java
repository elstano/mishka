package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = TableName.HEATER)
public class Heater {

    @Id
    @Column(name = ColumnName.HEATER_ID)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CH_ID)
    private CastHouse castHouse;
    @Column(name = "STATE")
    private String state;
    @Column(name = "START_TIME")
    private Date startTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastHouse getCastHouse() {
        return castHouse;
    }

    public void setCastHouse(CastHouse castHouse) {
        this.castHouse = castHouse;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
