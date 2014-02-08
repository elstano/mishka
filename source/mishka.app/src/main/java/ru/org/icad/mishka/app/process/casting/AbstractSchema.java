package ru.org.icad.mishka.app.process.casting;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import ru.org.icad.mishka.app.model.PeriodicOperation;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;

public abstract class AbstractSchema implements Schema {

    private Queue<CastWrapper> sourceCastWrappers = Queues.newConcurrentLinkedQueue();
    private Queue<CastWrapper> resultCastWrappers = Queues.newConcurrentLinkedQueue();
    private Map<String, Operation> operationMap = Maps.newConcurrentMap();

    private Queue<Operation> operations;
    private Queue<PeriodicOperation> cleanCollectorOperations;
    private Queue<PeriodicOperation> periodicOperations;

    @Override
    public Queue<CastWrapper> getSourceCastWrappers() {
        return sourceCastWrappers;
    }

    @Override
    public void setSourceCastWrappers(Collection<CastWrapper> sourceCastWrappers) {
        this.sourceCastWrappers.addAll(sourceCastWrappers);
    }

    @Override
    public Queue<CastWrapper> getResultCastWrappers() {
        return resultCastWrappers;
    }

    public void setResultCastWrappers(Queue<CastWrapper> resultCastWrappers) {
        this.resultCastWrappers = resultCastWrappers;
    }

    @Override
    public Map<String, Operation> getOperationMap() {
        return operationMap;
    }

    public void setOperationMap(Map<String, Operation> operationMap) {
        this.operationMap = operationMap;
    }

    @Override
    public Queue<Operation> getOperations() {
        return operations;
    }

    @Override
    public void setOperations(Queue<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public Queue<PeriodicOperation> getCleanCollectorOperations() {
        return cleanCollectorOperations;
    }

    @Override
    public void setCleanCollectorOperations(Queue<PeriodicOperation> cleanCollectorOperations) {
        this.cleanCollectorOperations = cleanCollectorOperations;
    }

    @Override
    public Queue<PeriodicOperation> getPeriodicOperations() {
        return periodicOperations;
    }

    @Override
    public void setPeriodicOperations(Queue<PeriodicOperation> periodicOperations) {
        this.periodicOperations = periodicOperations;
    }
}
