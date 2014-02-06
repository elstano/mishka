package ru.org.icad.mishka.app.process.casting;

import java.util.Deque;
import java.util.List;
import java.util.Map;

public interface Schema {

    public List<? extends Operation> getInitOperations();

    public void addToSchemeCasts(CastWrapper castWrapper);

    public Deque<CastWrapper> getDequeCastWrapper();

    public Map<String, Operation> getOperationMap();

    public Deque<CastWrapper> getCastingResult();
}
