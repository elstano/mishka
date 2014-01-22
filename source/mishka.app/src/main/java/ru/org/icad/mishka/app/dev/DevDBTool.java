package ru.org.icad.mishka.app.dev;

import com.google.common.collect.Lists;
import ru.org.icad.mishka.app.TableName;
import ru.org.icad.mishka.app.model.Plant;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class DevDbTool {

    public List<String> getTableNames() {
        List<String> tableNames = Lists.newArrayList();

        String o = "";
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
        List<Plant> plants = Collections.emptyList();
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();

            TypedQuery<Plant> plantTypedQuery = em.createQuery("SELECT p FROM PLANT p", Plant.class);
            transaction.commit();
            plants = plantTypedQuery.getResultList();
        } catch (Exception e) {
            // empty
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table>" + "\n");

        for (Plant plant : plants) {
            stringBuilder.append("<tr>" + "\n")
                    .append("<td>" + "\n")
                    .append(plant.getId())
                    .append("</td>" + "\n")
                    .append("<td>" + "\n")
                    .append(plant.getName())
                    .append("</td>" + "\n")
                    .append("<td>" + "\n")
                    .append(plant.getPremiumA7())
                    .append("</td>" + "\n")
                    .append("<td>" + "\n")
                    .append(plant.getClipAddCost())
                    .append("</td>" + "\n")
                    .append("<td>" + "\n")
                    .append(plant.getClipMeltLoss())
                    .append("</td>" + "\n")
                    .append("</tr>" + "\n");
        }
        stringBuilder.append("</table>");

        return stringBuilder.toString();
    }
}
