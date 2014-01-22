package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = TableName.ELECTROLIZER_PROGNOSIS)
public class ElectrolizerPrognosis {

    @Id
    @Column(name = ColumnName.ELECTROLIZER_ID)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CU_ID)
    private CastingUnit castingUnit;
    @Column(name = "PROGNOS_DATE")
    private Date date;
    @Column(name = "SHIFT")
    private int shift;
    @Column(name = "TONNAGE")
    private int tonnage;
    @Column(name = "FE")
    private double fe;
    @Column(name = "SI")
    private double si;
    @Column(name = "CU")
    private double cu;
    @Column(name = "MG")
    private double mg;
    @Column(name = "MN")
    private double mn;
    @Column(name = "TI")
    private double ti;

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

    public int getTonnage() {
        return tonnage;
    }

    public void setTonnage(int tonnange) {
        this.tonnage = tonnange;
    }

    public double getFe() {
        return fe;
    }

    public void setFe(double fe) {
        this.fe = fe;
    }

    public double getSi() {
        return si;
    }

    public void setSi(double si) {
        this.si = si;
    }

    public double getCu() {
        return cu;
    }

    public void setCu(double cu) {
        this.cu = cu;
    }

    public double getMg() {
        return mg;
    }

    public void setMg(double mg) {
        this.mg = mg;
    }

    public double getMn() {
        return mn;
    }

    public void setMn(double mn) {
        this.mn = mn;
    }

    public double getTi() {
        return ti;
    }

    public void setTi(double ti) {
        this.ti = ti;
    }
}
