package ru.org.icad.mishka.app.process.casting;

import ru.org.icad.mishka.app.model.Cast;

import java.util.Map;
import java.util.Queue;

public interface Schema {

    public CastWrapper getCast();

    public Queue<Operation> getOperations();

    public Map<String, Operation> getOperationMap();

    public void process();
}
