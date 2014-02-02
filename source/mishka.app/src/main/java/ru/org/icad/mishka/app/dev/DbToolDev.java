package ru.org.icad.mishka.app.dev;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import ru.org.icad.mishka.app.TableName;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.util.HtmlUtil;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DbToolDev {

    private static final Map<String, Class> CLASS_MAP = ImmutableMap.<String, Class>builder()
            .put(TableName.OPERATION, Operation.class)
            .put(TableName.CASTING_SPEED, CastingSpeed.class)
            .put(TableName.CASTING_UNIT, CastingUnit.class)
            .put(TableName.CU_CASTING_MACHINE, CastingUnitCastingMachine.class)
            .put(TableName.CU_COLLECTOR, CastingUnitCollector.class)
            .put(TableName.CU_DISTRIBUTOR, CastingUnitDistributor.class)
            .put(TableName.CU_FILTERS, CastingUnitFilters.class)
            .put(TableName.CU_HOMOGEN_CUTTING_LINE, CastingUnitHomogenCuttingLine.class)
            .put(TableName.CU_CH_LINES, CastingUnitHomogenCuttingLines.class)
            .put(TableName.CU_MARKS, CastingUnitMarks.class)
            .put(TableName.CU_PRODUCT_CHANGE, CastingUnitProductChange.class)
            .put(TableName.CU_REPAIR, CastingUnitRepair.class)
            .put(TableName.CAST_MACH_MOULDS, CastMachMoulds.class)
            .put(TableName.CONTAINER_TYPE, ContainerType.class)
            .put(TableName.CUTTING_LINE, CuttingLine.class)
            .put(TableName.ELECTROLIZER_PROGNOSIS, ElectrolizerPrognosis.class)
            .put(TableName.CU_FILTER, CastingUnitFilters.class)
            .put(TableName.FILTER_CHANGE_MARK, FilterChangeMark.class)
            .put(TableName.FILTER_CONS, FilterCons.class)
            .put(TableName.FILTRATION, Filtration.class)
            .put(TableName.FORM, Form.class)
            .put(TableName.HEATER, Heater.class)
            .put(TableName.HOMOGENIZATION_LINE, HomogenizationLine.class)
            .put(TableName.MARK, Mark.class)
            .put(TableName.MOULD, Mould.class)
            .put(TableName.MOULD_BLANKS, MouldBlanks.class)
            .put(TableName.CUSTOMER_ORDER, Order.class)
            .put(TableName.ORDER_CU_DIRECTIVE, OrderCastingUnitDirective.class)
            .put(TableName.PLANT_CONTAINERS, PlantContainer.class)
            .put(TableName.PREPARE_TIME_CONST, PrepareTimeConst.class)
            .put(TableName.PRODUCED_PRODUCT, ProducedProduct.class)
            .put(TableName.PRODUCT, Product.class)
            .put(TableName.PRODUCT_COST, ProductCost.class)
            .put(TableName.TRANSPORT_COST, TransportCost.class)
            .put(TableName.TRANSPORT_DESTINATION, TransportDestination.class)
            .put(TableName.PLANT, Plant.class)
            .put(TableName.CAST_HOUSE, CastHouse.class)
            .put(TableName.CAST, Cast.class)
            .build();

    public List<String> getTableNames() {
        List<String> tableNames = Lists.newArrayList();

        Object o = new Object();
        Field[] fields = TableName.class.getFields();
        for (Field field : fields) {
            try {
                tableNames.add((String) field.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return tableNames;
    }

    public String getTableContent(String tableName) {
        List objects = Collections.emptyList();

        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            Class clazz = CLASS_MAP.get(tableName);
            if (clazz != null) {
                TypedQuery plantTypedQuery = em.createQuery("SELECT o FROM " + clazz.getName() + " o", clazz);
                objects = plantTypedQuery.getResultList();
                transaction.commit();
            }
        } catch (Exception e) {
            // empty
        }


        if (objects.isEmpty()) {
            return "<table><tr><td>Table is empty</td></tr></table>";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table>" + "\n");

        for (Object o : objects) {
            stringBuilder.append(HtmlUtil.objectToTableRow(o));
        }
        stringBuilder.append("</table>");

        return stringBuilder.toString();
    }
}
