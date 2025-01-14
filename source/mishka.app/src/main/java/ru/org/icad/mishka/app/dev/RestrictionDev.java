package ru.org.icad.mishka.app.dev;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastElectrolizer;
import ru.org.icad.mishka.app.model.ElectrolizerPrognosis;
import ru.org.icad.mishka.app.process.cob.RestrictionByCob;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(RestrictionDev.class);

    private static final String CAST_QUERY = "SELECT c FROM Cast c  WHERE c.castDate >= :startDate AND c.castDate < :endDate ORDER BY c.castNumber DESC";
    private static final String ELECTROLIZER_QUERY = "SELECT e FROM ElectrolizerPrognosis e  WHERE e.prognosDate >= :startDate AND e.prognosDate < :endDate ORDER BY e.prognosDate DESC";
    private static final String START_DATE = "01/05/2013";
    private static final String END_DATE = "01/06/2013";

    private static <T> void saveOrUpdate(List<T> data) {
        final long startTime = System.currentTimeMillis();
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            if (data != null)
                for (T t : data) {
                    em.merge(t);
                }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Save or update eletrilizer: " + (System.currentTimeMillis() - startTime));
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
            typedQuery.setParameter("startDate", startDate, TemporalType.DATE);
            typedQuery.setParameter("endDate", endDate, TemporalType.DATE);

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
            typedQuery.setParameter("startDate", startDate, TemporalType.DATE);
            typedQuery.setParameter("endDate", endDate, TemporalType.DATE);

            electrolizerPrognosises = typedQuery.getResultList();
            transaction.commit();

        } catch (Exception e) {
            // empty
        }

        List<CastElectrolizer> castElectrolizers = Lists.newArrayList();

        final long startTime = System.currentTimeMillis();
        Map<Cast, List<ElectrolizerPrognosis>> result = RestrictionByCob.checkRestriction(casts, electrolizerPrognosises);
        LOGGER.info("Check restriction time: " + (System.currentTimeMillis() - startTime));

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
        return null;
    }
}
