package ru.org.icad.mishka.app.process.casting;

import ru.org.icad.mishka.app.model.Cast;

import java.sql.Date;

public class CastWrapper {

    private long prepareCollectorTime;
    private long castTime;
    private long flushCastTime;
    private long flushCollectorPrepareTime;
    private long flushCmPrepareTime;
    private Date startDate;
    private Date endDate;
    private Cast cast;

    // ToDo Убрать отсюда
    private Integer blankCountTwo;
    private Integer ingotInBlankCountTwo;
    private Integer lengthTwo;


    public CastWrapper(Cast cast) {
        this.cast = cast;
    }

    public long getPrepareCollectorTime() {
        return prepareCollectorTime;
    }

    public void setPrepareCollectorTime(long prepareCollectorTime) {
        this.prepareCollectorTime = prepareCollectorTime;
    }

    public long getCastTime() {
        return castTime;
    }

    public void setCastTime(long castTime) {
        this.castTime = castTime;
    }

    public long getFlushCastTime() {
        return flushCastTime;
    }

    public void setFlushCastTime(long flushCastTime) {
        this.flushCastTime = flushCastTime;
    }

    public long getFlushCollectorPrepareTime() {
        return flushCollectorPrepareTime;
    }

    public void setFlushCollectorPrepareTime(long flushCollectorPrepareTime) {
        this.flushCollectorPrepareTime = flushCollectorPrepareTime;
    }

    public long getFlushCmPrepareTime() {
        return flushCmPrepareTime;
    }

    public void setFlushCmPrepareTime(long flushCmPrepareTime) {
        this.flushCmPrepareTime = flushCmPrepareTime;
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

    public Integer getBlankCountTwo() {
        return blankCountTwo;
    }

    public void setBlankCountTwo(Integer blankCountTwo) {
        this.blankCountTwo = blankCountTwo;
    }

    public Integer getIngotInBlankCountTwo() {
        return ingotInBlankCountTwo;
    }

    public void setIngotInBlankCountTwo(Integer ingotInBlankCountTwo) {
        this.ingotInBlankCountTwo = ingotInBlankCountTwo;
    }

    public Integer getLengthTwo() {
        return lengthTwo;
    }

    public void setLengthTwo(Integer lengthTwo) {
        this.lengthTwo = lengthTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CastWrapper that = (CastWrapper) o;

        if (!cast.equals(that.cast)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return cast.hashCode();
    }
}
