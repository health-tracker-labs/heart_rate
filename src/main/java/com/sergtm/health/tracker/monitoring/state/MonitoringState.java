package com.sergtm.health.tracker.monitoring.state;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class MonitoringState {
    private Double lastWeather;
    private Double lastUserBp;
}
