package ru.org.icad.mishka.app.process.casting.schema4;

import ru.org.icad.mishka.app.constant.OperationName;
import ru.org.icad.mishka.app.process.casting.AbstractSchema;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.SchemaConfiguration;
import ru.org.icad.mishka.app.process.casting.schema4.operation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Schema4 extends AbstractSchema {

    private final SchemaConfiguration schemaConfiguration;

    public Schema4(SchemaConfiguration schemaConfiguration) {
        this.schemaConfiguration = schemaConfiguration;

        CleanCollectorOperation cleanCollectorOperation = new CleanCollectorOperation(this);
        PrepareCollectorOperation prepareCollectorOperation = new PrepareCollectorOperation(this);
        CastCmOperation castCmOperation = new CastCmOperation(this, 2);
        PeriodicCmOperation periodicCmOperation = new PeriodicCmOperation(this);
        PrepareCmOperation prepareCmOperation = new PrepareCmOperation(this);

        final Map<String, Operation> operationMap = getOperationMap();
        operationMap.put(OperationName.CLEAN_COLLECTOR, cleanCollectorOperation);
        operationMap.put(OperationName.PREPARE_COLLECTOR, prepareCollectorOperation);
        operationMap.put(OperationName.CAST_CM, castCmOperation);
        operationMap.put(OperationName.PERIODIC_CM, periodicCmOperation);
        operationMap.put(OperationName.PREPARE_CM, prepareCmOperation);
    }

    @Override
    public List<? extends Operation> getInitOperations() {
        return Arrays.asList(
                getOperationMap().get(OperationName.CLEAN_COLLECTOR),
                getOperationMap().get(OperationName.PERIODIC_CM)
        );
    }

    @Override
    public SchemaConfiguration getSchemaConfiguration() {
        return schemaConfiguration;
    }
}
