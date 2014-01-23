package ru.org.icad.mishka.app.dev;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import ru.org.icad.mishka.app.TableName;
import ru.org.icad.mishka.app.model.*;

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
            .put(TableName.PLANT, Plant.class)
            .put(TableName.FORM, Form.class)
            .put(TableName.MARK, Mark.class)
            .put(TableName.CAST_HOUSE, CastHouse.class)
            .put(TableName.CASTING_UNIT, CastingUnit.class)

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
            stringBuilder.append("<tr>" + "\n")
                    .append("<td>" + "\n")
                    .append(o)
                    .append("</td>" + "\n")
                    .append("</tr>" + "\n");
        }
        stringBuilder.append("</table>");

        return stringBuilder.toString();
    }
}
