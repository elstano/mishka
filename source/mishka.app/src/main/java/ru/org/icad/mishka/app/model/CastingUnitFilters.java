package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.CU_FILTERS)
public class CastingUnitFilters implements Serializable {

    private static final long serialVersionUID = -3868068150785310580L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private CastingUnit castingUnit;
    @OneToOne
    private Filter filter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastingUnit getCastingUnit() {
        return castingUnit;
    }

    public void setCastingUnit(CastingUnit castingUnit) {
        this.castingUnit = castingUnit;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
