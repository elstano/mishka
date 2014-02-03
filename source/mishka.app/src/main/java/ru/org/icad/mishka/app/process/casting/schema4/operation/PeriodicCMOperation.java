package ru.org.icad.mishka.app.process.casting.schema4.operation;

import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;

import java.sql.Date;

public class PeriodicCMOperation extends Operation {

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public Date getProcessTime() {
        return null;
    }
}
