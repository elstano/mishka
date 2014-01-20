package ru.org.icad.mishka.app.model;

import javax.persistence.*;

@Entity
@Table(name = "CASTING_SPEED")
public class CastingSpeed {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private Mould mould;
    @OneToOne
    private Mark mark;
    @Column(name = "SPEED")
    private int speed;

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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
