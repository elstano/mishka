package ru.org.icad.mishka.app.process.casting;

import java.sql.Date;
import java.util.Queue;

public abstract class Operation {

    private int castId;
    private int activationCount;
    private int activationMaxCount;
    private Date activationDate;
    private Queue<CastWrapper> castWrappers;

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

    public void init(Queue<CastWrapper> castWrappers) {
        this.castWrappers = castWrappers;
    }

    public abstract boolean activate();

    public abstract Date getProcessTime();
}
