package ru.org.icad.mishka.app.model;

import java.util.*;

/**
 * Created by @author Ivan Solovyev.
 */
public class Schedule
{
    //Customer Orders with specific for Schedule tonnage
    Collection<CustomerOrder> customerOrders;

    //Division of Customer Orders into Groups in the specific Schedule
    Collection<GroupCustomerOrder> groupCustomerOrders;

    //Group of Customer Orders unassigned in current Schedule
    Collection<String> unassignedGroupCustomerOrders;

    //Collection of CastingUnit with specific for Schedule previousProductId and startTime
    Collection<CastingUnit> castingUnits;

    //TODO:Consider 9th schema, Mould on central LA (LA.ID = 50) should be used
    //Last mounted Mould on Casting Unit
    Map<Integer, Integer> castingUnitMould;

    //Map with Casts for each Cast Unit
    Map<Integer, List<Cast>> casts;

    public Schedule()
    {
        customerOrders = new ArrayList<>();
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
}
