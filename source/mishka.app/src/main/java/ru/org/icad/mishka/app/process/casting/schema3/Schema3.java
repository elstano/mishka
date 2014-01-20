package ru.org.icad.mishka.app.process.casting.schema3;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.OperationName;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.process.casting.schema3.operation.PrepareCmOneOperation;
import ru.org.icad.mishka.app.process.casting.schema3.operation.PrepareCmTwoOperation;
import ru.org.icad.mishka.app.process.casting.schema3.operation.PrepareMixerOperation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Schema3 implements Schema {

    private final CastWrapper castWrapper;
    private final Queue<Operation> operations = Queues.newConcurrentLinkedQueue();
    private final Map<String, Operation> operationMap = Maps.newConcurrentMap();


    public Schema3(CastWrapper castWrapper) {
        this.castWrapper = castWrapper;

        Operation prepareMixerOperation = new PrepareMixerOperation();
        Operation prepareCmOneOperation = new PrepareCmOneOperation();
        Operation prepareCmTwoOperation = new PrepareCmTwoOperation();

        this.operations.add(prepareMixerOperation);
        this.operations.add(prepareCmOneOperation);
        this.operations.add(prepareCmTwoOperation);

        this.operationMap.put(OperationName.PREPARE_MIXER_OPERATION, prepareMixerOperation);
        this.operationMap.put(OperationName.PREPARE_CM_ONE_OPERATION, prepareCmOneOperation);
        this.operationMap.put(OperationName.PREPARE_CM_TWO_OPERATION, prepareCmTwoOperation);

        this.operationMap.put(OperationName.CAST_CM_ONE_OPERATION, prepareCmTwoOperation);
        this.operationMap.put(OperationName.CAST_CM_TWO_OPERATION, prepareCmTwoOperation);
        this.operationMap.put(OperationName.PREPARE_CM_TWO_OPERATION, prepareCmTwoOperation);
        this.operationMap.put(OperationName.PREPARE_CM_TWO_OPERATION, prepareCmTwoOperation);
        this.operationMap.put(OperationName.PREPARE_CM_TWO_OPERATION, prepareCmTwoOperation);
    }

    @Override
    public CastWrapper getCast() {
        return castWrapper;
    }

    @Override
    public Queue<Operation> getOperations() {
        return operations;
    }

    @Override
    public Map<String, Operation> getOperationMap() {
        return operationMap;
    }

    @Override
    public void process() {
        for (Operation operation : operations) {
            operation.activate();
        }
    }

    public static List<? extends  Operation> getInitOperations() {
        return Arrays.asList(new PrepareMixerOperation());
    }
}
