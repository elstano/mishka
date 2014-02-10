package ru.org.icad.mishka.app.process.casting.schema1_2;

import ru.org.icad.mishka.app.process.casting.AbstractSchema;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.SchemaConfiguration;
import ru.org.icad.mishka.app.process.casting.schema1_2.operation.PrepareDistrOperation;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class Schema1_2 extends AbstractSchema {

    public Schema1_2() {
    }

    @Override
    public List<? extends Operation> getInitOperations() {
        return Arrays.asList(new PrepareDistrOperation());
    }


    @Override
    public Map<String, Operation> getOperationMap() {
        return null;
    }

    @Override
    public SchemaConfiguration getSchemaConfiguration() {
        return null;
    }

    @Override
    public Deque<CastWrapper> getResultCastWrappers() {
        return null;
    }

}
