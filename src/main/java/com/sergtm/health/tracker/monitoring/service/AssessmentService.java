package com.sergtm.health.tracker.monitoring.service;

import com.sergtm.health.tracker.monitoring.state.MonitoringState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
public class AssessmentService {
    @Autowired
    private MonitoringState monitoringState;

    public synchronized void updateWeather(double pressure) {
        monitoringState.setLastWeather(pressure);
        calculateIfReady();
    }

    public synchronized void updateUserBp(double systolic) {
        monitoringState.setLastUserBp(systolic);
        calculateIfReady();
    }

    private void calculateIfReady() {
        if (nonNull(monitoringState.getLastWeather())
                && nonNull(monitoringState.getLastUserBp())) {
            throw new IllegalStateException("Not implemented!");
        }
    }
}
