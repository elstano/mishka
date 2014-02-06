package ru.org.icad.mishka.app.process.casting;

import com.google.common.collect.Queues;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;

import java.util.Queue;

public class CastingUnitWrapper {

    private CastingUnit castingUnit;

    private Queue<Operation> operations = Queues.newConcurrentLinkedQueue();

    private Schema schema = new Schema4();


    public CastingUnitWrapper(CastingUnit castingUnit) {
        this.castingUnit = castingUnit;
    }

    public CastingUnit getCastingUnit() {
        return castingUnit;
    }

    public void setCastingUnit(CastingUnit castingUnit) {
        this.castingUnit = castingUnit;
    }

    public Queue<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Queue<Operation> operations) {
        this.operations = operations;
    }

    public void castingProcess() {
        operations.addAll(schema.getInitOperations());
        while (!operations.isEmpty()) {
            operations.poll().activate();
        }
    }
}
