package ru.org.icad.mishka.app.process.casting.schema4.operation;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.PeriodicOperation;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

import java.sql.Date;

public class PeriodicCMOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodicCMOperation.class);


    private Schema schema;

    public PeriodicCMOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {
        double time = 0;
        if (isNeedPeriodic()) {
            PeriodicOperation periodicOperation = schema.getPeriodicOperations().poll();
            time = periodicOperation.getDurationTime() / 60;

            LOGGER.debug("Operation type: PeriodicCMOperation startDate: " + getActivationDate() + ", cleanTime" + time);
        }

        Operation operation = schema.getOperationMap().get(OperationName.PREPARE_CM);
        operation.setActivationDate(new Date(getActivationDate().getTime() + (long) (time * 3600 * 1000)));

        schema.getOperations().add(operation);
    }

    private boolean isNeedPeriodic() {
        PeriodicOperation periodicOperation = schema.getPeriodicOperations().peek();
        if(periodicOperation == null) {
            return false;
        }

        DateTime periodicOperationTime = new DateTime(periodicOperation.getOperationDate().getTime());
        DateTime periodicOperationStartShiftTime = periodicOperationTime.minusHours(1).plusHours((periodicOperation.getShift() - 1) * 8);

        return !getActivationDate().before(periodicOperationStartShiftTime.toDate());
    }
}
