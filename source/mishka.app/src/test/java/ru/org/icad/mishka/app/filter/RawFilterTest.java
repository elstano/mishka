package ru.org.icad.mishka.app.filter;

import ru.org.icad.mishka.app.chemistry.Chemistry;
import ru.org.icad.mishka.app.equipment.Electrolyzer;
import ru.org.icad.mishka.app.order.Order;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class RawFilterTest {

    private static final List<Order> orders = Lists.newArrayList(
            new Order(4, new Chemistry(0.14D, 0.3D, 0.3D, 0.4D, 0.5D, 0.2D))
    );

    private static final List<Electrolyzer> electrolyzers = Lists.newArrayList(
            new Electrolyzer(1, 1, new Chemistry(0.12D, 0.2D, 0.1D, 0.3D, 0.4D, 0.1D), 1),
            new Electrolyzer(2, 1, new Chemistry(0.11D, 0.5D, 0.1D, 0.3D, 0.4D, 0.1D), 1),
            new Electrolyzer(3, 2, new Chemistry(0.12D, 0.1D, 0.1D, 0.1D, 0.1D, 0.2D), 1),
            new Electrolyzer(4, 1, new Chemistry(0.11D, 0.3D, 0.2D, 0.1D, 0.3D, 0.1D), 2),
            new Electrolyzer(5, 1, new Chemistry(0.11D, 0.3D, 0.2D, 0.1D, 0.3D, 0.1D), 1)

    );

    private static final List<Electrolyzer> exceptedElectrolyzers = Lists.newArrayList(
            new Electrolyzer(1, 1, new Chemistry(0.12D, 0.2D, 0.1D, 0.3D, 0.4D, 0.1D), 1),
            new Electrolyzer(3, 2, new Chemistry(0.12D, 0.1D, 0.1D, 0.1D, 0.1D, 0.2D), 1),
            new Electrolyzer(5, 1, new Chemistry(0.11D, 0.3D, 0.2D, 0.1D, 0.3D, 0.1D), 1)
    );

    @Test(groups = "self", dataProvider = "data")
    public void testCheckRestriction(List<Order> orders, List<Electrolyzer> electrolyzers, List<Electrolyzer> exceptedElectrolyzers) {
        Map<Order, List<Electrolyzer>> orderMap = RawFilter.checkRestriction(orders, electrolyzers);
        List<Electrolyzer> currentElectrolyzer = orderMap.get(orders.iterator().next());

        assertTrue(exceptedElectrolyzers.containsAll(currentElectrolyzer));
    }

    @DataProvider(name = "data")
    public static Object[][] getData()  {
        return new Object[][]{{orders, electrolyzers, exceptedElectrolyzers}};
    }

}
