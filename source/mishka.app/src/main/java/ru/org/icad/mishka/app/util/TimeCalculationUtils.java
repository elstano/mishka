package ru.org.icad.mishka.app.util;

import ru.org.icad.mishka.app.jdbc.JDBCHandler;
import ru.org.icad.mishka.app.jdbc.JDBCTool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by @author Ivan Solovyev.
 */
public class TimeCalculationUtils
{
    private static final String PREPARATION_TIME_QUERY =
                    "with prep as (\n" +
                    "select coalesce(cu_pc.time_cast, 0) + coalesce(cu_pc.time_prepare_c, 0) as prepare_time\n" +
                    "  from customer_order co,\n" +
                    "       product co_p,\n" +
                    "       product cu_p,\n" +
                    "       cu_product_change cu_pc\n" +
                    " where co.order_id = ?\n" +
                    "   and co_p.product_id = co.product_id\n" +
                    "   and cu_p.product_id = ?\n" +
                    "   and cu_pc.cu_id = ?\n" +
                    "   and cu_pc.mark_id_1 = cu_p.mark_id\n" +
                    "   and cu_pc.mark_id_2 = co_p.mark_id\n" +
                    "), remould as (\n" +
                    "select case\n" +
                    "         when exists (select 1 from mould mould\n" +
                    "                       where mould.mould_id = ?\n" +
                    "                         and ((mould.form_id = 13 and mould.width = o.height and m.height = o.width)\n" +
                    "                           or (mould.form_id = 4 and mould.diameter = o.diameter)\n" +
                    "                           or (mould.form_id = 7 and mould.width = o.weight))) then 0\n" +
                    "         else cu_cm.remould_time\n" +
                    "       end as remould_time\n" +
                    "  from cu_casting_machine cu_cm,\n" +
                    "       cast_mach_moulds cm_m,\n" +
                    "       mould m,\n" +
                    "       customer_order o,\n" +
                    "       product p\n" +
                    " where cu_cm.cu_id = ?\n" +
                    "   and cm_m.cast_mach_id = cu_cm.cast_mach_id\n" +
                    "   and m.mould_id = cm_m.mould_id\n" +
                    "   and m.form_id = p.form_id\n" +
                    "   and o.order_id = ?\n" +
                    "   and p.product_id = o.product_id\n" +
                    "   and ((p.form_id = 13 and m.width = o.height and m.height = o.width) or (p.form_id = 4 and m.diameter = o.diameter) or (p.form_id = 7 and m.width = o.weight))\n" +
                    ")\n" +
                    "select max(prepare_time) as prepare_time\n" +
                    "  from (select prepare_time from prep union all select remould_time from remould)";

    public static int getPreparationTime(final int cuId, final int previousProductId, final String orderId, final int currentMouldId) throws SQLException
    {
        JDBCHandler<Integer> handler = new JDBCHandler<Integer>(){
            @Override
            public Integer onResultSet(ResultSet rs) throws SQLException
            {
                Integer preparationTime = 0;

                if (rs.next())
                {
                    preparationTime = rs.getInt(1);
                }

                return preparationTime;
            }

            @Override
            public void parametrize(PreparedStatement statement) throws SQLException
            {
                statement.setString(1, orderId);
                statement.setInt(2, previousProductId);
                statement.setInt(3, cuId);
                statement.setInt(4, currentMouldId);
                statement.setInt(5, cuId);
                statement.setString(6, orderId);
            }
        };
        return JDBCTool.instance().executeQuery(PREPARATION_TIME_QUERY, handler);
    }
}
