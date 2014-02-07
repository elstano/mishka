package ru.org.icad.mishka.app.process.casting;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface Schema {

    public List<? extends Operation> getInitOperations();

    public Queue<CastWrapper> getSourceCastWrappers();

    public void setSourceCastWrappers(Collection<CastWrapper> sourceCastWrappers);

    public Queue<CastWrapper> getResultCastWrappers();

    public Map<String, Operation> getOperationMap();

    public Queue<Operation> getOperations();

    public void setOperations(Queue<Operation> operations);
}
