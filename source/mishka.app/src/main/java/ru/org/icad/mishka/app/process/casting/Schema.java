package ru.org.icad.mishka.app.process.casting;

import ru.org.icad.mishka.app.model.PeriodicOperation;

import java.util.*;

public interface Schema {

    public List<? extends Operation> getInitOperations();

    public Queue<CastWrapper> getSourceCastWrappers();

    public void setSourceCastWrappers(Collection<CastWrapper> sourceCastWrappers);

    public Queue<CastWrapper> getSourceOneCastWrappers();

    public void setSourceOneCastWrappers(Collection<CastWrapper> sourceOneCastWrappers);

    public Queue<CastWrapper> getSourceTwoCastWrappers();

    public void setSourceTwoCastWrappers(Collection<CastWrapper> sourceTwoCastWrappers);

    public Deque<CastWrapper> getResultCastWrappers();

    public Map<String, Operation> getOperationMap();

    public Queue<Operation> getOperations();

    public void setOperations(Queue<Operation> operations);

    public Queue<PeriodicOperation> getCleanCollectorOperations();

    public void setCleanCollectorOperations(Queue<PeriodicOperation> cleanCollectorOperations);

    public Queue<PeriodicOperation> getPeriodicOperations();

    public void setPeriodicOperations(Queue<PeriodicOperation> periodicOperations);

    public SchemaConfiguration getSchemaConfiguration();
}
