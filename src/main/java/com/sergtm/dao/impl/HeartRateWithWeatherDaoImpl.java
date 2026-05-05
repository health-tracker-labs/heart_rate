package com.sergtm.dao.impl;

import com.sergtm.dao.IHeartRateWithWeatherDao;
import com.sergtm.entities.HeartRateWithWeatherPressure;
import com.sergtm.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Collection;

@Repository
@Transactional(readOnly=true)
public class HeartRateWithWeatherDaoImpl implements IHeartRateWithWeatherDao {
    private static final String FIND_HEART_RATE_WEATHER_PRESSURE_BY_DATE_RANGE =
            "FROM HeartRateWithWeatherPressure h where h.date between :from and :to and (h.person.id is null or h.person.id not in (select hp.person.id " +
                    "from HeartRateWithWeatherPressure hp join hp.person.staffMembers s " +
                    "where s.user.id != :user_id)) order by h.date";
    private static final String FIND_HEART_RATE_WEATHER_PRESSURE_BY_DATE_RANGE_AND_PERSON_ID =
            "FROM HeartRateWithWeatherPressure h where h.date between :from and :to and (h.person.id is null or h.person.id = :id) order by h.date";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<HeartRateWithWeatherPressure> getData(LocalDateTime from, LocalDateTime to, Long id, User user){
        Query query = entityManager.createQuery(id == null? FIND_HEART_RATE_WEATHER_PRESSURE_BY_DATE_RANGE : FIND_HEART_RATE_WEATHER_PRESSURE_BY_DATE_RANGE_AND_PERSON_ID);
        query.setParameter("from", from.toLocalDate());
        query.setParameter("to", to.toLocalDate());
        if (id != null) {
            query.setParameter("id", id);
        } else {
            query.setParameter("user_id", user.getId());
        }
        return query.getResultList();
    }
}
