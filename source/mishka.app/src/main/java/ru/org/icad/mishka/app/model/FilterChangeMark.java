package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.*;

@Entity
@Customizer(OrderCustomizer.class)
@Table(name = TableName.FILTER_CHANGE_MARK)
public class FilterChangeMark {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.FILTER_ID)
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

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
