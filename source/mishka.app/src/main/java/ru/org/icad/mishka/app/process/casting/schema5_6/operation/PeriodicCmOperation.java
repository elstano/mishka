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

public class PeriodicCmOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodicCmOperation.class);

    private final Schema schema;

    public PeriodicCmOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
        long time;
        if (isNeedPeriodic()) {
            PeriodicOperation periodicOperation = schema.getPeriodicOperations().poll();
            time = (long) (periodicOperation.getDurationTime() * 60 * 1000);

            Operation operation = schema.getOperationMap().get(OperationName.PERIODIC_CM);
            operation.setActivationDate(new Date(getActivationDate().getTime() + time));

            schema.getOperations().add(operation);

            LOGGER.debug("Result - customUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                    + ", Operation type: PeriodicCmOperation startDate: "
                    + TimeUtil.convertTimeToString(getActivationDate().getTime())
                    + ", cleanTime: " + time / 60 / 1000);

            return;
        }

        Operation operation = schema.getOperationMap().get(OperationName.PREPARE_CM);

        if (operation.getActivationDate() == null || (getActivationDate() != null && getActivationDate().compareTo(operation.getActivationDate()) == 1)) {
            operation.setActivationDate(new Date(getActivationDate().getTime()));
        }

        operation.setActivationCount(operation.getActivationCount() - 1);

        if (operation.getActivationCount() == 0) {
            schema.getOperations().add(operation);
        }
    }

    private boolean isNeedPeriodic() {
        PeriodicOperation periodicOperation = schema.getPeriodicOperations().peek();
        if (periodicOperation == null) {
            return false;
        }

        DateTime periodicOperationTime = new DateTime(periodicOperation.getOperationDate().getTime());
        DateTime periodicOperationStartShiftTime = periodicOperationTime.minusHours(1).plusHours((periodicOperation.getShift() - 1) * 8);

        return !getActivationDate().before(periodicOperationStartShiftTime.toDate());
    }
}
