package ru.org.icad.mishka.app.process.casting.schema9;

import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.process.casting.AbstractSchema;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.SchemaConfiguration;
import ru.org.icad.mishka.app.process.casting.schema9.operation.RemouldCmTwoOperation;
import ru.org.icad.mishka.app.process.casting.schema9.operation.cast.CastCmOneCollectorOneOperation;
import ru.org.icad.mishka.app.process.casting.schema9.operation.cast.CastCmThreeCollectorTwoOperation;
import ru.org.icad.mishka.app.process.casting.schema9.operation.cast.CastCmTwoCollectorOneOperation;
import ru.org.icad.mishka.app.process.casting.schema9.operation.cast.CastCmTwoCollectorTwoOperation;
import ru.org.icad.mishka.app.process.casting.schema9.operation.clean.CleanCollectorOneOperation;
import ru.org.icad.mishka.app.process.casting.schema9.operation.clean.CleanCollectorTwoOperation;
import ru.org.icad.mishka.app.process.casting.schema9.operation.periodic.PeriodicCmOneOperation;
import ru.org.icad.mishka.app.process.casting.schema9.operation.periodic.PeriodicCmThreeOperation;
import ru.org.icad.mishka.app.process.casting.schema9.operation.periodic.PeriodicCmTwoOperation;
import ru.org.icad.mishka.app.process.casting.schema9.operation.prepare.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Schema9 extends AbstractSchema {

    private static final int INIT_NEXT_ID = 1;
    private SchemaConfiguration schemaConfiguration;

    public Schema9(SchemaConfiguration schemaConfiguration) {
        this.schemaConfiguration = schemaConfiguration;

        CleanCollectorOneOperation cleanCollectorOneOperation = new CleanCollectorOneOperation(this);
        CleanCollectorTwoOperation cleanCollectorTwoOperation = new CleanCollectorTwoOperation(this);

        PrepareCollectorOneOperation prepareCollectorOneOperation = new PrepareCollectorOneOperation(this);
        PrepareCollectorTwoOperation prepareCollectorTwoOperation = new PrepareCollectorTwoOperation(this);
        PrepareCmOneOperation prepareCmOneOperation = new PrepareCmOneOperation(this, 2, 2);
        PrepareCmTwoOperation prepareCmTwoOperation = new PrepareCmTwoOperation(this, 2, 2);
        PrepareCmThreeOperation prepareCmThreeOperation = new PrepareCmThreeOperation(this, 2, 2);

        PeriodicCmOneOperation periodicCmOneOperation = new PeriodicCmOneOperation(this);
        PeriodicCmTwoOperation periodicCmTwoOperation = new PeriodicCmTwoOperation(this);
        PeriodicCmThreeOperation periodicCmThreeOperation = new PeriodicCmThreeOperation(this);

        RemouldCmTwoOperation remouldCmTwoOperation = new RemouldCmTwoOperation(this);
        remouldCmTwoOperation.setNextId(INIT_NEXT_ID);

        CastCmTwoCollectorOneOperation castCmCollectorOneOperation = new CastCmTwoCollectorOneOperation(this, 2, 2);
        CastCmTwoCollectorTwoOperation castCmCollectorTwoOperation = new CastCmTwoCollectorTwoOperation(this, 2, 2);
        CastCmOneCollectorOneOperation castCmOneCollectorOneOperation = new CastCmOneCollectorOneOperation(this, 2, 2);
        CastCmThreeCollectorTwoOperation castCmThreeCollectorTwoOperation = new CastCmThreeCollectorTwoOperation(this, 2, 2);

        final Map<String, Operation> operationMap = getOperationMap();
        operationMap.put(OperationName.CLEAN_COLLECTOR_ONE, cleanCollectorOneOperation);
        operationMap.put(OperationName.CLEAN_COLLECTOR_TWO, cleanCollectorTwoOperation);
        operationMap.put(OperationName.PREPARE_COLLECTOR_ONE, prepareCollectorOneOperation);
        operationMap.put(OperationName.PREPARE_COLLECTOR_TWO, prepareCollectorTwoOperation);
        operationMap.put(OperationName.PREPARE_CM_ONE, prepareCmOneOperation);
        operationMap.put(OperationName.PREPARE_CM_TWO, prepareCmTwoOperation);
        operationMap.put(OperationName.PREPARE_CM_THREE, prepareCmThreeOperation);
        operationMap.put(OperationName.PERIODIC_CM_ONE, periodicCmOneOperation);
        operationMap.put(OperationName.PERIODIC_CM_TWO, periodicCmTwoOperation);
        operationMap.put(OperationName.PERIODIC_CM_THREE, periodicCmThreeOperation);
        operationMap.put(OperationName.REMOULD_CM_TWO, remouldCmTwoOperation);
        operationMap.put(OperationName.CAST_CM_TWO_COLLECTOR_ONE, castCmCollectorOneOperation);
        operationMap.put(OperationName.CAST_CM_TWO_COLLECTOR_TWO, castCmCollectorTwoOperation);
        operationMap.put(OperationName.CAST_CM_ONE_COLLECTOR_ONE, castCmOneCollectorOneOperation);
        operationMap.put(OperationName.CAST_CM_THREE_COLLECTOR_TWO, castCmThreeCollectorTwoOperation);
    }

    @Override
    public List<? extends Operation> getInitOperations() {
        return Arrays.asList(
                getOperationMap().get(OperationName.REMOULD_CM_TWO),
                getOperationMap().get(OperationName.PERIODIC_CM_ONE),
                getOperationMap().get(OperationName.PERIODIC_CM_TWO),
                getOperationMap().get(OperationName.PERIODIC_CM_THREE),
                getOperationMap().get(OperationName.CLEAN_COLLECTOR_ONE),
                getOperationMap().get(OperationName.CLEAN_COLLECTOR_TWO)
        );
    }

    @Override
    public SchemaConfiguration getSchemaConfiguration() {
        return schemaConfiguration;
    }
}
