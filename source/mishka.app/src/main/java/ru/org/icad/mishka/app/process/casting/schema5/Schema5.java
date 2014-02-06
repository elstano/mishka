package ru.org.icad.mishka.app.process.casting.schema5;

import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

import java.util.Deque;
import java.util.List;
import java.util.Map;

public class Schema5 implements Schema {
    @Override
    public List<? extends Operation> getInitOperations() {
        return null;
    }

    @Override
    public void addToSchemeCasts(CastWrapper castWrapper) {

    }

    @Override
    public Deque<CastWrapper> getDequeCastWrapper() {
        return null;
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
