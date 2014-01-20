package ru.org.icad.mishka.app.process.casting;

import ru.org.icad.mishka.app.excel.loader.CastLoader;
import ru.org.icad.mishka.app.excel.loader.FileLoader;
import ru.org.icad.mishka.app.model.Cast;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.List;

public class CastingProcessDev {

    public static void start() {

        List<File> files = FileLoader.load("D:\\temp");

        List<Cast> casts = null;

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();


        for (File file : files) {
            casts = CastLoader.load(file.getPath(), "Sheet1");
            if (!casts.isEmpty()) {
                break;
            }

        }

        if (casts != null) {
            for (ru.org.icad.mishka.app.model.Cast cast : casts) {
                em.persist(cast);
            }
        }
    }
}
