package ru.org.icad.mishka.app.loader.db;

import org.apache.commons.lang3.StringUtils;
import ru.org.icad.mishka.app.jdbc.JDBCHandler;
import ru.org.icad.mishka.app.jdbc.JDBCTool;
import ru.org.icad.mishka.app.model.GroupCustomerOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Ivan Solovyev
 */
public class GroupCustomerOrderLoader implements DBLoader<GroupCustomerOrder>
{
    private final static String GROUP_CUSTOMER_ORDER_SQL =
            "select MIN(co.order_id) OVER (PARTITION BY f.form_name, m.mark_name, co.diameter, co.width, co.height, co.weight) group_id,\n" +
            "       co.order_id\n" +
            "  from customer_order co,\n" +
            "       product p,\n" +
            "       form f,\n" +
            "       mark m\n" +
            " where co.period between '01-MAY-13' and '31-MAY-13'\n" +
            "   and f.form_id in (13/*SLAB*/)\n" + //(4 /*BILLET*/, 7/*INGOT*/, 13/*SLAB*/)
            "   and p.product_id = co.product_id\n" +
            "   and f.form_id = p.form_id\n" +
            "   and m.mark_id = p.mark_id\n" +
            " order by group_id, order_id";

    @Override
    public List<GroupCustomerOrder> load() throws SQLException
    {
        JDBCHandler<List<GroupCustomerOrder>> handler = new JDBCHandler<List<GroupCustomerOrder>>(){
            @Override
            public List<GroupCustomerOrder> onResultSet(ResultSet rs) throws SQLException
            {
                List<GroupCustomerOrder> groupCustomerOrders = new ArrayList<>();

                String currentGroupId = "";

                while (rs.next())
                {
                    String customerOrderId = rs.getString("order_id");
                    String groupId = rs.getString("group_id");

                    if (StringUtils.equals(currentGroupId, groupId))
                    {
                        //add order to group
                        groupCustomerOrders.get(groupCustomerOrders.size() - 1).addCustomerOrder(customerOrderId);
                    }
                    else
                    {
                        //create new group
                        GroupCustomerOrder groupCustomerOrder = new GroupCustomerOrder();
                        groupCustomerOrder.setGroupId(groupId);

                        groupCustomerOrder.addCustomerOrder(customerOrderId);
                        groupCustomerOrders.add(groupCustomerOrder);
                    }

                    currentGroupId = groupId;
                }

                return groupCustomerOrders;
            }

            @Override
            public void parametrize(PreparedStatement statement) throws SQLException
            {
            }
        };
        return JDBCTool.instance().executeQuery(GROUP_CUSTOMER_ORDER_SQL, handler);
    }

    @Override
    public Map<Object, GroupCustomerOrder> loadMap() throws SQLException
    {
        Map<Object, GroupCustomerOrder> groupCustomerOrderMap = new HashMap<>();
        List<GroupCustomerOrder> groupCustomerOrders = this.load();

        for (GroupCustomerOrder groupCustomerOrder : groupCustomerOrders)
        {
            groupCustomerOrderMap.put(groupCustomerOrder.getGroupId(), groupCustomerOrder);
        }

        return groupCustomerOrderMap;
    }
}
