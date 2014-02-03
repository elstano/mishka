package ru.org.icad.mishka.app.dev;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.Nullable;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.process.casting.schema1_2.Schema1_2;
import ru.org.icad.mishka.app.process.casting.schema3.Schema3;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;
import ru.org.icad.mishka.app.process.casting.schema5_6.Schema5_6;

import javax.naming.InitialContext;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class CastingDev {
    private static final String CAST_QUERY = "SELECT c FROM Cast c  WHERE c.castDate >= :startDate AND c.castDate < :endDate ORDER BY c.castNumber DESC";
    private static final String START_DATE = "01/05/2013";
    private static final String END_DATE = "01/06/2013";

    private static final Map<Integer, Schema> CAST_UNIT_SCHEMA_MAP = ImmutableMap.<Integer, Schema>builder()
            .put(1, new Schema1_2())
            .put(2, new Schema3())
            .put(3, new Schema4())
            .put(4, new Schema5_6())
            .build();

    public void castingProcess() {

        List<Cast> casts = Lists.newArrayList();
        Date startDate = stringToDate(START_DATE);
        Date endDate = stringToDate(END_DATE);
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            TypedQuery<Cast> typedQuery = em.createQuery(CAST_QUERY, Cast.class);
            typedQuery.setParameter("startDate", startDate, TemporalType.DATE);
            typedQuery.setParameter("endDate", endDate, TemporalType.DATE);

            casts = typedQuery.getResultList();
            transaction.commit();

        } catch (Exception e) {
            // empty
        }

        for (Cast cast : casts) {
        Schema schema = CAST_UNIT_SCHEMA_MAP.get(cast.getCastingUnit().getId());
            schema.addToSchemeCasts(cast);
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
