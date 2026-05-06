package com.sergtm.health.tracker.monitoring.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class WeatherBatchApplicationEvent {
    private List<WeatherApplicationEvent> eventList;
}
