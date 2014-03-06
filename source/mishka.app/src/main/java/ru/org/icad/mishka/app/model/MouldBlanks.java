package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "MouldBlanks.findAll",
                query = "SELECT mb FROM MouldBlanks mb"),
        @NamedQuery(name = "MouldBlanks.getMouldBlanksForMould",
                query = "SELECT mb FROM MouldBlanks mb WHERE mb.mould.id = :mouldId")
})
@Entity
@Customizer(OrderCustomizer.class)
@Table(name = TableName.MOULD_BLANKS)
public class MouldBlanks {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.MOULD_ID)
    private Mould mould;
    @Column(name = "NUM_BLANKS")
    private Integer numBlanks;

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

    public Integer getNumBlanks() {
        return numBlanks;
    }

    public void setNumBlanks(Integer numBlanks) {
        this.numBlanks = numBlanks;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
