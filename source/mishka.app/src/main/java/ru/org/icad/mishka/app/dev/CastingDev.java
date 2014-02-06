package ru.org.icad.mishka.app.dev;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.Nullable;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;
import ru.org.icad.mishka.app.process.casting.schema5.Schema5;
import ru.org.icad.mishka.app.process.casting.schema6.Schema6;
import ru.org.icad.mishka.app.process.casting.schema9.Schema9;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class CastingDev {
    private static final String START_DATE = "01/05/2013";
    private static final String END_DATE = "01/06/2013";

    private static final List<Integer> SAZ_CAST_UNIT = Lists.newArrayList(22, 24, 26,28, 30, 31, 33);

    private static final Map<Integer, Schema> CAST_UNIT_SCHEMA_MAP = ImmutableMap.<Integer, Schema>builder()
            .put(30, new Schema4())
            .put(33, new Schema4())
            .put(26, new Schema5())
            .put(28, new Schema5())
            .put(22, new Schema6())
            .put(24, new Schema6())
            .put(31, new Schema9())

            .build();

    @PersistenceContext(unitName = "MishkaService")
    private EntityManager em;

    public void castingProcess() {
        Date startDate = stringToDate(START_DATE);
        Date endDate = stringToDate(END_DATE);

        TypedQuery<Cast> typedQuery = em.createNamedQuery("Cast.getCastsBetweenDate", Cast.class);
        typedQuery.setParameter("startDate", startDate, TemporalType.DATE);
        typedQuery.setParameter("endDate", endDate, TemporalType.DATE);
        List<Cast> casts = typedQuery.getResultList();

        for (Cast cast : casts) {
            Schema schema = CAST_UNIT_SCHEMA_MAP.get(cast.getCastingUnit().getId());
            schema.addToSchemeCasts(new CastWrapper(cast));
        }

        for (Schema schema : CAST_UNIT_SCHEMA_MAP.values()) {
            final Operation operation = schema.getInitOperations().iterator().next();
            operation.setCastWrappers(schema.getDequeCastWrapper());
            operation.activate();
        }
    }

    @Nullable
    private Date stringToDate(String string) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return new Date(format.parse(string).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
