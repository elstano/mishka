package ru.org.icad.mishka.app.process.calculation;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.dev.casting.CastingDev;
import ru.org.icad.mishka.app.loader.db.CastingUnitLoader;
import ru.org.icad.mishka.app.loader.db.CustomerOrderLoaded;
import ru.org.icad.mishka.app.loader.db.DBLoader;
import ru.org.icad.mishka.app.loader.db.GroupCustomerOrderLoader;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.process.casting.CastWrapper;
import ru.org.icad.mishka.app.process.casting.CastingProcess;
import ru.org.icad.mishka.app.util.TimeCalculationUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.sql.SQLException;
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

        //TODO: while
        if (groupCustomerOrders.size() > 0)
        {
            CastingUnit castingUnitForAssign = null;
            //get first available cast unit
            for (CastingUnit castingUnit : castingUnitList)
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
                CustomerOrder customerOrder = customerOrderMap.get(assignedOrderId);

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

                for (int i = 0; i < customerOrder.getTonnage() % collectorTonnage; i++)
                {
                    Cast cast = new Cast();
                    cast.setCastingUnit(castingUnitForAssign);
                    cast.setCustomerOrder(customerOrder);
                    cast.setCastNumber(casts.get(castingUnitForAssign.getId()).size() + 1);

                    casts.get(castingUnitForAssign.getId()).add(cast);
                }
            }

            CastingProcess castingProcess = new CastingProcess(CastingDev.SCHEMA_MAP.get(castingUnitForAssign.getId()));
            List<CastWrapper> castWrappers = castingProcess.castingProcess(casts.get(castingUnitForAssign.getId()));

            for (CastWrapper castWrapper : castWrappers)
            {
                for (CastingUnit cu : castingUnitList)
                {
                    if (cu.getId() == castingUnitForAssign.getId())
                    {
                        cu.setStartTime(castWrapper.getEndDate());
                        cu.setPreviousProductId(customerOrderMap.get(assignedGroupCustomerOrder.getGroupId()).getProduct().getId());
                    }
                }
            }

            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Finishing calculation for 1 Group of Orders");
            }
        }
    }
}
