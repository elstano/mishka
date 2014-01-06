package ru.org.icad.mishka.app.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CASTING_SPEED")
public class CastingSpeed implements Serializable {

    private static final long serialVersionUID = 8296133913360074899L;

    @Id
    @GeneratedValue
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
