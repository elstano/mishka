package ru.org.icad.mishka.app.process.calculation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ivan Solovyev
 * This class contain Collection of CustomerOrders grouped by Form, Mark, dimentions
 */
public class GroupCustomerOrder
{
    private String groupId;

    private Collection<String> customerOrders;

    public GroupCustomerOrder()
    {
        this.customerOrders = new ArrayList<>();
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public Collection<String> getCustomerOrderIds()
    {
        return customerOrders;
    }

    public void addCustomerOrder(String customerOrderId)
    {
        customerOrders.add(customerOrderId);
    }
}
