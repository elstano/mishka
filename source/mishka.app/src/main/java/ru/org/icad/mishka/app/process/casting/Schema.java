package ru.org.icad.mishka.app.process.casting;

import java.util.List;
import java.util.Queue;

public interface Schema {

    public List<? extends Operation> getInitOperations();

    public void addToSchemeCasts(CastWrapper castWrapper);

    public Queue<CastWrapper> getQueueCastWrapper();
}
