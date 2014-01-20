package ru.org.icad.mishka.app.process.casting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.process.casting.schema1_2.Schema1_2;
import ru.org.icad.mishka.app.process.casting.schema3.Schema3;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;
import ru.org.icad.mishka.app.process.casting.schema5_6.Schema5_6;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class CastingProcess {

   private static Map<Integer, Queue<? extends Operation>> operationMap = Maps.newConcurrentMap();

    static {
        operationMap.put(1, Queues.newConcurrentLinkedQueue(Schema1_2.getInitOperations()));
        operationMap.put(2, Queues.newConcurrentLinkedQueue(Schema1_2.getInitOperations()));
        operationMap.put(3, Queues.newConcurrentLinkedQueue(Schema3.getInitOperations()));
        operationMap.put(4, Queues.newConcurrentLinkedQueue(Schema4.getInitOperations()));
        operationMap.put(5, Queues.newConcurrentLinkedQueue(Schema5_6.getInitOperations()));
        operationMap.put(6, Queues.newConcurrentLinkedQueue(Schema5_6.getInitOperations()));
    }

    private CastingUnit castingUnit;
    private List<Cast> casts;

    public CastingProcess(CastingUnit castingUnit, List<Cast> casts) {
        this.castingUnit = castingUnit;
        this.casts = casts;
    }

    public void start() {
        List<CastWrapper> castWrappers = Lists.newArrayList();

        for (Cast cast : casts) {
            if (cast.getCastingUnit() != null && castingUnit.getId() == cast.getCastingUnit().getId()) {
                castWrappers.add(new CastWrapper(cast));
            }
        }

        Queue<? extends Operation> operations = operationMap.get(castingUnit.getId());

        for(CastWrapper castWrapper: castWrappers) {
           Operation operation = operations.poll();
            operation.init(castWrapper);
            operation.activate();
        }
    }
}
