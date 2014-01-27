package ru.org.icad.mishka.app.model;


import ru.org.icad.mishka.app.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = TableName.CAST_ELECTROLIZER)
public class CastElectrolizer extends Cast {

    @Column(name = "IS_ENOUGH")
    private boolean isEnough;

    public CastElectrolizer() {
    }

    public CastElectrolizer(Cast cast) {
        this.setCastingUnit(cast.getCastingUnit());
        this.setDate(cast.getDate());
        this.setShift(cast.getShift());
        this.setCastNumber(cast.getCastNumber());
        this.setOrder(cast.getOrder());
        this.setIngotCount(cast.getIngotCount());
        this.setIngotInBlankCount(cast.getIngotInBlankCount());
    }

    public CastElectrolizer(Cast cast, boolean isEnough) {
        this.setCastingUnit(cast.getCastingUnit());
        this.setDate(cast.getDate());
        this.setShift(cast.getShift());
        this.setCastNumber(cast.getCastNumber());
        this.setOrder(cast.getOrder());
        this.setIngotCount(cast.getIngotCount());
        this.setIngotInBlankCount(cast.getIngotInBlankCount());
        this.setEnough(isEnough);
    }


    public boolean isEnough() {
        return isEnough;
    }

    public void setEnough(boolean isEnough) {
        this.isEnough = isEnough;
    }

    @Override
    public String toString() {
        return "CastElectrolizer{" +
                "isEnough=" + isEnough +
                "} " + super.toString();
    }
}
