package ru.org.icad.mishka.app.process.casting.schema4.operation;

import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

import java.sql.Date;

public class PrepareMixerOperation extends Operation {

    public PrepareMixerOperation() {
    }

    @Override
    public void init(CastWrapper castWrapper) {

    }

    @Override
    public boolean activate() {

        return false;
    }

    @Override
    public Date getProcessTime() {
        return null;
    }
}
