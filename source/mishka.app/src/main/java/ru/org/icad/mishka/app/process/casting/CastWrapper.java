package ru.org.icad.mishka.app.process.casting;

import ru.org.icad.mishka.app.model.Cast;

import java.sql.Date;

public class CastWrapper {

    private int castId;
    private long prepareTime;
    private long castTime;
    private Date startDate;
    private Date endDate;
    private Cast cast;

    public CastWrapper(Cast cast) {
        this.cast = cast;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public long getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(long prepareTime) {
        this.prepareTime = prepareTime;
    }

    public long getCastTime() {
        return castTime;
    }

    public void setCastTime(long castTime) {
        this.castTime = castTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Cast getCast() {
        return cast;
    }

    public void setCast(Cast cast) {
        this.cast = cast;
    }
}
