package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.FILTER_CHANGE_MARK)
public class FilterChangeMark implements Serializable {

    private static final long serialVersionUID = 7255032725200944555L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private Filter filter;
    @Column(name = "MARK_ID_1")
    private int markId1;
    @Column(name = "MARK_ID_2")
    private int markId2;
    @Column(name = "DURATION_TIME")
    private int durationTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public int getMarkId1() {
        return markId1;
    }

    public void setMarkId1(int markId1) {
        this.markId1 = markId1;
    }

    public int getMarkId2() {
        return markId2;
    }

    public void setMarkId2(int markId2) {
        this.markId2 = markId2;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }
}
