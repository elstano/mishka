package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;

@Entity
@Table(name = TableName.CASTING_SPEED)
public class CastingSpeed {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Id
    @OneToOne
    @JoinColumn(name = ColumnName.MOULD_ID)
    private Mould mould;
    @Id
    @OneToOne
    @JoinColumn(name = ColumnName.MARK_ID)
    private Mark mark;
    @Column(name = "SPEED")
    private double speed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mould getMould() {
        return mould;
    }

    public void setMould(Mould mould) {
        this.mould = mould;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
