package com.sergtm.dao;

import com.sergtm.dto.StatisticOnDay;
import com.sergtm.entities.HeartRate;
import com.sergtm.entities.HeartRateWithWeatherPressure;
import com.sergtm.entities.User;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Sergey on 18.07.2017.
 */
public interface IHeartRateDao {
    void addHeartRate(HeartRate heartRate);
    HeartRate getById(Long heartRateId);
    void deleteHeartRate(HeartRate heartRate);
    Collection<HeartRate> getByPage(int firstResult, int maxResult);
    //Collection<HeartRate> findHeartRatesByDateRange(Date from, Date from2, Long personId);
    Collection<HeartRateWithWeatherPressure> getData(Date from, Date from2, Long personId, User user);
}
