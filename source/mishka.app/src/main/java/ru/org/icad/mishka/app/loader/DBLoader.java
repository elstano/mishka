package ru.org.icad.mishka.app.loader;

import ru.org.icad.mishka.app.TableName;
import ru.org.icad.mishka.app.loader.excel.CastLoader;
import ru.org.icad.mishka.app.loader.excel.PlantLoader;
import ru.org.icad.mishka.app.util.ExcelUtil;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.util.List;

public class DBLoader {
    public static void load(String filePath) {
        List<String> sheetNames = ExcelUtil.getSheetNames(filePath);

        for (String sheetName : sheetNames) {
            if (TableName.PLANT.equals(sheetName)) {
                PlantLoader plantLoader = new PlantLoader();
                persist(plantLoader.load(filePath, TableName.PLANT));

                continue;
            }

            if (TableName.CAST.equals(sheetName)) {
                CastLoader castLoader = new CastLoader();
                persist(castLoader.load(filePath, TableName.CAST));
            }
        }
    }

    private static <T> void persist(List<T> data) {
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
            EntityManager em = emf.createEntityManager();
            if (data != null)
                for (T plant : data) {
                    em.persist(plant);
                }
            em.flush();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
