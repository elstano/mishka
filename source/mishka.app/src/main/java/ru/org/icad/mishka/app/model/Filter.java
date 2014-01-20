package ru.org.icad.mishka.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "FILTER")
public class Filter implements Serializable {

    private static final long serialVersionUID = -2171566222069357036L;

    @Id
    @Column(name = "FILTER_ID")
    private int id;
    @OneToOne
    private CastHouse castHouse;
    @Column(name = "STATE")
    private int state;
    @Column(name = "START_CU_ID")
    private int startCastingUnitId;
    @Column(name = "START_HEATER_ID")
    private int startHeaterId;
    @Column(name = "START_TIME")
    private Date startTime;
    @Column(name = "RESOURCE")
    private int resource;
    @Column(name = "RESOURCE_OVER")
    private int resourceOver;
    @Column(name = "PREPARE_TIME")
    private int prepareTime;
    @Column(name = "HEAT_TIME")
    private int heatTime;
    @Column(name = "INSTALL_TIME")
    private int installTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CastHouse getCastHouse() {
        return castHouse;
    }

    public void setCastHouse(CastHouse castHouse) {
        this.castHouse = castHouse;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStartCastingUnitId() {
        return startCastingUnitId;
    }

    public void setStartCastingUnitId(int startCastingUnitId) {
        this.startCastingUnitId = startCastingUnitId;
    }

    public int getStartHeaterId() {
        return startHeaterId;
    }

    public void setStartHeaterId(int startHeaterId) {
        this.startHeaterId = startHeaterId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getResourceOver() {
        return resourceOver;
    }

    public void setResourceOver(int resourceOver) {
        this.resourceOver = resourceOver;
    }

    public int getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }

    public int getHeatTime() {
        return heatTime;
    }

    public void setHeatTime(int heatTime) {
        this.heatTime = heatTime;
    }

    public int getInstallTime() {
        return installTime;
    }

    public void setInstallTime(int installTime) {
        this.installTime = installTime;
    }
}
