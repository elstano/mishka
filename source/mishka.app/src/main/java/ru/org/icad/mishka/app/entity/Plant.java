package ru.org.icad.mishka.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "PLANT")
public class Plant implements Serializable {

    private static final long serialVersionUID = 6458047083599466218L;

    @Id
    @Column(name = "PLANT_ID")
    private int id;
    @Column(name = "PLANT_NAME")
    private String name;
    @Column(name = "PREMIUM_A7")
    private BigDecimal premiumA7;
    @Column(name = "CLIP_ADD_COST")
    private BigDecimal clipAddCost;
    @Column(name = "CLIP_MELT_LOSS")
    private BigDecimal clipMeltLoss;

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

    public BigDecimal getPremiumA7() {
        return premiumA7;
    }

    public void setPremiumA7(BigDecimal premiumA7) {
        this.premiumA7 = premiumA7;
    }

    public BigDecimal getClipAddCost() {
        return clipAddCost;
    }

    public void setClipAddCost(BigDecimal clipAddCost) {
        this.clipAddCost = clipAddCost;
    }

    public BigDecimal getClipMeltLoss() {
        return clipMeltLoss;
    }

    public void setClipMeltLoss(BigDecimal clipMeltLoss) {
        this.clipMeltLoss = clipMeltLoss;
    }
}
