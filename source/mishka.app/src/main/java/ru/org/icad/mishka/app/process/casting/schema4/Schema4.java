package ru.org.icad.mishka.app.process.casting.schema4;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.process.casting.schema4.operation.*;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class Schema4 implements Schema {

    private Deque<CastWrapper> castWrappers = Queues.newLinkedBlockingDeque();

    private Deque<CastWrapper> castingResult = Queues.newLinkedBlockingDeque();

    private Map<String, Operation> operationMap = Maps.newConcurrentMap();


    public Schema4() {
        CleanCollectorOperation cleanCollectorOperation = new CleanCollectorOperation(this);
        PrepareCollectorOperation prepareCollectorOperation = new PrepareCollectorOperation(this);
        CastCmOperation castCmOperation = new CastCmOperation(this);
        PeriodicCMOperation periodicCmOperation = new PeriodicCMOperation(this);
        PrepareCmOperation prepareCmOperation = new PrepareCmOperation(this);

        operationMap.put(OperationName.CLEAN_COLLECTOR, cleanCollectorOperation);
        operationMap.put(OperationName.PREPARE_COLLECTOR, prepareCollectorOperation);
        operationMap.put(OperationName.CAST_CM, castCmOperation);
        operationMap.put(OperationName.PERIODIC_CM, periodicCmOperation);
        operationMap.put(OperationName.PREPARE_CM, prepareCmOperation);
    }

    @Override
    public List<? extends Operation> getInitOperations() {
        return Arrays.asList(operationMap.get("cleanMixer"));
    }

    @Override
    public void addToSchemeCasts(CastWrapper castWrapper) {
        castWrappers.add(castWrapper);

    }

    @Override
    public Deque<CastWrapper> getDequeCastWrapper() {
        return castWrappers;
    }

    @Override
    public Map<String, Operation> getOperationMap() {
        return operationMap;
    }

    @Override
    public Deque<CastWrapper> getCastingResult() {
        return castingResult;
    }

}
