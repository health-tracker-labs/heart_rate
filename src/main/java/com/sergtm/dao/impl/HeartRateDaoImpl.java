package com.sergtm.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import com.sergtm.dto.StatisticOnDay;
import com.sergtm.entities.HeartRateWithWeatherPressure;
import com.sergtm.entities.Pressure;
import com.sergtm.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import com.sergtm.dao.IHeartRateDao;
import com.sergtm.entities.HeartRate;

/**
 * Created by Sergey on 18.07.2017.
 */
@Repository
@Transactional
public class HeartRateDaoImpl implements IHeartRateDao {
    //private static final String FIND_BY_DATE_RANGE = "FROM HeartRate h where h.date between :from and :to order by h.date";
    //private static final String FIND_BY_DATE_RANGE_AND_PERSON_ID = "FROM HeartRate h where h.date between :from and :to and h.person.id = :id order by h.date";
    private static final String FIND_HEART_RATE_WEATHER_PRESSURE_BY_DATE_RANGE =
            "FROM HeartRateWithWeatherPressure h where h.date between :from and :to and (h.person.id is null or h.person.id not in (select hp.person.id " +
                    "from HeartRateWithWeatherPressure hp join hp.person.staffMembers s " +
                    "where s.user.id != :user_id)) order by h.date";
    private static final String FIND_HEART_RATE_WEATHER_PRESSURE_BY_DATE_RANGE_AND_PERSON_ID =
            "FROM HeartRateWithWeatherPressure h where h.date between :from and :to and (h.person.id is null or h.person.id = :id) order by h.date"; /* and " +
                    "(h.person.id is null or h.person.id not in (select hp.person.id " +
                    "from HeartRateWithWeatherPressure hp join hp.person.staffMembers s " +
                    "where s.user.id != :user_id)) order by h.date)";*/

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

    @Override
    public Collection<HeartRateWithWeatherPressure> getData(Date from, Date to, Long id, User user){
        Query query = sessionFactory.getCurrentSession().createQuery(id == null? FIND_HEART_RATE_WEATHER_PRESSURE_BY_DATE_RANGE : FIND_HEART_RATE_WEATHER_PRESSURE_BY_DATE_RANGE_AND_PERSON_ID);
        query.setParameter("from", from, TemporalType.DATE);
        query.setParameter("to", to, TemporalType.DATE);
        if (id != null) {
            query.setParameter("id", id);
        } else {
            query.setParameter("user_id", user.getId());
        }
        return query.getResultList();
    }
}