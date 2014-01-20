package ru.org.icad.mishka.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CAST_MACH_MOULDS")
public class CastMachMoulds implements Serializable {

    private static final long serialVersionUID = 4803568493813809259L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private CastingUnitCastingMachine castingUnitCastingMachine;
    @OneToOne
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
}
