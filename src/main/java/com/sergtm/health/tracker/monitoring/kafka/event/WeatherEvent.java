package com.sergtm.health.tracker.monitoring.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class WeatherEvent {
    private LocalDate dt;
    private double pressure;
}
