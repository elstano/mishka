package ru.org.icad.mishka.app.loader.db;

import ru.org.icad.mishka.app.model.CustomerOrder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author Ivan Solovyev.
 */
public class CustomerOrderLoaded implements DBLoader<CustomerOrder>
{

    @Override
    public Collection<CustomerOrder> load() throws SQLException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MishkaService");
        EntityManager em = emf.createEntityManager();

        return em.createNamedQuery("CustomerOrder.findAll", CustomerOrder.class).getResultList();
    }

    @Override
    public Map<Object, CustomerOrder> loadMap() throws SQLException
    {
        Map<Object, CustomerOrder> customerOrderMap = new HashMap<>();

        for (CustomerOrder cu : this.load())
        {
            customerOrderMap.put(cu.getId(), cu);
        }

        return customerOrderMap;
    }
}
