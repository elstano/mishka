package ru.org.icad.mishka.app.process.casting.schema1_2;

import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.schema1_2.operation.PrepareDistrOperation;

import java.util.Arrays;
import java.util.List;

public class Schema1_2 {

    public static List<? extends  Operation> getInitOperations() {
      return Arrays.asList(new PrepareDistrOperation());
    }
}
