package ru.org.icad.mishka.app.process.casting.schema5_6;

import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.process.casting.AbstractSchema;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.schema4.operation.PrepareCmOperation;
import ru.org.icad.mishka.app.process.casting.schema5_6.operation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Schema5_6 extends AbstractSchema {

    public Schema5_6() {
        CleanCollectorOneOperation cleanCollectorOneOperation = new CleanCollectorOneOperation(this);
        CleanCollectorTwoOperation cleanCollectorTwoOperation = new CleanCollectorTwoOperation(this);
        PrepareCollectorOneOperation prepareCollectorOneOperation = new PrepareCollectorOneOperation(this);
        PrepareCollectorTwoOperation prepareCollectorTwoOperation = new PrepareCollectorTwoOperation(this);
        PeriodicCmOperation periodicCmOperation = new PeriodicCmOperation(this);
        PrepareCmOperation prepareCmOperation = new PrepareCmOperation(this);
        RemouldCmOperation remouldCmOperation = new RemouldCmOperation(this);
        CastCmOneOperation castCmOneOperation = new CastCmOneOperation(this);
        CastCmTwoOperation castCmTwoOperation = new CastCmTwoOperation(this);

        final Map<String, Operation> operationMap = getOperationMap();
        operationMap.put(OperationName.CLEAN_COLLECTOR_ONE, cleanCollectorOneOperation);
        operationMap.put(OperationName.CLEAN_COLLECTOR_TWO, cleanCollectorTwoOperation);
        operationMap.put(OperationName.PREPARE_COLLECTOR_ONE, prepareCollectorOneOperation);
        operationMap.put(OperationName.PREPARE_COLLECTOR_TWO, prepareCollectorTwoOperation);
        operationMap.put(OperationName.PERIODIC_CM, periodicCmOperation);
        operationMap.put(OperationName.PREPARE_CM, prepareCmOperation);
        operationMap.put(OperationName.REMOULD_CM, remouldCmOperation);
        operationMap.put(OperationName.CAST_CM_ONE, castCmOneOperation);
        operationMap.put(OperationName.CAST_CM_TWO, castCmTwoOperation);
    }

    @Override
    public List<? extends Operation> getInitOperations() {
        return Arrays.asList(
                getOperationMap().get(OperationName.CLEAN_COLLECTOR_ONE),
                getOperationMap().get(OperationName.CLEAN_COLLECTOR_TWO),
                getOperationMap().get(OperationName.PERIODIC_CM),
                getOperationMap().get(OperationName.REMOULD_CM)
                );
    }
}
