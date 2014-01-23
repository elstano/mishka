package ru.org.icad.mishka.app.loader;

import ru.org.icad.mishka.app.TableName;
import ru.org.icad.mishka.app.loader.excel.*;
import ru.org.icad.mishka.app.util.ExcelUtil;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.io.File;
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

            if (TableName.MARK.equals(sheetName)) {
                MarkLoader markLoader = new MarkLoader();
                persist(markLoader.load(filePath, TableName.MARK));

                continue;
            }

            if (TableName.CAST.equals(sheetName)) {
                CastLoader castLoader = new CastLoader();
                persist(castLoader.load(filePath, TableName.CAST));

                continue;
            }

            if (TableName.FORM.equals(sheetName)) {
                FormLoader formLoader = new FormLoader();
                persist(formLoader.load(filePath, TableName.FORM));

                continue;
            }

            if(TableName.CAST_HOUSE.equals(sheetName)) {
                CastHouseLoader castHouseLoader = new CastHouseLoader();
                persist(castHouseLoader.load(filePath, TableName.CAST_HOUSE));

                continue;
            }

            if (TableName.CASTING_UNIT.equals(sheetName)) {
                CastingUnitLoader castingUnitLoader = new CastingUnitLoader();
                persist(castingUnitLoader.load(filePath, TableName.CASTING_UNIT));

            }
        }

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
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
