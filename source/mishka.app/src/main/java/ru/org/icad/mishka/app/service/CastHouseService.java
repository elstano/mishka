package ru.org.icad.mishka.app.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CastHouseService {

    @PersistenceContext(unitName = "MishkaService")
    protected EntityManager em;

//    public CastHouse createEmployee(int id, String name, long salary) {
//        CastHouse castHouse = new CastHouse(id);
//        castHouse.set(name);
//        castHouse.setSalary(salary);
//        entityManager.persist(castHouse);
//        return castHouse;
//    }
//
//    public void removeEmployee(int id) {
//        CastHouse emp = findEmployee(id);
//        if (emp != null) {
//            entityManager.remove(emp);
//        }
//    }
//
//    public CastHouse raiseEmployeeSalary(int id, long raise) {
//        CastHouse emp = entityManager.find(CastHouse.class, id);
//        if (emp != null) {
//            emp.setSalary(emp.getSalary() + raise);
//        }
//        return emp;
//    }
//
//    public CastHouse findEmployee(int id) {
//        return entityManager.find(Employee.class, id);
//    }
//
//    public List<CastHouse> findAllEmployees() {
//        TypedQuery<CastHouse> query = entityManager.createQuery(
//                "SELECT e FROM Employee e", CastHouse.class);
//        return query.getResultList();
//    }
}
