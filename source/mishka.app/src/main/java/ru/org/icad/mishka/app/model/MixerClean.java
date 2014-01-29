package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = TableName.MIXER_CLEAN)
public class MixerClean {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.COLLE_ID)
    private CastingUnitCollector castingUnitCollector;
    @OneToOne
    @JoinColumn(name = ColumnName.DISTR_ID)
    private CastingUnitDistributor castingUnitDistributor;
    @Column(name = "CLEAN_DATE")
    private Date date;
    @Column(name = "SHIFT")
    private int shift;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastingUnitCollector getCastingUnitCollector() {
        return castingUnitCollector;
    }

    public void setCastingUnitCollector(CastingUnitCollector castingUnitCollector) {
        this.castingUnitCollector = castingUnitCollector;
    }

    public CastingUnitDistributor getCastingUnitDistributor() {
        return castingUnitDistributor;
    }

    public void setCastingUnitDistributor(CastingUnitDistributor castingUnitDistributor) {
        this.castingUnitDistributor = castingUnitDistributor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
