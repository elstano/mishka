package ru.org.icad.mishka.app.process.casting.schema5_6.operation;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.OperationName;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.Operation;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.util.CastUtil;
import ru.org.icad.mishka.app.util.GowkCastUtil;
import ru.org.icad.mishka.app.util.TimeUtil;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CastCmCollectorOneOperation extends Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastCmCollectorOneOperation.class);
    private static final ArrayList<Integer> CUSTING_UNITS_HOMOGEN = Lists.newArrayList(26, 28);

    private final Schema schema;

    public CastCmCollectorOneOperation(Schema schema, int activationMaxCount, int activationCount) {
        this.schema = schema;

        this.setActivationMaxCount(activationMaxCount);
        this.setActivationCount(activationCount);
    }

    @Override
    public void activate() {
        final CastWrapper castWrapper = getCastWrapper();
        schema.getResultCastWrappers().add(castWrapper);

        long time = 0;
        long homogenTime = 0;
        final Cast cast = castWrapper.getCast();
        final CustomerOrder customerOrder = cast.getCustomerOrder();
        if ("flush".equals(customerOrder.getId())) {
            time = castWrapper.getFlushCastTime();
        } else {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();

            final Product product = customerOrder.getProduct();
            int markId = product.getMark().getId();
            Query castingSpeedQuery = em.createNativeQuery("SELECT * from CASTING_SPEED cs where cs.MOULD_ID = " + schema.getSchemaConfiguration().getMouldIds()[0]
                    + " and cs.MARK_ID in (SELECT m.mark_id FROM MARK m "
                    + " CONNECT BY PRIOR m.parent_mark_id = m.mark_id START WITH m.mark_id = " + markId
                    + ") and ROWNUM = 1", CastingSpeed.class);

            CastingSpeed castingSpeed = (CastingSpeed) castingSpeedQuery.getSingleResult();

            final int formId = product.getForm().getId();
            if (castWrapper.getBlankCountTwo() != null) {
                double lengthBlank = 0;
                try {
                    lengthBlank = GowkCastUtil.getLengthBlank(castWrapper);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                time = (long) (lengthBlank / castingSpeed.getSpeed() * 60 * 1000);
            } else {
                if (Form.SLAB == formId || Form.BILLET == formId) {
                    time = (long) (CastUtil.getLengthBlank(cast) / castingSpeed.getSpeed() * 60 * 1000);
                }
            }

            if (CUSTING_UNITS_HOMOGEN.contains(schema.getSchemaConfiguration().getCastingUnitId()) && Form.BILLET == formId) {
                TypedQuery<HomogenizationLine> homogenizationLineQuery
                        = em.createNamedQuery("HomogenizationLine.getHomogenizationLineByIdAndDiameter", HomogenizationLine.class);
                homogenizationLineQuery.setParameter("castingUnitHomogenCuttingLineId", 1);
                homogenizationLineQuery.setParameter("diameter", customerOrder.getDiameter());

                HomogenizationLine homogenizationLine = homogenizationLineQuery.getSingleResult();

                TypedQuery<CuttingLine> cuttingLineQuery
                        = em.createNamedQuery("CuttingLine.getCuttingLineByIdAndDiameter", CuttingLine.class);
                cuttingLineQuery.setParameter("castingUnitHomogenCuttingLineId", 1);
                cuttingLineQuery.setParameter("diameter", customerOrder.getDiameter());

                CuttingLine cuttingLine = cuttingLineQuery.getSingleResult();

                int cellBetweenBlanks = customerOrder.getDiameter() >= 228 ? 2 : 1;

                long previewsTimeHomogen = 0;
                long previewsTimeCutting = 0;
                long timeHomogen = 0;
                long timeCutting = 0;
                for (int i = 1; i <= cast.getBlankCount(); i++) {
                    timeHomogen = previewsTimeHomogen + homogenizationLine.getLoadTime() * 1000 * cellBetweenBlanks;
                    previewsTimeHomogen = timeHomogen;

                    timeCutting = Math.max(timeHomogen, previewsTimeCutting) + cuttingLine.getSpeed() * 1000 * (cast.getIngotInBlankCount() + 1);
                    previewsTimeCutting = timeHomogen;
                }
                if (castWrapper.getBlankCountTwo() != null) {
                    for (int i = 1; i <= castWrapper.getBlankCountTwo(); i++) {
                        timeHomogen = previewsTimeHomogen + homogenizationLine.getLoadTime() * 1000 * 2;
                        previewsTimeHomogen = timeHomogen;

                        timeCutting = Math.max(timeHomogen, previewsTimeCutting) + cuttingLine.getSpeed() * 1000 * (castWrapper.getIngotInBlankCountTwo() + 1);
                        previewsTimeCutting = timeHomogen;
                    }
                }

                homogenTime = timeCutting;
            }
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();
        Query castingUnitRepairsQuery = em.createNativeQuery("SELECT * from CU_REPAIR cur where cur.CAST_MACH_ID = "
                +  schema.getSchemaConfiguration().getCastingUnitCastingMachineIds()[0] , CastingUnitRepair.class);
        List castingUnitRepairs = castingUnitRepairsQuery.getResultList();
        if(castingUnitRepairs != null) {
            for(Object object : castingUnitRepairs) {
                CastingUnitRepair castingUnitRepair = (CastingUnitRepair )object;
                if(castingUnitRepair.getTimeEnd().before(getActivationDate())) {
                    continue;
                }

                if(castingUnitRepair.getTimeStart().after(new Date(getActivationDate().getTime() + time /2))) {
                    continue;
                }

                setActivationDate(castingUnitRepair.getTimeEnd());

                LOGGER.debug("Result - castingUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                        + ", Operation type: CastCmCollectorOneOperation Repair, customer order id: " + customerOrder.getId());
            }
        }

        castWrapper.setCastTime(time);

        final Date startCastDate = getActivationDate();
        final Date endCastDate = new Date(startCastDate.getTime() + time);

        Operation cleanCollectorOneOperation = schema.getOperationMap().get(OperationName.CLEAN_COLLECTOR_ONE);
        cleanCollectorOneOperation.setActivationDate(endCastDate);
        Operation periodicCmOperation = schema.getOperationMap().get(OperationName.PERIODIC_CM);
        periodicCmOperation.setActivationDate(endCastDate);

        Operation remouldCmOperation = schema.getOperationMap().get(OperationName.REMOULD_CM);
        remouldCmOperation.setActivationDate(endCastDate);
        remouldCmOperation.setNextId(2);

        final Queue<Operation> operations = schema.getOperations();
        operations.add(remouldCmOperation);
        operations.add(periodicCmOperation);
        operations.add(cleanCollectorOneOperation);

        setActivationCount(getActivationMaxCount());

        LOGGER.debug("Result - castingUnitId: " + schema.getSchemaConfiguration().getCastingUnitId()
                + ", Operation type: CastCmCollectorOneOperation, customer order id: " + customerOrder.getId()
                + ", startDate: " + TimeUtil.convertTimeToString(startCastDate.getTime() - castWrapper.getPrepareCollectorTime())
                + ", startCastDate: " + TimeUtil.convertTimeToString(startCastDate.getTime())
                + ", endCastDate: " + TimeUtil.convertTimeToString(endCastDate.getTime())
                + ", prepareTime: " + castWrapper.getPrepareCollectorTime() / 60 / 1000
                + ", castTime: " + castWrapper.getCastTime() / 60 / 1000
                + (homogenTime == 0 ? "": ", homogenTime: " + homogenTime / 60 / 1000)
                + (isGowk(castWrapper) ? ", gowk: " + isGowk(castWrapper): "")
        );
    }

    private boolean isGowk(CastWrapper castWrapper) {
        return castWrapper.getLengthTwo() != null
                || castWrapper.getBlankCountTwo() != null
                || castWrapper.getIngotInBlankCountTwo() != null;
    }
}
