package ru.org.icad.mishka.app.process.casting.schema5_6;

import com.google.common.collect.Queues;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.process.casting.schema5_6.operation.PrepareCmOneOperation;
import ru.org.icad.mishka.app.process.casting.schema5_6.operation.PrepareCmTwoOperation;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Schema5_6 implements Schema {

    private static final Queue<CastWrapper> CAST_WRAPPERS = Queues.newSynchronousQueue();

    @Override
    public List<? extends Operation> getInitOperations() {
        return Arrays.asList(new PrepareCmOneOperation(), new PrepareCmTwoOperation());
    }

    @Override
    public void addToSchemeCasts(CastWrapper castWrapper) {
        CAST_WRAPPERS.add(castWrapper);
    }

    @Override
    public Queue<CastWrapper> getQueueCastWrapper() {
        return CAST_WRAPPERS;
    }
}
