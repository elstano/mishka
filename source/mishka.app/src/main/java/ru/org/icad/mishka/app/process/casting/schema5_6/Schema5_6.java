package ru.org.icad.mishka.app.process.casting.schema5_6;

import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.schema5_6.operation.PrepareCmOneOperation;
import ru.org.icad.mishka.app.process.casting.schema5_6.operation.PrepareCmTwoOperation;

import java.util.Arrays;
import java.util.List;

public class Schema5_6 {

    public static List<? extends Operation> getInitOperations() {
        return Arrays.asList(new PrepareCmOneOperation(), new PrepareCmTwoOperation());
    }
}
