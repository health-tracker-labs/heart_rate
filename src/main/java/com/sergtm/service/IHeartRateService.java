package com.sergtm.service;

import com.sergtm.entities.HeartRate;
import com.sergtm.entities.IEntity;

import java.util.Collection;
import java.util.Date;

public interface IHeartRateService {
    Collection<HeartRate> findAll();
    Collection<? extends IEntity> createHeartRate(int upperPressure, int lowerPressure, Date datetime, String firstName, String secondName);
    void addHeartRateById(Long id, int upperPressure, int lowerPressure, Date datetime);
    Collection<? extends IEntity> getHelp(String query, String topicName);
}
