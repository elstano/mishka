package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = TableName.PRODUCT)
public class Product {

    @Id
    @Column(name = ColumnName.PRODUCT_ID)
    private int id;
    @Column(name = "PRODUCT_NAME")
    private String name;
    @OneToOne
    @JoinColumn(name = ColumnName.PLANT_ID)
    private Plant plant;
    @Column(name = "SPEC")
    private String spec;
    @Column(name = "SERIES")
    private String series;
    @OneToOne
    @JoinColumn(name = ColumnName.MARK_ID)
    private Mark mark;
    @OneToOne
    @JoinColumn(name = ColumnName.FORM_ID)
    private Form form;
    @OneToOne
    @JoinColumn(name = ColumnName.FILTRATION_ID)
    private Filtration filtration;
    @Column(name = "HOMOGENIZATION")
    private int homogenization;
    @Column(name = "CLIPPING")
    private int clipping;
    @Column(name = "COB")
    private int cob;
    @Column(name = "SI_MIN")
    private double siMin;
    @Column(name = "SI_MAX")
    private double siMax;
    @Column(name = "FE_MIN")
    private double feMin;
    @Column(name = "FE_MAX")
    private double feMax;
    @Column(name = "CU_MIN")
    private double cuMin;
    @Column(name = "CU_MAX")
    private double cuMax;
    @Column(name = "MG_MIN")
    private double mgMin;
    @Column(name = "MG_MAX")
    private double mgMax;
    @Column(name = "MN_MIN")
    private double mnMin;
    @Column(name = "MN_MAX")
    private double mnMax;
    @Column(name = "TI_MIN")
    private double tiMin;
    @Column(name = "TI_MAX")
    private double tiMax;

    public Product() {
    }

    public Product(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form formId) {
        this.form = formId;
    }

    public Filtration getFiltration() {
        return filtration;
    }

    public void setFiltration(Filtration filtration) {
        this.filtration = filtration;
    }

    public int getHomogenization() {
        return homogenization;
    }

    public void setHomogenization(int homogenization) {
        this.homogenization = homogenization;
    }

    public int getClipping() {
        return clipping;
    }

    public void setClipping(int clipping) {
        this.clipping = clipping;
    }

    public int getCob() {
        return cob;
    }

    public void setCob(int cob) {
        this.cob = cob;
    }

    public double getSiMin() {
        return siMin;
    }

    public void setSiMin(double siMin) {
        this.siMin = siMin;
    }

    public double getSiMax() {
        return siMax;
    }

    public void setSiMax(double siMax) {
        this.siMax = siMax;
    }

    public double getFeMin() {
        return feMin;
    }

    public void setFeMin(double feMin) {
        this.feMin = feMin;
    }

    public double getFeMax() {
        return feMax;
    }

    public void setFeMax(double feMax) {
        this.feMax = feMax;
    }

    public double getCuMin() {
        return cuMin;
    }

    public void setCuMin(double cuMin) {
        this.cuMin = cuMin;
    }

    public double getCuMax() {
        return cuMax;
    }

    public void setCuMax(double cuMax) {
        this.cuMax = cuMax;
    }

    public double getMgMin() {
        return mgMin;
    }

    public void setMgMin(double mgMin) {
        this.mgMin = mgMin;
    }

    public double getMgMax() {
        return mgMax;
    }

    public void setMgMax(double mgMax) {
        this.mgMax = mgMax;
    }

    public double getMnMin() {
        return mnMin;
    }

    public void setMnMin(double mnMin) {
        this.mnMin = mnMin;
    }

    public double getMnMax() {
        return mnMax;
    }

    public void setMnMax(double mnMax) {
        this.mnMax = mnMax;
    }

    public double getTiMin() {
        return tiMin;
    }

    public void setTiMin(double tiMin) {
        this.tiMin = tiMin;
    }

    public double getTiMax() {
        return tiMax;
    }

    public void setTiMax(double tiMax) {
        this.tiMax = tiMax;
    }

    public boolean isSuit(ElectrolizerPrognosis electrolizerPrognosis) {
        return cuMax >= electrolizerPrognosis.getCu()
                && feMax >= electrolizerPrognosis.getFe()
                && mgMax >= electrolizerPrognosis.getMg()
                && mnMax >= electrolizerPrognosis.getMn()
                && siMax >= electrolizerPrognosis.getSi()
                && tiMax >= electrolizerPrognosis.getTi();

    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
