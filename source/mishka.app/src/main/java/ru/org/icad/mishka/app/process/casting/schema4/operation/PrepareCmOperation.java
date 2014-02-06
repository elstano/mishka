package ru.org.icad.mishka.app.process.casting.schema4.operation;

import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

public class PrepareCmOperation extends Operation {

    private Schema schema;

    public PrepareCmOperation(Schema schema) {
        this.schema = schema;
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
}
