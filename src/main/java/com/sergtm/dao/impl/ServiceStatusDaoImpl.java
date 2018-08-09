package com.sergtm.dao.impl;

import com.sergtm.dao.IServiceStatusDao;
import com.sergtm.entities.ServiceStatus;
import com.sergtm.model.ServiceName;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Collection;

@Repository
@Transactional(readOnly = true)
public class ServiceStatusDaoImpl implements IServiceStatusDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Collection<ServiceStatus> getAll() {
        String sql = "FROM ServiceStatus";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return query.getResultList();
    }

    @Override
    public ServiceStatus getByName(ServiceName serviceName) {
        String sql = "FROM ServiceStatus s WHERE s.serviceName = :serviceName";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("serviceName", serviceName);
        if (query.getResultList().size() != 0)
            return (ServiceStatus) query.getResultList().get(0);
        return null;
    }

    @Override
    @Transactional
    public void update(ServiceStatus serviceStatus) {
        sessionFactory.getCurrentSession().update(serviceStatus);
    }
}