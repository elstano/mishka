package ru.org.icad.mishka.app.process.casting.schema4.operation;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.PeriodicOperation;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CleanCollectorOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CleanCollectorOperation.class);


    private Schema schema;

    public CleanCollectorOperation(Schema schema) {
        this.schema = schema;
    }

    @Override
    public void activate() {

        long time = 0;
        if (isNeedClean()) {
            PeriodicOperation periodicOperation = schema.getCleanCollectorOperations().poll();
            time = (long) (periodicOperation.getDurationTime() * 60 * 1000);

            LOGGER.debug("Result - Operation type: CleanCollectorOperation startDate: " + convertTimeToString(getActivationDate().getTime()) + ", cleanTime: " + time / 60 / 1000);
        }

        Operation operation = schema.getOperationMap().get(OperationName.PREPARE_COLLECTOR);
        operation.setActivationDate(new Date(getActivationDate().getTime() + time));

        schema.getOperations().add(operation);
    }

    private boolean isNeedClean() {
        PeriodicOperation periodicOperation = schema.getCleanCollectorOperations().peek();
        if(periodicOperation == null) {
            return false;
        }

        DateTime periodicOperationTime = new DateTime(periodicOperation.getOperationDate().getTime());
        DateTime periodicOperationStartShiftTime = periodicOperationTime.minusHours(1).plusHours((periodicOperation.getShift() - 1) * 8);

        return !getActivationDate().before(periodicOperationStartShiftTime.toDate());
    }

    private String convertTimeToString(long time) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(time);
    }
}
