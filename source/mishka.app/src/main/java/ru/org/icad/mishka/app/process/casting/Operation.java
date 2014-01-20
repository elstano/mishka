package ru.org.icad.mishka.app.process.casting;

import java.sql.Date;

public abstract class Operation {

    private int castId;
    private int activationCount;
    private int activationMaxCount;
    private Date activationDate;

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public int getActivationCount() {
        return activationCount;
    }

    public void setActivationCount(int activationCount) {
        this.activationCount = activationCount;
    }

    public int getActivationMaxCount() {
        return activationMaxCount;
    }

    public void setActivationMaxCount(int activationMaxCount) {
        this.activationMaxCount = activationMaxCount;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public abstract void init(CastWrapper castWrapper);

    public abstract boolean activate();

    public abstract Date getProcessTime();
}
