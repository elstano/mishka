package ru.org.icad.mishka.app.cache;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.PrepareTimeConst;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PrepareTimeConstCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareTimeConstCache.class);

    private static final PrepareTimeConstCache PREPARE_TIME_CONST_CACHE = new PrepareTimeConstCache();

    public final Map<Integer, PrepareTimeConst> prepareTimeConstMap = Maps.newHashMap();


    private PrepareTimeConstCache() {
        List prepareTimeConsts = Collections.emptyList();

        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();

            TypedQuery plantTypedQuery = em.createQuery("SELECT p FROM PrepareTimeConst p", PrepareTimeConst.class);
            prepareTimeConsts = plantTypedQuery.getResultList();
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("Can't get table: PrepareTimeConst", e);
        }

        for (Object prepareTimeConst : prepareTimeConsts) {
            if (prepareTimeConst instanceof PrepareTimeConst) {
                prepareTimeConstMap.put(((PrepareTimeConst) prepareTimeConst).getId(), (PrepareTimeConst) prepareTimeConst);
            }
        }
    }


    public static PrepareTimeConstCache getPrepareTimeConstCache() {
        return PREPARE_TIME_CONST_CACHE;
    }

    public Map<Integer, PrepareTimeConst> getPrepareTimeConstMap() {
        return prepareTimeConstMap;
    }

}
