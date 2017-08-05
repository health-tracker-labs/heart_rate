package com.sergtm.dao;

import com.sergtm.entities.HeartRate;

import java.util.Collection;

/**
 * Created by Sergey on 18.07.2017.
 */
public interface IHeartRateDao {
    void addHeartRate(HeartRate heartRate);
    void updateHeartRate(Long heartRateId, HeartRate heartRate);
    HeartRate getHeartRateById(Long heartRateId);
    void deleteHeartRate(Long heartRateId);
    Collection getAllHeartRates();
}
