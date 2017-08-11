package com.sergtm.dao.impl;

import com.sergtm.dao.IHeartRateDao;
import com.sergtm.entities.HeartRate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Created by Sergey on 18.07.2017.
 */
@Repository
@Transactional
public class HeartRateDaoImpl implements IHeartRateDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addHeartRate(HeartRate heartRate) {
        sessionFactory.getCurrentSession().save(heartRate);
    }

    @Override
    public void updateHeartRate(Long heartRateId, HeartRate heartRate) {

    }

    @Override
    public HeartRate getById(Long heartRateId) {
        return sessionFactory.getCurrentSession().get(HeartRate.class, heartRateId);
    }

    @Override
    public void deleteHeartRate(HeartRate heartRate) {
        sessionFactory.getCurrentSession().delete(heartRate);
    }


    @Override
    public Collection<HeartRate> getByPage(int firstResult, int maxResult) {
        String sql = "FROM HeartRate h order by h.date DESC";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }
}
