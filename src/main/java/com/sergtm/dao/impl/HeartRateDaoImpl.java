package com.sergtm.dao.impl;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sergtm.dao.IHeartRateDao;
import com.sergtm.entities.HeartRate;

/**
 * Created by Sergey on 18.07.2017.
 */
@Repository
@Transactional
public class HeartRateDaoImpl implements IHeartRateDao {
    private static final String FIND_BY_DATE_RANGE = "FROM HeartRate h where h.date between :from and :to order by h.date";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addHeartRate(HeartRate heartRate) {
        sessionFactory.getCurrentSession().save(heartRate);
    }

    @Override
    public HeartRate getById(Long heartRateId) {
        return sessionFactory.getCurrentSession().get(HeartRate.class, heartRateId);
    }

    @Override
    public void deleteHeartRate(HeartRate heartRate) {
        sessionFactory.getCurrentSession().delete(heartRate);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<HeartRate> getByPage(int firstResult, int maxResult) {
        final String sql = "FROM HeartRate h order by h.date DESC";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
   
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<HeartRate> findHeartRatesByDateRange(Date from, Date to) {
        Query query = sessionFactory.getCurrentSession().createQuery(FIND_BY_DATE_RANGE);
        query.setParameter("from", from, TemporalType.DATE);
        query.setParameter("to", to, TemporalType.DATE);
        return query.getResultList();
    }
}