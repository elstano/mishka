package ru.org.icad.mishka.app.process.casting.schema5_6;

import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.process.casting.AbstractSchema;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.SchemaConfiguration;
import ru.org.icad.mishka.app.process.casting.schema5_6.operation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Schema5_6 extends AbstractSchema {

    private static final int INIT_NEXT_ID = 1;
    private SchemaConfiguration schemaConfiguration;

    public Schema5_6(SchemaConfiguration schemaConfiguration) {
        this.schemaConfiguration = schemaConfiguration;

        CleanCollectorOneOperation cleanCollectorOneOperation = new CleanCollectorOneOperation(this);
        CleanCollectorTwoOperation cleanCollectorTwoOperation = new CleanCollectorTwoOperation(this);
        PrepareCollectorOneOperation prepareCollectorOneOperation = new PrepareCollectorOneOperation(this);
        PrepareCollectorTwoOperation prepareCollectorTwoOperation = new PrepareCollectorTwoOperation(this);
        PeriodicCmOperation periodicCmOperation = new PeriodicCmOperation(this);
        PrepareCmOperation prepareCmOperation = new PrepareCmOperation(this, 2, 2);
        RemouldCmOperation remouldCmOperation = new RemouldCmOperation(this);
        remouldCmOperation.setNextId(INIT_NEXT_ID);

        CastCmCollectorOneOperation castCmCollectorOneOperation = new CastCmCollectorOneOperation(this, 2, 2);
        CastCmCollectorTwoOperation castCmCollectorTwoOperation = new CastCmCollectorTwoOperation(this, 2, 2);

        final Map<String, Operation> operationMap = getOperationMap();
        operationMap.put(OperationName.CLEAN_COLLECTOR_ONE, cleanCollectorOneOperation);
        operationMap.put(OperationName.CLEAN_COLLECTOR_TWO, cleanCollectorTwoOperation);
        operationMap.put(OperationName.PREPARE_COLLECTOR_ONE, prepareCollectorOneOperation);
        operationMap.put(OperationName.PREPARE_COLLECTOR_TWO, prepareCollectorTwoOperation);
        operationMap.put(OperationName.PERIODIC_CM, periodicCmOperation);
        operationMap.put(OperationName.PREPARE_CM, prepareCmOperation);
        operationMap.put(OperationName.REMOULD_CM, remouldCmOperation);
        operationMap.put(OperationName.CAST_CM_COLLECTOR_ONE, castCmCollectorOneOperation);
        operationMap.put(OperationName.CAST_CM_COLLECTOR_TWO, castCmCollectorTwoOperation);
    }

    @Override
    public List<? extends Operation> getInitOperations() {
        return Arrays.asList(
                getOperationMap().get(OperationName.REMOULD_CM),
                getOperationMap().get(OperationName.PERIODIC_CM),
                getOperationMap().get(OperationName.CLEAN_COLLECTOR_ONE),
                getOperationMap().get(OperationName.CLEAN_COLLECTOR_TWO)
        );
    }

    @Override
    public SchemaConfiguration getSchemaConfiguration() {
        return schemaConfiguration;
    }
}
