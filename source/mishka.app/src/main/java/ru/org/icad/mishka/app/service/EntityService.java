package ru.org.icad.mishka.app.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityService {

    @PersistenceContext(unitName = "MishkaService")
    protected EntityManager entityManager;

    public EntityService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
