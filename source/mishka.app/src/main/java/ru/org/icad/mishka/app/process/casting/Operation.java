package ru.org.icad.mishka.app.process.casting;

import java.sql.Date;
import java.util.Queue;

public abstract class Operation {

    private CastWrapper castWrapper;
    private int activationCount;
    private int activationMaxCount;
    private Date activationDate;
    private Queue<CastWrapper> castWrappers;

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

    public void setCastWrappers(Queue<CastWrapper> castWrappers) {
        this.castWrappers = castWrappers;
    }

    public Queue<CastWrapper> getCastWrappers() {
        return castWrappers;
    }

    public abstract boolean activate();
}
