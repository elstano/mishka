package ru.org.icad.mishka.app.process.casting.schema5_6;

import com.google.common.collect.Queues;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.process.casting.schema5_6.operation.PrepareCmOneOperation;
import ru.org.icad.mishka.app.process.casting.schema5_6.operation.PrepareCmTwoOperation;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class Schema5_6 implements Schema {

    private Deque<CastWrapper> CAST_WRAPPERS = Queues.newLinkedBlockingDeque();

    @Override
    public List<? extends Operation> getInitOperations() {
        return Arrays.asList(new PrepareCmOneOperation(), new PrepareCmTwoOperation());
    }

    @Override
    public void addToSchemeCasts(CastWrapper castWrapper) {
        CAST_WRAPPERS.add(castWrapper);
    }

    @Override
    public Deque<CastWrapper> getDequeCastWrapper() {
        return CAST_WRAPPERS;
    }

    @Override
    public Map<String, Operation> getOperationMap() {
        return null;
    }

    @Override
    public Deque<CastWrapper> getCastingResult() {
        return null;
    }
}
