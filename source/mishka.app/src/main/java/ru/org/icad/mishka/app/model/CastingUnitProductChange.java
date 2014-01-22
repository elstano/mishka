package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.CU_PRODUCT_CHANGE)
public class CastingUnitProductChange implements Serializable {

    private static final long serialVersionUID = 763166633341116200L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @OneToOne
    private CastingUnit castingUnit;
    @Column(name = "MARK_ID_1")
    private int markId1;
    @Column(name = "MARK_ID_2")
    private int markId2;
    @Column(name = "TIME")
    private int time;
    @Column(name = "TONNAGE")
    private int tonnage;
    @Column(name = "CLEAN_NECESSITY")
    private int cleanNecessity;

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

    public int getMarkId1() {
        return markId1;
    }

    public void setMarkId1(int markId1) {
        this.markId1 = markId1;
    }

    public int getMarkId2() {
        return markId2;
    }

    public void setMarkId2(int markId2) {
        this.markId2 = markId2;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTonnage() {
        return tonnage;
    }

    public void setTonnage(int tonnage) {
        this.tonnage = tonnage;
    }

    public int getCleanNecessity() {
        return cleanNecessity;
    }

    public void setCleanNecessity(int cleanNecessity) {
        this.cleanNecessity = cleanNecessity;
    }
}
