package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class PrepareCmOperation extends Operation {

    private final Schema schema;

    public PrepareCmOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
//        Operation castCmOneOperation = this.getSchema().getOperationMap().get(OperationName.CAST_CM_ONE_OPERATION);
//        if (castCmOneOperation == null) {
//            castCmOneOperation = new CastCmOneOperation();
//            castCmOneOperation.setActivationCount(castCmOneOperation.getActivationMaxCount() - 1);
//
//            this.getSchema().getOperationMap().put(OperationName.CAST_CM_ONE_OPERATION, castCmOneOperation);
//            this.getSchema().getOperations().add(castCmOneOperation);
//        }

    }
}
