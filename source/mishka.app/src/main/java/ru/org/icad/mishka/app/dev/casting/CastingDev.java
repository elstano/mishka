package ru.org.icad.mishka.app.dev.casting;

import com.google.common.collect.ImmutableMap;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.process.casting.CastingProcess;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.process.casting.SchemaConfiguration;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;
import ru.org.icad.mishka.app.process.casting.schema5_6.Schema5_6;
import ru.org.icad.mishka.app.process.casting.schema9.Schema9;
import ru.org.icad.mishka.app.util.TimeUtil;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class CastingDev {
    private static final String START_DATE = "01/05/2013 00:00:00";
    private static final String END_DATE = "01/06/2013 00:00:00";

    public void castingProcess() {
        final Map<Integer, Schema> schemaMap = ImmutableMap.<Integer, Schema>builder()
                .put(30, new Schema4(new SchemaConfiguration(2, 30, new int[]{49}, new int[]{46}, new int[]{15})))
                .put(33, new Schema4(new SchemaConfiguration(2, 33, new int[]{52}, new int[]{51}, new int[]{16})))
                .put(22, new Schema5_6(new SchemaConfiguration(2, 22, new int[]{40, 41}, new int[]{41}, new int[]{103})))
                .put(24, new Schema5_6(new SchemaConfiguration(2, 24, new int[]{42, 43}, new int[]{42}, new int[]{59})))
                .put(26, new Schema5_6(new SchemaConfiguration(2, 26, new int[]{44, 45}, new int[]{43}, new int[]{155})))
                .put(28, new Schema5_6(new SchemaConfiguration(2, 28, new int[]{46, 47}, new int[]{44}, new int[]{158})))
                .put(31, new Schema9(new SchemaConfiguration(2, 31, new int[]{50, 51}, new int[]{48, 50, 49}, new int[]{23, 30, 24})))
                .build();

        final Date startDate = TimeUtil.stringToDate(START_DATE);
        final Date endDate = TimeUtil.stringToDate(END_DATE);

        List<Cast> casts30 = getCasts(startDate, endDate, 30);
        CastingProcess castingProcess30 = new CastingProcess(schemaMap.get(30));
        castingProcess30.castingProcess(casts30);

        List<Cast> casts33 = getCasts(startDate, endDate, 33);
        CastingProcess castingProcess33 = new CastingProcess(schemaMap.get(33));
        castingProcess33.castingProcess(casts33);

        List<Cast> casts22 = getCasts(startDate, endDate, 22);
        CastingProcess castingProcess22 = new CastingProcess(schemaMap.get(22));
        castingProcess22.castingProcess(casts22);

        List<Cast> casts24 = getCasts(startDate, endDate, 24);
        CastingProcess castingProcess24 = new CastingProcess(schemaMap.get(24));
        castingProcess24.castingProcess(casts24);

        List<Cast> casts26 = getCasts(startDate, endDate, 26);
        CastingProcess castingProcess26 = new CastingProcess(schemaMap.get(26));
        castingProcess26.castingProcess(casts26);

        List<Cast> casts28 = getCasts(startDate, endDate, 28);
        CastingProcess castingProcess28 = new CastingProcess(schemaMap.get(28));
        castingProcess28.castingProcess(casts28);

        List<Cast> casts31 = getCasts(startDate, endDate, 31);
        CastingProcess castingProcess31 = new CastingProcess(schemaMap.get(31));
        castingProcess31.castingProcess(casts31);
    }

    private List<Cast> getCasts(Date startDate, Date endDate, int castUnitId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Cast> castQuery = em.createNamedQuery("Cast.getCastsForCastingUnitBetweenDate", Cast.class);
        castQuery.setParameter("startDate", startDate, TemporalType.DATE);
        castQuery.setParameter("endDate", endDate, TemporalType.DATE);
        castQuery.setParameter("castingUnitId", castUnitId);

        return castQuery.getResultList();
    }
}
