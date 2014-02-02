package ru.org.icad.mishka.app.dev;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastElectrolizer;
import ru.org.icad.mishka.app.model.ElectrolizerPrognosis;
import ru.org.icad.mishka.app.process.raw.RestrictionByRaw;

import javax.naming.InitialContext;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RestrictionDev {

    private static final String CAST_QUERY = "SELECT c FROM Cast c  WHERE c.date >= ?1 AND c.date < ?2 ORDER BY c.castNumber DESC";
    private static final String ELECTROLIZER_QUERY = "SELECT e FROM ElectrolizerPrognosis e  WHERE e.date >= ?1 AND e.date < ?2 ORDER BY e.date DESC";
    private static final String START_DATE ="01/05/2013";
    private static final String END_DATE = "01/06/2013";

    private static <T> void saveOrUpdate(List<T> data) {
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            if (data != null)
                for (T plant : data) {
                    em.merge(plant);
                }
//            em.flush();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restrictionProcess() {

        List<Cast> casts = Lists.newArrayList();
        Date startDate = stringToDate(START_DATE);
        Date endDate = stringToDate(END_DATE);
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            TypedQuery<Cast> typedQuery = em.createQuery(CAST_QUERY, Cast.class);
            typedQuery.setParameter(1, startDate, TemporalType.DATE);
            typedQuery.setParameter(2, endDate, TemporalType.DATE);

            casts = typedQuery.getResultList();
            transaction.commit();

        } catch (Exception e) {
            // empty
        }

        List<ElectrolizerPrognosis> electrolizerPrognosises = Lists.newArrayList();
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            TypedQuery<ElectrolizerPrognosis> typedQuery = em.createQuery(ELECTROLIZER_QUERY, ElectrolizerPrognosis.class);
            typedQuery.setParameter(1, startDate, TemporalType.DATE);
            typedQuery.setParameter(2, endDate, TemporalType.DATE);

            electrolizerPrognosises = typedQuery.getResultList();
            transaction.commit();

        } catch (Exception e) {
            // empty
        }

        List<CastElectrolizer> castElectrolizers = Lists.newArrayList();

        Map<Cast, List<ElectrolizerPrognosis>> result = RestrictionByRaw.checkRestriction(casts, electrolizerPrognosises);

        Set<Cast> resultCasts = result.keySet();
        for (Cast cast : resultCasts) {
            List<ElectrolizerPrognosis> tempElectrolizerPrognosises = result.get(cast);
            if (tempElectrolizerPrognosises == null || tempElectrolizerPrognosises.isEmpty()) {
                castElectrolizers.add(new CastElectrolizer(cast, false));
            } else {
                castElectrolizers.add(new CastElectrolizer(cast, true));

            }

        }

        saveOrUpdate(castElectrolizers);

    }

    @Nullable
    private Date stringToDate(String string) {
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return new Date(format.parse(string).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
