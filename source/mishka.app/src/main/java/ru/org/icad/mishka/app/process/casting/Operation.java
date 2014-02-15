package ru.org.icad.mishka.app.process.casting;

import java.sql.Date;

public abstract class Operation {

    private CastWrapper castWrapper;
    private int activationCount;
    private int activationMaxCount;
    private Date activationDate;
    private int nextId;

    public CastWrapper getCastWrapper() {
        return castWrapper;
    }

    public void setCastWrapper(CastWrapper castWrapper) {
        this.castWrapper = castWrapper;
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

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public abstract void activate();
}
