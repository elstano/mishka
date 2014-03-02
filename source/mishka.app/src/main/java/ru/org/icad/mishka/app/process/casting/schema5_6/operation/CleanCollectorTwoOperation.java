package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.PeriodicOperation;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.util.TimeUtil;

import java.sql.Date;

public class CleanCollectorTwoOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CleanCollectorTwoOperation.class);

    private final Schema schema;

    public CleanCollectorTwoOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {

        long time = 0;
        if (isNeedClean()) {
            PeriodicOperation periodicOperation = schema.getCleanCollectorOperations().poll();
            time = (long) (periodicOperation.getDurationTime() * 60 * 1000);

            LOGGER.debug("Result - castingUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                    + ", Operation type: CleanCollectorTwoOperation startDate: "
                    + TimeUtil.convertTimeToString(getActivationDate().getTime()) + ", cleanTime: " + time / 60 / 1000);
        }

        Operation operation = schema.getOperationMap().get(OperationName.PREPARE_COLLECTOR_TWO);
        operation.setActivationDate(new Date(getActivationDate().getTime() + time));

        schema.getOperations().add(operation);
    }

    private boolean isNeedClean() {
        PeriodicOperation periodicOperation = schema.getCleanCollectorOperations().peek();
        if (periodicOperation == null) {
            return false;
        }

        if(periodicOperation.getCastingUnitCollector().getId() != schema.getSchemaConfiguration().getCastingUnitCollectorIds()[1]) {
            return false;
        }

        DateTime periodicOperationTime = new DateTime(periodicOperation.getOperationDate().getTime());
        DateTime periodicOperationStartShiftTime = periodicOperationTime.minusHours(1).plusHours((periodicOperation.getShift() - 1) * 8);

        return !getActivationDate().before(periodicOperationStartShiftTime.toDate());
    }

}
