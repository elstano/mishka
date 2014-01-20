package ru.org.icad.mishka.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

    private static final long serialVersionUID = 3479415488976214421L;

    @Id
    @Column(name = "PRODUCT_ID")
    private int id;
    @Column(name = "PRODUCT_NAME")
    private String name;
    @OneToOne
    private Plant plant;
    @Column(name = "SPEC")
    private String spec;
    @Column(name = "SERIES")
    private String series;
    @OneToOne
    private Mark mark;
    @OneToOne
    private Form formId;
    @Column(name = "PREMIUM")
    private BigDecimal premium;
    @Column(name = "DIAMETER")
    private int diameter;
    @Column(name = "WIDTH")
    private int width;
    @Column(name = "HEIGHT")
    private int height;
    @Column(name = "WEIGHT")
    private int weight;
    @OneToOne
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

    public Form getFormId() {
        return formId;
    }

    public void setFormId(Form formId) {
        this.formId = formId;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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
}
