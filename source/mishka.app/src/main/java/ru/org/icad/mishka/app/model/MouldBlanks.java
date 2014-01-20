package ru.org.icad.mishka.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MOULD_BLANKS")
public class MouldBlanks implements Serializable {

    private static final long serialVersionUID = -3795722512830010105L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private Mould mould;
    @Column(name = "NUM_BLANKS")
    private int numBlanks;

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

    public int getNumBlanks() {
        return numBlanks;
    }

    public void setNumBlanks(int numBlanks) {
        this.numBlanks = numBlanks;
    }
}
