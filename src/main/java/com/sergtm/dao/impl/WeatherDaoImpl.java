package com.sergtm.dao.impl;

import com.sergtm.dao.IWeatherDao;
import com.sergtm.health.tracker.persistence.entity.Weather;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class WeatherDaoImpl implements IWeatherDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Weather> getLatestWeather() {
        String sql = "FROM Weather";
        Query query = entityManager.createQuery(sql);
        List<Weather> weatherList = query.getResultList();
        if (weatherList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(weatherList.get(0));
    }

    @Override
    public void saveOrUpdate(Weather weather) {
        entityManager.merge(weather);
    }
}
