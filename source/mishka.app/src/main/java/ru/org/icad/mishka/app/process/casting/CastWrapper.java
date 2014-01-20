package ru.org.icad.mishka.app.process.casting;

import ru.org.icad.mishka.app.model.Cast;

import java.sql.Date;

public class CastWrapper {

    private int castId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
