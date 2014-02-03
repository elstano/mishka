package ru.org.icad.mishka.app.process.casting;

import com.google.common.collect.Queues;
import ru.org.icad.mishka.app.model.Cast;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface Schema {

    public List<? extends Operation> getInitOperations();

    public void addToSchemeCasts(CastWrapper castWrapper);

    public Queue<CastWrapper> getQueueCastWrapper();
}
