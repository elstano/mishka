package ru.org.icad.mishka.app.dev;

import com.google.common.collect.Lists;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastElectrolizer;
import ru.org.icad.mishka.app.model.ElectrolizerPrognosis;

import javax.naming.InitialContext;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.sql.Date;
import java.util.List;

public class RestrictionDev {

    private static final String CAST_QUERY = "SELECT c FROM Cast c  WHERE c.date >= ?1 AND c.date <= ?2 ORDER BY c.castNumber DESC";
    private static final String ELECTROLIZER_QUERY = "SELECT e FROM ElectrolizerPrognosis e  WHERE e.date >= ?1 AND e.date <= ?2 ORDER BY e.date DESC";
    private static final Date START_DATE = new Date(1367362550L);
    private static final Date END_DATE = new Date(1370030399L);

    private static <T> void persist(List<T> data) {
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            if (data != null)
                for (T plant : data) {
                    em.persist(plant);
                }
            em.flush();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restrictionProcess() {

        List<Cast> casts = Lists.newArrayList();
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            TypedQuery<Cast> typedQuery = em.createQuery(CAST_QUERY, Cast.class);
            typedQuery.setParameter(1, START_DATE, TemporalType.DATE);
            typedQuery.setParameter(2, END_DATE, TemporalType.DATE);

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
            typedQuery.setParameter(1, START_DATE, TemporalType.DATE);
            typedQuery.setParameter(2, END_DATE, TemporalType.DATE);

            electrolizerPrognosises = typedQuery.getResultList();
            transaction.commit();

        } catch (Exception e) {
            // empty
        }

        List<CastElectrolizer> castElectrolizers = Lists.newArrayList();
        for (Cast cast : casts) {
            castElectrolizers.add(new CastElectrolizer(cast, true));
        }

        persist(castElectrolizers);

//        Map<Order, List<ElectrolizerPrognosis>> result = RestrictionByRaw.checkRestriction(, electrolizerPrognosises);
    }
}
