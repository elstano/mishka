package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.MOULD_BLANKS)
public class MouldBlanks {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.MOULD_ID)
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

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
