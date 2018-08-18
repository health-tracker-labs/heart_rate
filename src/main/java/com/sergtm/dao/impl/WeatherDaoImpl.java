package com.sergtm.dao.impl;

import com.sergtm.dao.IWeatherDao;
import com.sergtm.entities.Weather;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class WeatherDaoImpl implements IWeatherDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Weather> getLatestWeather() {
        String sql = "FROM Weather";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        List<Weather> weatherList = query.getResultList();
        if (weatherList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(weatherList.get(0));
    }

    @Override
    public void saveOrUpdate(Weather weather) {
        sessionFactory.getCurrentSession().saveOrUpdate(weather);
    }
}
