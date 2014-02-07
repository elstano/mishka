package ru.org.icad.mishka.app.process.casting;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Queues;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastingUnit;

import javax.persistence.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class CastingUnitWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitWrapper.class);

    private static final String START_DATE = "01/05/2013";
    private static final String END_DATE = "01/06/2013";

    private static final Function<Cast, CastWrapper> CAST_WRAPPER_FUNCTION = new Function<Cast, CastWrapper>() {
        @Override
        public CastWrapper apply(Cast cast) {
            return new CastWrapper(cast);
        }
    };

    private Queue<Operation> operations = Queues.newConcurrentLinkedQueue();

    private CastingUnit castingUnit;
    private Schema schema;

    public CastingUnitWrapper() {
    }

    public CastingUnitWrapper(CastingUnit castingUnit, Schema schema) {
        this.castingUnit = castingUnit;
        this.schema = schema;
    }

    public CastingUnit getCastingUnit() {
        return castingUnit;
    }

    public void setCastingUnit(CastingUnit castingUnit) {
        this.castingUnit = castingUnit;
    }

    public Queue<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Queue<Operation> operations) {
        this.operations = operations;
    }

    public void castingProcess() {
        Date startDate = stringToDate(START_DATE);
        Date endDate = stringToDate(END_DATE);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Cast> typedQuery = em.createNamedQuery("Cast.getCastsForCastingUnitBetweenDate", Cast.class);
        typedQuery.setParameter("startDate", startDate, TemporalType.DATE);
        typedQuery.setParameter("endDate", endDate, TemporalType.DATE);
        typedQuery.setParameter("castingUnitId", castingUnit.getId());

        List<Cast> casts = typedQuery.getResultList();

        Collection<CastWrapper> castWrappers = Collections2.transform(casts, CAST_WRAPPER_FUNCTION);

        schema.setOperations(operations);
        schema.setSourceCastWrappers(castWrappers);

        for (Operation operation : schema.getInitOperations()) {
            operation.setActivationDate(startDate);
        }

        operations.addAll(schema.getInitOperations());

        final long startTime = System.currentTimeMillis();

        while (!operations.isEmpty()) {
            operations.poll().activate();
        }

        LOGGER.debug("Casting time: " + (System.currentTimeMillis() - startTime));
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
