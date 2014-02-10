package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = TableName.ELECTROLIZER_PROGNOSIS)
public class ElectrolizerPrognosis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = ColumnName.ELECTROLIZER_ID)
    private int electrolizerId;
    @OneToOne
    @JoinColumn(name = ColumnName.CH_ID)
    private CastHouse castHouse;
    @Column(name = "PROGNOS_DATE")
    private Date prognosDate;
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

    public int getElectrolizerId() {
        return electrolizerId;
    }

    public void setElectrolizerId(int electrolizerId) {
        this.electrolizerId = electrolizerId;
    }

    public CastHouse getCastHouse() {
        return castHouse;
    }

    public void setCastHouse(CastHouse castHouse) {
        this.castHouse = castHouse;
    }

    public Date getPrognosDate() {
        return prognosDate;
    }

    public void setPrognosDate(Date date) {
        this.prognosDate = date;
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

    public void setTonnage(int tonnage) {
        this.tonnage = tonnage;
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

    public boolean isSuit(Product product) {
        return cu >= product.getCuMax()
                && fe >= product.getFeMax()
                && mg >= product.getMgMax()
                && mn >= product.getMnMax()
                && si >= product.getSiMax()
                && ti >= product.getTiMax();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElectrolizerPrognosis that = (ElectrolizerPrognosis) o;

        if (electrolizerId != that.electrolizerId) return false;
        if (shift != that.shift) return false;
        if (tonnage != that.tonnage) return false;
        if (!castHouse.equals(that.castHouse)) return false;
        if (!prognosDate.equals(that.prognosDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = electrolizerId;
        result = 31 * result + castHouse.hashCode();
        result = 31 * result + prognosDate.hashCode();
        result = 31 * result + shift;
        result = 31 * result + tonnage;
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
