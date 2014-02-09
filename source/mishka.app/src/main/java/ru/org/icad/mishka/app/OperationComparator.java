package ru.org.icad.mishka.app;

import ru.org.icad.mishka.app.model.PeriodicOperation;

import java.util.Comparator;

public final class OperationComparator {

    public static final Comparator<PeriodicOperation> PERIODIC_OPERATION_COMPARATOR = new Comparator<PeriodicOperation>() {
        @Override
        public int compare(PeriodicOperation o1, PeriodicOperation o2) {
            return o1.getOperationDate().compareTo(o2.getOperationDate());
        }
    };

    private OperationComparator() {
    }
}
