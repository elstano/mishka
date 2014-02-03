package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;

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
    @Column(name = "STATUS")
    private String status;
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
    private String homogenization;
    @Column(name = "CLIPPING")
    private int clipping;
    @Column(name = "COB")
    private int cob;
    @Column(name = "SI_MIN")
    private Double siMin;
    @Column(name = "SI_MAX")
    private Double siMax;
    @Column(name = "FE_MIN")
    private Double feMin;
    @Column(name = "FE_MAX")
    private Double feMax;
    @Column(name = "CU_MIN")
    private Double cuMin;
    @Column(name = "CU_MAX")
    private Double cuMax;
    @Column(name = "MG_MIN")
    private Double mgMin;
    @Column(name = "MG_MAX")
    private Double mgMax;
    @Column(name = "MN_MIN")
    private Double mnMin;
    @Column(name = "MN_MAX")
    private Double mnMax;
    @Column(name = "CR_MIN")
    private Double crMin;
    @Column(name = "CR_MAX")
    private Double crMax;
    @Column(name = "ZN_MIN")
    private Double znMin;
    @Column(name = "ZN_MAX")
    private Double znMax;
    @Column(name = "TI_MIN")
    private Double tiMin;
    @Column(name = "TI_MAX")
    private Double tiMax;
    @Column(name = "B_MIN")
    private Double bMin;
    @Column(name = "B_MAX")
    private Double bMax;
    @Column(name = "NA_MIN")
    private Double naMin;
    @Column(name = "NA_MAX")
    private Double naMax;
    @Column(name = "CA_MIN")
    private Double caMin;
    @Column(name = "CA_MAX")
    private Double caMax;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void setForm(Form form) {
        this.form = form;
    }

    public Filtration getFiltration() {
        return filtration;
    }

    public void setFiltration(Filtration filtration) {
        this.filtration = filtration;
    }

    public String getHomogenization() {
        return homogenization;
    }

    public void setHomogenization(String homogenization) {
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

    public Double getSiMin() {
        return siMin;
    }

    public void setSiMin(Double siMin) {
        this.siMin = siMin;
    }

    public Double getSiMax() {
        return siMax;
    }

    public void setSiMax(Double siMax) {
        this.siMax = siMax;
    }

    public Double getFeMin() {
        return feMin;
    }

    public void setFeMin(Double feMin) {
        this.feMin = feMin;
    }

    public Double getFeMax() {
        return feMax;
    }

    public void setFeMax(Double feMax) {
        this.feMax = feMax;
    }

    public Double getCuMin() {
        return cuMin;
    }

    public void setCuMin(Double cuMin) {
        this.cuMin = cuMin;
    }

    public Double getCuMax() {
        return cuMax;
    }

    public void setCuMax(Double cuMax) {
        this.cuMax = cuMax;
    }

    public Double getMgMin() {
        return mgMin;
    }

    public void setMgMin(Double mgMin) {
        this.mgMin = mgMin;
    }

    public Double getMgMax() {
        return mgMax;
    }

    public void setMgMax(Double mgMax) {
        this.mgMax = mgMax;
    }

    public Double getMnMin() {
        return mnMin;
    }

    public void setMnMin(Double mnMin) {
        this.mnMin = mnMin;
    }

    public Double getMnMax() {
        return mnMax;
    }

    public void setMnMax(Double mnMax) {
        this.mnMax = mnMax;
    }

    public Double getCrMin() {
        return crMin;
    }

    public void setCrMin(Double crMin) {
        this.crMin = crMin;
    }

    public Double getCrMax() {
        return crMax;
    }

    public void setCrMax(Double crMax) {
        this.crMax = crMax;
    }

    public Double getZnMin() {
        return znMin;
    }

    public void setZnMin(Double znMin) {
        this.znMin = znMin;
    }

    public Double getZnMax() {
        return znMax;
    }

    public void setZnMax(Double znMax) {
        this.znMax = znMax;
    }

    public Double getTiMin() {
        return tiMin;
    }

    public void setTiMin(Double tiMin) {
        this.tiMin = tiMin;
    }

    public Double getTiMax() {
        return tiMax;
    }

    public void setTiMax(Double tiMax) {
        this.tiMax = tiMax;
    }

    public Double getBMin() {
        return bMin;
    }

    public void setBMin(Double bMin) {
        this.bMin = bMin;
    }

    public Double getBMax() {
        return bMax;
    }

    public void setBMax(Double bMax) {
        this.bMax = bMax;
    }

    public Double getNaMin() {
        return naMin;
    }

    public void setNaMin(Double naMin) {
        this.naMin = naMin;
    }

    public Double getNaMax() {
        return naMax;
    }

    public void setNaMax(Double naMax) {
        this.naMax = naMax;
    }

    public Double getCaMin() {
        return caMin;
    }

    public void setCaMin(Double caMin) {
        this.caMin = caMin;
    }

    public Double getCaMax() {
        return caMax;
    }

    public void setCaMax(Double caMax) {
        this.caMax = caMax;
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
