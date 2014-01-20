package ru.org.icad.mishka.app.process.casting.schema1_2.operation;

import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.OperationName;

import java.sql.Date;

public class PrepareCmOperation extends Operation {

    private CastWrapper cast;

//    public PrepareCmOperation(Schema schema) {
//        this.setSchema(schema);
//    }

    @Override
    public void init(CastWrapper castWrapper) {

    }

    @Override
    public boolean activate() {
//        Operation castCmOneOperation = this.getSchema().getOperationMap().get(OperationName.CAST_CM_ONE_OPERATION);
//        if (castCmOneOperation == null) {
//            castCmOneOperation = new PrepareCmOperation();
//            castCmOneOperation.setActivationCount(castCmOneOperation.getActivationMaxCount() - 1);
//
//            this.getSchema().getOperationMap().put(OperationName.CAST_CM_ONE_OPERATION, castCmOneOperation);
//            this.getSchema().getOperations().add(castCmOneOperation);
//        }

        return true;
    }

    @Override
    public Date getProcessTime() {
        return null;
    }
}
