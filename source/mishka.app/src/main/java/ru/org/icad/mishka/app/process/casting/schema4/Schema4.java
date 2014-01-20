package ru.org.icad.mishka.app.process.casting.schema4;

import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.schema3.operation.PrepareMixerOperation;

import java.util.Arrays;
import java.util.List;

public class Schema4 {

    public static List<? extends Operation> getInitOperations() {
        return Arrays.asList(new PrepareMixerOperation());
    }
}
