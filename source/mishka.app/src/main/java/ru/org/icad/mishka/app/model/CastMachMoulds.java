package ru.org.icad.mishka.app.model;

import org.eclipse.persistence.annotations.Customizer;
import ru.org.icad.mishka.app.OrderCustomizer;
import ru.org.icad.mishka.app.constant.ColumnName;
import ru.org.icad.mishka.app.constant.TableName;

import javax.persistence.*;

@Entity
@Customizer(OrderCustomizer.class)
@Table(name = TableName.CAST_MACH_MOULDS)
public class CastMachMoulds {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CAST_MACH_ID)
    private CastingUnitCastingMachine castingUnitCastingMachine;
    @OneToOne
    @JoinColumn(name = ColumnName.MOULD_ID)
    private Mould mould;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastingUnitCastingMachine getCastingUnitCastingMachine() {
        return castingUnitCastingMachine;
    }

    public void setCastingUnitCastingMachine(CastingUnitCastingMachine castingUnitCastingMachine) {
        this.castingUnitCastingMachine = castingUnitCastingMachine;
    }

    public Mould getMould() {
        return mould;
    }

    public void setMould(Mould mould) {
        this.mould = mould;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
