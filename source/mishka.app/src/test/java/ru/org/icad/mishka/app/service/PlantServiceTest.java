package ru.org.icad.mishka.app.service;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PlantServiceTest {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    PlantService service;

    @BeforeClass
    public void beforeClass() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("MishkaService");
        entityManager = entityManagerFactory.createEntityManager();
        service = new PlantService(entityManager);
    }

    @AfterClass
    public void afterClass() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testCreateEmployee() throws Exception {

    }

    @Test
    public void testRemovePlant() throws Exception {

    }

    @Test
    public void testFindPlant() throws Exception {

    }

    @Test
    public void testFindAllPlant() throws Exception {

    }
}
