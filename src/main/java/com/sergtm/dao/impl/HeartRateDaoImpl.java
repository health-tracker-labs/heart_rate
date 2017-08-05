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
    SessionFactory sessionFactory;

    @Override
    public void addHeartRate(HeartRate heartRate) {
        sessionFactory.getCurrentSession().save(heartRate);
    }

    @Override
    public void updateHeartRate(Long heartRateId, HeartRate heartRate) {

    }

    @Override
    public HeartRate getHeartRateById(Long heartRateId) {
        return null;
    }

    @Override
    public void deleteHeartRate(Long heartRateId) {

    }

    @Override
    public Collection getAllHeartRates() {
        String sql = "FROM HeartRate";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return query.getResultList();
    }
}
