package ru.org.icad.mishka.app.model;

import ru.org.icad.mishka.app.ColumnName;
import ru.org.icad.mishka.app.TableName;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = TableName.CU_FILTER)
public class Filter {

    @Id
    @Column(name = ColumnName.FILTER_ID)
    private int id;
    @OneToOne
    @JoinColumn(name = ColumnName.CH_ID)
    private CastHouse castHouse;
    @Column(name = "STATE")
    private String state;
    @Column(name = "START_CU_ID")
    private Integer startCastingUnitId;
    @Column(name = "START_HEATER_ID")
    private Integer startHeaterId;
    @Column(name = "START_TIME")
    private Date startTime;
    @Column(name = "RESOURCE_CURRENT")
    private int resource;
    @Column(name = "RESOURCE_OVER")
    private int resourceOver;
    @Column(name = "PREPARE_TIME")
    private int prepareTime;
    @Column(name = "HEAT_TIME")
    private int heatTime;
    @Column(name = "INSTALL_TIME")
    private int installTime;
    @Column(name = "TYPE")
    private String type;

    public Filter() {
    }

    public Filter(int id) {
        this.id = id;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getStartCastingUnitId() {
        return startCastingUnitId;
    }

    public void setStartCastingUnitId(Integer startCastingUnitId) {
        this.startCastingUnitId = startCastingUnitId;
    }

    public Integer getStartHeaterId() {
        return startHeaterId;
    }

    public void setStartHeaterId(Integer startHeaterId) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
