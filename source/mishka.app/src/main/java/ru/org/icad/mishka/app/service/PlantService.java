package ru.org.icad.mishka.app.service;

import ru.org.icad.mishka.app.TableName;
import ru.org.icad.mishka.app.model.Plant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class PlantService {

    @PersistenceContext(unitName = "MishkaService")
    protected EntityManager entityManager;

    public PlantService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Plant createPlant(
            int id, String name,
            BigDecimal clipAddCost,
            BigDecimal clipMeltLoss,
            BigDecimal premiumA7
    ) {
        Plant plant = new Plant(id);
        plant.setName(name);
        plant.setClipAddCost(clipAddCost);
        plant.setClipMeltLoss(clipMeltLoss);
        plant.setPremiumA7(premiumA7);

        entityManager.persist(plant);

        return plant;
    }

    public void removePlant(int id) {
        Plant plant = findPlant(id);
        if (plant != null) {
            entityManager.remove(plant);
        }
    }

//    public Plant raiseEmployeeSalary(int id, long raise) {
//        Plant emp = entityManager.find(Plant.class, id);
//        if (emp != null) {
//            emp.setSalary(emp.getSalary() + raise);
//        }
//        return emp;
//    }

    public Plant findPlant(int id) {
        return entityManager.find(Plant.class, id);
    }

    public List<Plant> findAllPlant() {
        TypedQuery<Plant> query = entityManager.createQuery("SELECT p FROM " + TableName.PLANT + " p", Plant.class);

        return query.getResultList();
    }
}
