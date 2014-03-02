package ru.org.icad.mishka.app.process.calculation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.loader.db.CastingUnitLoader;
import ru.org.icad.mishka.app.loader.db.CustomerOrderLoaded;
import ru.org.icad.mishka.app.loader.db.DBLoader;
import ru.org.icad.mishka.app.loader.db.GroupCustomerOrderLoader;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.CastingProcess;
import ru.org.icad.mishka.app.process.casting.Schema;
import ru.org.icad.mishka.app.process.casting.SchemaConfiguration;
import ru.org.icad.mishka.app.process.casting.schema4.Schema4;
import ru.org.icad.mishka.app.process.casting.schema5_6.Schema5_6;
import ru.org.icad.mishka.app.process.casting.schema9.Schema9;
import ru.org.icad.mishka.app.util.TimeCalculationUtils;

import javax.persistence.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by @author Ivan Solovyev.
 */
public class ScheduleCalculator
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleCalculator.class);

    public ScheduleCalculator()
    {
    }

    public void calculateSchedule() throws SQLException
    {
        final Map<Integer, Schema> schemaMap = ImmutableMap.<Integer, Schema>builder()
                .put(30, new Schema4(new SchemaConfiguration(2, 30, new int[]{49}, new int[]{46}, new int[] {15})))
                .put(33, new Schema4(new SchemaConfiguration(2, 33, new int[]{52}, new int[]{51}, new int[] {16})))
                .put(22, new Schema5_6(new SchemaConfiguration(2, 22, new int[]{40, 41}, new int[]{41}, new int[] {103})))
                .put(24, new Schema5_6(new SchemaConfiguration(2, 24, new int[]{42, 43}, new int[]{42}, new int[] {59})))
                .put(26, new Schema5_6(new SchemaConfiguration(2, 26, new int[]{44, 45}, new int[]{43}, new int[] {155})))
                .put(28, new Schema5_6(new SchemaConfiguration(2, 28, new int[]{46, 47}, new int[]{44}, new int[] {158})))
                .put(31, new Schema9(new SchemaConfiguration(2, 31, new int[]{50, 51}, new int[]{48, 50, 49}, new int[] {23, 30, 24})))
                .build();

        Map<Integer, List<Cast>> casts = new HashMap<>();

        //Division of Orders into Groups
        DBLoader<GroupCustomerOrder> groupCULoader = new GroupCustomerOrderLoader();
        Collection<GroupCustomerOrder> groupCustomerOrders = groupCULoader.load();

        //Map of Customer Orders
        DBLoader<CustomerOrder> customerOrderDBLoader = new CustomerOrderLoaded();
        Map<Object, CustomerOrder> customerOrderMap = customerOrderDBLoader.loadMap();

        if (LOGGER.isDebugEnabled())
        {
            for (GroupCustomerOrder gco : groupCustomerOrders)
            {
                LOGGER.debug("Group of Customer Orders: " + gco.getGroupId());
                for (String coId : gco.getCustomerOrderIds())
                {
                    LOGGER.debug("    Customer Order: " + coId + ", Due Date: " + customerOrderMap.get(coId).getDueDate());
                }
            }
        }

        //Collection of Casting Units
        DBLoader<CastingUnit> castingUnitDBLoader = new CastingUnitLoader();
        Collection<CastingUnit> castingUnitList = castingUnitDBLoader.load();
        for (CastingUnit cu : castingUnitList)
        {
            cu.setStartTime(new Date(2013, 5, 1));
            casts.put(cu.getId(), new ArrayList<Cast>());
        }

        //Last assigned Product for Casting Unit to calculate next assigned Group of Orders
        Map<Integer, Integer> castingUnitLastProduct = new HashMap<>();
        //Last mounted Mould on Casting Unit
        Map<Integer, Integer> castingUnitMould = new HashMap<>();

        for (CastingUnit cu : castingUnitList)
        {
            castingUnitLastProduct.put(cu.getId(), cu.getPreviousProductId());
            //TODO: load from DB, current value is blank
            castingUnitMould.put(cu.getId(), 0);
        }

        Schedule schedule = new Schedule();
        schedule.setCustomerOrders(customerOrderMap);
        schedule.setGroupCustomerOrders(groupCustomerOrders);
        schedule.setCastingUnits(castingUnitList);
        schedule.setCastingUnitMould(castingUnitMould);
        schedule.setUnassignedGroupCustomerOrders(groupCustomerOrders);

        //TODO:while
        if (schedule.getUnassignedGroupCustomerOrders().size() > 0)
        {
            CastingUnit castingUnitForAssign = null;
            //get first available cast unit
            for (CastingUnit castingUnit : schedule.getCastingUnits())
            {
                if (castingUnitForAssign == null)
                {
                    castingUnitForAssign = castingUnit;
                }
                else
                {
                    if (ObjectUtils.compare(castingUnit.getStartTime(), castingUnitForAssign.getStartTime()) < 0)
                    {
                        castingUnitForAssign = castingUnit;
                    }
                }
            }

            assert castingUnitForAssign != null;

            int minimalPreparationTime = 0;
            GroupCustomerOrder assignedGroupCustomerOrder = null;

            //get most convenient group of orders
            for (GroupCustomerOrder gco : groupCustomerOrders)
            {
                int preparationTime = TimeCalculationUtils.getPreparationTime(castingUnitForAssign.getId(),
                        castingUnitLastProduct.get(castingUnitForAssign.getId()),
                        gco.getCustomerOrderIds().iterator().next(),
                        castingUnitMould.get(castingUnitForAssign.getId()));

                if (assignedGroupCustomerOrder == null)
                {
                    minimalPreparationTime = preparationTime;
                    assignedGroupCustomerOrder = gco;
                }
                else
                {
                    if (preparationTime < minimalPreparationTime)
                    {
                        minimalPreparationTime = preparationTime;
                        assignedGroupCustomerOrder = gco;
                    }
                }
            }

            assert assignedGroupCustomerOrder != null;

            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Preparation Time for " + castingUnitForAssign.getId() + " on " +
                        assignedGroupCustomerOrder.getGroupId() + " is " + minimalPreparationTime);
            }

            //generate Casts, assign order, recalculate time

            for (String assignedOrderId : assignedGroupCustomerOrder.getCustomerOrderIds())
            {
                CustomerOrder customerOrder = schedule.getCustomerOrders().get(assignedOrderId);

                //Calculation number of Casts for current Order
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
                EntityManager em = emf.createEntityManager();
                TypedQuery<CastingUnitCollector> castingUnitCollectorQuery = em.createNamedQuery("CastingUnitCollector.findByCastingUnit", CastingUnitCollector.class);
                castingUnitCollectorQuery.setParameter("castUnitId", castingUnitForAssign.getId());
                List<CastingUnitCollector> castingUnitCollectors = castingUnitCollectorQuery.getResultList();
                int collectorTonnage = 0;
                for (CastingUnitCollector castingUnitCollector : castingUnitCollectors) {
                    if (collectorTonnage == 0)
                    {
                        collectorTonnage = castingUnitCollector.getMixerTonnageMax();
                    }
                    else if (castingUnitCollector.getMixerTonnageMax() < collectorTonnage)
                    {
                        collectorTonnage = castingUnitCollector.getMixerTonnageMax();
                    }
                }

//                for (int i = 0; i < customerOrder.getTonnage() % collectorTonnage; i++)
//                {
//                    Cast cast = new Cast();
//                    cast.setCastingUnit(castingUnitForAssign);
//                    cast.setCustomerOrder(customerOrder);
//                    cast.setCastNumber(casts.get(castingUnitForAssign.getId()).size() + 1);
//
//                    casts.get(castingUnitForAssign.getId()).add(cast);
//                }
                TypedQuery<Cast> typedQuery = em.createNamedQuery("Cast.getCastsForCustomerOrder", Cast.class);
                typedQuery.setParameter("customerOrderId", customerOrder.getId());

                List<Cast> customerOrderCasts = typedQuery.getResultList();
                for (Cast cast : customerOrderCasts)
                {
                    cast.setCastingUnit(castingUnitForAssign);
                    casts.get(castingUnitForAssign.getId()).add(cast);
                }
            }

            CastingProcess castingProcess = new CastingProcess(schemaMap.get(castingUnitForAssign.getId()));
            List<CastWrapper> castWrappers = castingProcess.castingProcess(casts.get(castingUnitForAssign.getId()));

            for (CastWrapper castWrapper : castWrappers)
            {
                for (CastingUnit cu : schedule.getCastingUnits())
                {
                    if (cu.getId() == castingUnitForAssign.getId() && castWrapper.getCast().getCastingUnit().getId() == castingUnitForAssign.getId())
                    {
                        if (castWrapper.getEndDate().compareTo(cu.getStartTime()) > 0)
                        {
                            cu.setStartTime(castWrapper.getEndDate());
                            cu.setPreviousProductId(customerOrderMap.get(assignedGroupCustomerOrder.getGroupId()).getProduct().getId());
                        }
                    }
                }
            }

            schedule.getUnassignedGroupCustomerOrders().remove(assignedGroupCustomerOrder);

            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Finishing calculation for one Group of Orders");
            }
        }
    }
}
