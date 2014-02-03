package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;

import java.sql.Date;

public class PrepareCmTwoOperation extends Operation {

    @Override
    public boolean activate() {
//        Operation castCmOneOperation = new CastCmOneOperation();
//        this.getSchema().getOperations().add(castCmOneOperation);

        return true;

    }
}
