package ru.org.icad.mishka.app.process.casting.schema3.operation;

import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

import java.sql.Date;

public class PrepareCmTwoOperation extends Operation {

    public PrepareCmTwoOperation() {
    }

    @Override
    public void init(CastWrapper castWrapper) {

    }

    @Override
    public boolean activate() {
//        Operation castCmOneOperation = new CastCmOneOperation();
//        this.getSchema().getOperations().add(castCmOneOperation);

        return true;

    }

    @Override
    public Date getProcessTime() {
        return null;
    }
}
