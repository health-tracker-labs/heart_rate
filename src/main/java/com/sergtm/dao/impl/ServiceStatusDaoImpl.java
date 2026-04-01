package com.sergtm.dao.impl;

import com.sergtm.dao.IServiceStatusDao;
import com.sergtm.entities.ServiceStatus;
import com.sergtm.model.ServiceName;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
@Transactional(readOnly = true)
public class ServiceStatusDaoImpl implements IServiceStatusDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<ServiceStatus> getAll() {
        String sql = "FROM ServiceStatus";
        Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }

    @Override
    public ServiceStatus getByName(ServiceName serviceName) {
        String sql = "FROM ServiceStatus s WHERE s.serviceName = :serviceName";
        Query query = entityManager.createQuery(sql);
        query.setParameter("serviceName", serviceName);
        if (!query.getResultList().isEmpty())
            return (ServiceStatus) query.getResultList().get(0);
        return null;
    }

    @Override
    @Transactional
    public void update(ServiceStatus serviceStatus) {
        entityManager.merge(serviceStatus);
    }
}