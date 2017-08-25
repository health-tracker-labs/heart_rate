package com.sergtm.service;

import com.sergtm.dto.HeartRateOnDay;
import com.sergtm.entities.HeartRate;
import com.sergtm.entities.IEntity;
import com.sergtm.form.AddHeartRateForm;

import java.util.Collection;
import java.util.Date;

public interface IHeartRateService {
    Collection<? extends IEntity> createHeartRate(int upperPressure, int lowerPressure, Date datetime, String firstName, String secondName);
    HeartRate createHeartRate(AddHeartRateForm form);

    void addHeartRateById(Long id, int upperPressure, int lowerPressure, Date datetime);
    Collection<? extends IEntity> getHelp(String query, String topicName);
    Collection<? extends IEntity> findByPage(int page);
    boolean deleteHeartRate(Long id);

    Collection<HeartRateOnDay> getChartData();
}
