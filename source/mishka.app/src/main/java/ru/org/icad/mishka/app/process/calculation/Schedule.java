package ru.org.icad.mishka.app.process.calculation;

import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.CustomerOrder;

import java.util.*;

public class Schedule
{
    //Customer Orders with specific for Schedule tonnage
    private Map<Object, CustomerOrder> customerOrders;

    //Division of Customer Orders into Groups in the specific Schedule
    private Collection<GroupCustomerOrder> groupCustomerOrders;

    //Group of Customer Orders unassigned in current Schedule
    private Collection<GroupCustomerOrder> unassignedGroupCustomerOrders;

    //Collection of CastingUnit with specific for Schedule previousProductId and startTime
    private Collection<CastingUnit> castingUnits;

    //TODO:Consider 9th schema, Mould on central LA (LA.ID = 50) should be used
    //Last mounted Mould on Casting Unit
    private Map<Integer, Integer> castingUnitMould;

    //Map with Casts for each Cast Unit
    Map<Integer, List<Cast>> casts;

    public Schedule()
    {
        customerOrders = new HashMap<>();
        groupCustomerOrders = new ArrayList<>();
        unassignedGroupCustomerOrders = new ArrayList<>();
        castingUnits = new ArrayList<>();
        castingUnitMould = new HashMap<>();
        casts = new HashMap<>();
    }

    public Schedule(Schedule schedule)
    {
        this();
        //TODO: make setting of current variables from the schedule
    }

    public Map<Object, CustomerOrder> getCustomerOrders()
    {
        return customerOrders;
    }

    public void setCustomerOrders(Map<Object, CustomerOrder> customerOrders)
    {
        this.customerOrders = customerOrders;
    }

    public void modifyCustomerOrder(CustomerOrder customerOrder)
    {
        //TODO: find CustomerOrder and replace it
    }

    public Collection<GroupCustomerOrder> getGroupCustomerOrders()
    {
        return groupCustomerOrders;
    }

    public void setGroupCustomerOrders(Collection<GroupCustomerOrder> groupCustomerOrders)
    {
        this.groupCustomerOrders = groupCustomerOrders;
    }


    public Collection<GroupCustomerOrder> getUnassignedGroupCustomerOrders()
    {
        return unassignedGroupCustomerOrders;
    }

    public void setUnassignedGroupCustomerOrders(Collection<GroupCustomerOrder> unassignedGroupCustomerOrders)
    {
        this.unassignedGroupCustomerOrders = unassignedGroupCustomerOrders;
    }

    public void removeUnassignedGroupCustomerOrder (String groupId)
    {
        unassignedGroupCustomerOrders.remove(groupId);
    }

    public Collection<CastingUnit> getCastingUnits()
    {
        return castingUnits;
    }

    public void setCastingUnits(Collection<CastingUnit> castingUnits)
    {
        this.castingUnits = castingUnits;
    }

    public void modifyCastingUnit(CastingUnit castingUnit)
    {
        //TODO: find CastingUnit and modify it
    }

    public Map<Integer, Integer> getCastingUnitMould()
    {
        return castingUnitMould;
    }

    public void setCastingUnitMould(Map<Integer, Integer> castingUnitMould)
    {
        this.castingUnitMould = castingUnitMould;
    }

    public void changeCastingUnitMould(Integer castingUnitId, Integer moudlId)
    {
        castingUnitMould.put(castingUnitId, moudlId);
    }
}
