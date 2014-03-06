package ru.org.icad.mishka.app.loader.db;

import org.apache.commons.lang3.StringUtils;
import ru.org.icad.mishka.app.jdbc.JDBCHandler;
import ru.org.icad.mishka.app.jdbc.JDBCTool;
import ru.org.icad.mishka.app.process.calculation.GroupCustomerOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Ivan Solovyev
 */
public class GroupCustomerOrderLoader implements DBLoader<GroupCustomerOrder>
{
    private static final String GROUP_CUSTOMER_ORDER_SQL =
            //            "select MIN(co.order_id) OVER (PARTITION BY f.form_name, m.mark_name, co.diameter, co.width, co.height, co.weight) group_id,\n" +
            //            "       co.order_id\n" +
            //            "  from customer_order co,\n" +
            //            "       product p,\n" +
            //            "       form f,\n" +
            //            "       mark m\n" +
            //            " where co.period between '01-MAY-13' and '31-MAY-13'\n" +
            //            "   and f.form_id in (13/*SLAB*/)\n" + //(4 /*BILLET*/, 7/*INGOT*/, 13/*SLAB*/)
            //            "   and p.product_id = co.product_id\n" +
            //            "   and f.form_id = p.form_id\n" +
            //            "   and m.mark_id = p.mark_id\n" +
            //            " order by group_id, order_id";
            "select distinct go.*--, p.FORM_ID, p.product_id, cu_m.cu_id, mould.mould_id, cu.previous_product_id, p.mark_id\n" +
                    "  from (select MIN(co.order_id) OVER (PARTITION BY p.form_id, p.mark_id, co.diameter, co.width, co.height, co.weight) group_id, co.order_id\n" +
                    "          from customer_order co,\n" +
                    "               product p\n" +
                    "         where co.period between TO_DATE('01/05/2013', 'dd/mm/yyyy') and TO_DATE('31/05/2013', 'dd/mm/yyyy')\n" +
                    "           and p.form_id in (13/*SLAB*/) --(4 /*BILLET*/, 7/*INGOT*/, 13/*SLAB*/)\n" +
                    "           and p.product_id = co.product_id) go,\n" +
                    "       customer_order o,\n" +
                    "       product p,\n" +
                    "       mould mould,\n" +
                    "       cast_house ch,\n" +
                    "       cast_mach_moulds cm_m,\n" +
                    "       cu_casting_machine cu_cm,\n" +
                    "       cu_marks cu_m,\n" +
                    "       casting_unit cu\n" +
                    " where o.order_id = go.order_id\n" +
                    "   and p.product_id = o.product_id\n" +
                    "   and p.form_id in (13/*SLAB*/) --(4 /*BILLET*/, 7/*INGOT*/, 13/*SLAB*/)\n" +
                    "   and mould.form_id = p.form_id\n" +
                    "   and ch.ch_id = mould.ch_id\n" +
                    "   and ch.plant_id = 5301\n" +
                    "   and ((p.form_id = 13 AND mould.width = o.height and mould.height = o.width) or (p.form_id = 4 and mould.diameter = o.diameter) or (p.form_id = 7 and mould.width = o.weight))\n" +
                    "   and cm_m.mould_id = mould.mould_id\n" +
                    "   and cu_cm.cast_mach_id = cm_m.cast_mach_id\n" +
                    "   and cu_m.cu_id = cu_cm.cu_id\n" +
                    "   and p.mark_id in (select m.mark_id from mark m start with m.mark_id = cu_m.mark_id connect by prior m.mark_id = m.parent_mark_id)\n" + //TODO:clarify condition
                    "   and cu_m.cu_id not in (28)\n" +
                    "   and cu.cu_id = cu_m.cu_id\n" +
                    " order by go.group_id, go.order_id";

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
