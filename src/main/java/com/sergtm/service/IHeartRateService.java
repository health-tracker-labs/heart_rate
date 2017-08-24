package com.sergtm.service;

import java.util.Collection;
import java.util.Date;

import com.sergtm.dto.HeartRateOnDay;
import com.sergtm.entities.HeartRate;
import com.sergtm.entities.IEntity;
import com.sergtm.form.AddHeartRateForm;

public interface IHeartRateService {
    Collection<? extends IEntity> createHeartRate(int upperPressure, int lowerPressure, Date datetime, String firstName, String secondName);
    HeartRate createHeartRate(AddHeartRateForm form);

    void addHeartRateById(Long id, int upperPressure, int lowerPressure, Date datetime);
    Collection<? extends IEntity> getHelp(String query, String topicName);
    Collection<? extends IEntity> findByPage(int page);
    void deleteHeartRate(Long id);

    Collection<HeartRateOnDay> getChartData();
}
