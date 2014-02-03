package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;

import java.sql.Date;

public class RemouldCmOperation extends Operation {

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public Date getProcessTime() {
        return null;
    }
}
