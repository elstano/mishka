package ru.org.icad.mishka.app.process.calculation;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.math3.util.Precision;
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
import ru.org.icad.mishka.app.util.CastUtil;
import ru.org.icad.mishka.app.util.TimeCalculationUtils;
import ru.org.icad.mishka.app.util.TimeUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.*;

public class ScheduleCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleCalculator.class);

    private static final String START_DATE = "01/05/2013 00:00:00";


    public ScheduleCalculator() {
    }

    public void calculateSchedule() throws Exception {
        final Map<Integer, Schema> schemaMap = ImmutableMap.<Integer, Schema>builder()
                .put(30, new Schema4(new SchemaConfiguration(2, 30, new int[]{49}, new int[]{46}, new int[]{15})))
                .put(33, new Schema4(new SchemaConfiguration(2, 33, new int[]{52}, new int[]{51}, new int[]{16})))
                .put(22, new Schema5_6(new SchemaConfiguration(2, 22, new int[]{40, 41}, new int[]{41}, new int[]{103})))
                .put(24, new Schema5_6(new SchemaConfiguration(2, 24, new int[]{42, 43}, new int[]{42}, new int[]{59})))
                .put(26, new Schema5_6(new SchemaConfiguration(2, 26, new int[]{44, 45}, new int[]{43}, new int[]{155})))
                .put(28, new Schema5_6(new SchemaConfiguration(2, 28, new int[]{46, 47}, new int[]{44}, new int[]{158})))
                .put(31, new Schema9(new SchemaConfiguration(2, 31, new int[]{50, 51}, new int[]{48, 50, 49}, new int[]{23, 30, 24})))
                .build();

        Map<Integer, List<Cast>> casts = new HashMap<>();

        //Division of Orders into Groups
        DBLoader<GroupCustomerOrder> groupCULoader = new GroupCustomerOrderLoader();
        Collection<GroupCustomerOrder> groupCustomerOrders = groupCULoader.load();

        //Map of Customer Orders
        DBLoader<CustomerOrder> customerOrderDBLoader = new CustomerOrderLoaded();
        Map<Object, CustomerOrder> customerOrderMap = customerOrderDBLoader.loadMap();

        if (LOGGER.isDebugEnabled()) {
            for (GroupCustomerOrder gco : groupCustomerOrders) {
                LOGGER.debug("Group of Customer Orders: " + gco.getGroupId());
                for (String coId : gco.getCustomerOrderIds()) {
                    LOGGER.debug("Customer Order: " + coId + ", Due Date: " + customerOrderMap.get(coId).getDueDate());
                }
            }
        }

        //Collection of Casting Units
        DBLoader<CastingUnit> castingUnitDBLoader = new CastingUnitLoader();
        Collection<CastingUnit> castingUnitList = castingUnitDBLoader.load();
        for (CastingUnit cu : castingUnitList) {
            cu.setStartTime(TimeUtil.stringToDate(START_DATE));
            casts.put(cu.getId(), new ArrayList<Cast>());
        }

        //Last assigned Product for Casting Unit to calculate next assigned Group of Orders
        Map<Integer, Integer> castingUnitLastProduct = new HashMap<>();
        //Last mounted Mould on Casting Unit
        Map<Integer, Integer> castingUnitMould = new HashMap<>();

        for (CastingUnit cu : castingUnitList) {
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
        if (schedule.getUnassignedGroupCustomerOrders().size() > 0) {
            CastingUnit castingUnitForAssign = null;
            //get first available cast unit
            for (CastingUnit castingUnit : schedule.getCastingUnits()) {
                if (castingUnitForAssign == null) {
                    castingUnitForAssign = castingUnit;
                } else {
                    if (ObjectUtils.compare(castingUnit.getStartTime(), castingUnitForAssign.getStartTime()) < 0) {
                        castingUnitForAssign = castingUnit;
                    }
                }
            }

            assert castingUnitForAssign != null;

            int minimalPreparationTime = 0;
            GroupCustomerOrder assignedGroupCustomerOrder = null;

            //get most convenient group of orders
            for (GroupCustomerOrder gco : groupCustomerOrders) {
                int preparationTime = TimeCalculationUtils.getPreparationTime(castingUnitForAssign.getId(),
                        castingUnitLastProduct.get(castingUnitForAssign.getId()),
                        gco.getCustomerOrderIds().iterator().next(),
                        castingUnitMould.get(castingUnitForAssign.getId()));

                if (assignedGroupCustomerOrder == null) {
                    minimalPreparationTime = preparationTime;
                    assignedGroupCustomerOrder = gco;
                } else {
                    if (preparationTime < minimalPreparationTime) {
                        minimalPreparationTime = preparationTime;
                        assignedGroupCustomerOrder = gco;
                    }
                }
            }

            assert assignedGroupCustomerOrder != null;

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Preparation Time for " + castingUnitForAssign.getId() + " on " +
                        assignedGroupCustomerOrder.getGroupId() + " is " + minimalPreparationTime);
            }

            //generate Casts, assign order, recalculate time

            for (String assignedOrderId : assignedGroupCustomerOrder.getCustomerOrderIds()) {
                CustomerOrder customerOrder = schedule.getCustomerOrders().get(assignedOrderId);

                //Calculation number of Casts for current Order
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
                EntityManager em = emf.createEntityManager();
                TypedQuery<CastingUnitCollector> castingUnitCollectorQuery = em.createNamedQuery("CastingUnitCollector.findByCastingUnit", CastingUnitCollector.class);
                castingUnitCollectorQuery.setParameter("castingUnitId", castingUnitForAssign.getId());
                List<CastingUnitCollector> castingUnitCollectors = castingUnitCollectorQuery.getResultList();
                double collectorTonnage = 0;
                for (CastingUnitCollector castingUnitCollector : castingUnitCollectors) {
                    if (collectorTonnage == 0) {
                        collectorTonnage = castingUnitCollector.getMixerTonnageMax();
                    } else if (castingUnitCollector.getMixerTonnageMax() < collectorTonnage) {
                        collectorTonnage = castingUnitCollector.getMixerTonnageMax();
                    }
                }

                //TODO:change Cast to optimized
//                for (int i = 0; i < customerOrder.getTonnage() % collectorTonnage; i++)
//                {
//                    Cast cast = new Cast();
//                    cast.setCastingUnit(castingUnitForAssign);
//                    cast.setCustomerOrder(customerOrder);
//                    cast.setCastNumber(casts.get(castingUnitForAssign.getId()).size() + 1);
//
//                    casts.get(castingUnitForAssign.getId()).add(cast);
//                }

//                TypedQuery<Cast> typedQuery = em.createNamedQuery("Cast.getCastsForCustomerOrder", Cast.class);
//                typedQuery.setParameter("customerOrderId", customerOrder.getId());
//
//                List<Cast> customerOrderCasts = typedQuery.getResultList();
//                for (Cast cast : customerOrderCasts)
//                {
//                    cast.setCastingUnit(castingUnitForAssign);
//                    casts.get(castingUnitForAssign.getId()).add(cast);
//                }

                TypedQuery<Mould> mouldTypedQuery = em.createNamedQuery("Mould.getMouldForSlab", Mould.class);
                mouldTypedQuery.setParameter("castingUnitId", castingUnitForAssign.getId());
                mouldTypedQuery.setParameter("formId", customerOrder.getProduct().getForm().getId());
                mouldTypedQuery.setParameter("width", customerOrder.getWidth());
                mouldTypedQuery.setParameter("height", customerOrder.getHeight());

                List<Mould> moulds = mouldTypedQuery.getResultList();

                casts.get(castingUnitForAssign.getId()).addAll(generateCasts(customerOrder, castingUnitForAssign, moulds.get(0)));
            }

            CastingProcess castingProcess = new CastingProcess(schemaMap.get(castingUnitForAssign.getId()));
            List<CastWrapper> castWrappers = castingProcess.castingProcess(casts.get(castingUnitForAssign.getId()));

            for (CastWrapper castWrapper : castWrappers) {
                for (CastingUnit cu : schedule.getCastingUnits()) {
                    if (cu.getId() == castingUnitForAssign.getId() && castWrapper.getCast().getCastingUnit().getId() == castingUnitForAssign.getId()) {
                        if (castWrapper.getEndDate().compareTo(cu.getStartTime()) > 0) {
                            cu.setStartTime(castWrapper.getEndDate());
                            cu.setPreviousProductId(customerOrderMap.get(assignedGroupCustomerOrder.getGroupId()).getProduct().getId());
                        }
                    }
                }
            }

            schedule.getUnassignedGroupCustomerOrders().remove(assignedGroupCustomerOrder);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Finishing calculation for one Group of Orders");
            }
        }
    }

    public List<Cast> generateCasts(CustomerOrder customerOrder, CastingUnit castingUnit, Mould mould) throws Exception {
        List<Cast> casts = new ArrayList<>();

        if (Form.SLAB == customerOrder.getProduct().getForm().getId()) {
            int blanks = 0;

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            TypedQuery<CastingUnitCastingMachine> typedQuery = em.createNamedQuery("CastingUnitCastingMachine.findByCastUnitId", CastingUnitCastingMachine.class);
            typedQuery.setParameter("castingUnitId", castingUnit.getId());
            List<CastingUnitCastingMachine> castingUnitCastingMachines = typedQuery.getResultList();

            int lengthMaxBlankCast = castingUnitCastingMachines.get(0).getLenghtBlankMax();

            double weightProdCast = CastUtil.RO * customerOrder.getLength() * customerOrder.getHeight() * customerOrder.getWidth();

            int lengthMaxProd = (int) (castingUnit.getCastHouse().getBlankWeightMax() * customerOrder.getLength() / weightProdCast);

            if (lengthMaxProd < lengthMaxBlankCast) {
                lengthMaxBlankCast = lengthMaxProd;
            }

            final int ingots = (int) Precision.round( (lengthMaxBlankCast - customerOrder.getProduct().getClipping()) / customerOrder.getLength(), BigDecimal.ROUND_DOWN);
            final int lengthBlanksCast = ingots * customerOrder.getLength() + customerOrder.getProduct().getClipping();

            TypedQuery<MouldBlanks> mbTypedQuery = em.createNamedQuery("MouldBlanks.getMouldBlanksForMould", MouldBlanks.class);
            mbTypedQuery.setParameter("mouldId", mould.getId());
            List<MouldBlanks> mouldBlanksList = mbTypedQuery.getResultList();

            //TODO: calculate MouldBlanks based on volume of mixer
            for (MouldBlanks mouldBlanks : mouldBlanksList) {
                if (mouldBlanks.getNumBlanks() > blanks) {
                    blanks = mouldBlanks.getNumBlanks();
                }
            }

            double vCast = CastUtil.RO * blanks * lengthBlanksCast * customerOrder.getHeight() * customerOrder.getWidth();

            int castNum = (int) Precision.round(customerOrder.getTonnage() / vCast, BigDecimal.ROUND_DOWN);

            for (int i = 0; i < castNum; i++) {
                Cast cast = new Cast();
                cast.setCastingUnit(castingUnit);
                cast.setCastNumber(i + 1);
                cast.setCustomerOrder(customerOrder);
                cast.setBlankCount(blanks);
                cast.setIngotCount(0);
                cast.setIngotInBlankCount(ingots);
                casts.add(cast);
            }

            return casts;
        }

        if (Form.INGOT == customerOrder.getProduct().getForm().getId()) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            TypedQuery<CastingUnitCollector> typedQuery = em.createNamedQuery("CastingUnitCollector.findByCastingUnit", CastingUnitCollector.class);
            typedQuery.setParameter("castingUnitId", castingUnit.getId());
            List<CastingUnitCollector> castingUnitCollectors = typedQuery.getResultList();

            CastingUnitCollector castingUnitCollector = castingUnitCollectors.get(0);

            double mixerTonnage = castingUnitCollector.getMixerTonnageMax() - castingUnitCollector.getMixerRestTonnage();

            int vCast = (int) Precision.round(mixerTonnage, BigDecimal.ROUND_DOWN);
            int castNum = (int) Precision.round(customerOrder.getTonnage() / vCast, BigDecimal.ROUND_DOWN);

            for (int i = 0; i < castNum; i++) {
                Cast cast = new Cast();
                cast.setCastingUnit(castingUnit);
                cast.setCastNumber(i + 1);
                cast.setCustomerOrder(customerOrder);
                cast.setBlankCount(1);
                cast.setIngotCount(0);
                cast.setIngotInBlankCount(vCast);
                casts.add(cast);
            }

            return casts;
        } else if (Form.BILLET == customerOrder.getProduct().getForm().getId()) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            TypedQuery<CastingUnitCastingMachine> typedQuery = em.createNamedQuery("CastingUnitCastingMachine.findByCastUnitId", CastingUnitCastingMachine.class);
            typedQuery.setParameter("castingUnitId", castingUnit.getId());
            List<CastingUnitCastingMachine> castingUnitCastingMachines = typedQuery.getResultList();

            int lengthMaxBlankCast = castingUnitCastingMachines.get(0).getLenghtBlankMax();

            double weightProdCast = CastUtil.RO * customerOrder.getLength() * customerOrder.getHeight() * customerOrder.getWidth();

            int lengthMaxProd = (int) (castingUnit.getCastHouse().getBlankWeightMax() * customerOrder.getLength() / weightProdCast);

            if (lengthMaxProd < lengthMaxBlankCast) {
                lengthMaxBlankCast = lengthMaxProd;
            }

            final int ingots = (int) Precision.round( (lengthMaxBlankCast - customerOrder.getProduct().getClipping()) / customerOrder.getLength(), BigDecimal.ROUND_DOWN);
            final int lengthBlanksCast = ingots * customerOrder.getLength() + customerOrder.getProduct().getClipping();

            TypedQuery<MouldBlanks> mbTypedQuery = em.createNamedQuery("MouldBlanks.getMouldBlanksForMould", MouldBlanks.class);
            mbTypedQuery.setParameter("mouldId", mould.getId());
            List<MouldBlanks> mouldBlanksList = mbTypedQuery.getResultList();

            //TODO: calculate MouldBlanks based on volume of mixer
            int blanks =  mouldBlanksList.get(0).getNumBlanks();

            double vCast = CastUtil.RO * blanks * lengthBlanksCast * Math.pow(customerOrder.getDiameter(), 2) * Math.PI / 4;

            int castNum = (int) Precision.round(customerOrder.getTonnage() / vCast, BigDecimal.ROUND_DOWN);

            for (int i = 0; i < castNum; i++) {
                Cast cast = new Cast();
                cast.setCastingUnit(castingUnit);
                cast.setCastNumber(i + 1);
                cast.setCustomerOrder(customerOrder);
                cast.setBlankCount(blanks);
                cast.setIngotCount(0);
                cast.setIngotInBlankCount(ingots);
                casts.add(cast);
            }

            return casts;
        }

        throw new Exception();
    }
}
