package com.sergtm.health.tracker.monitoring.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WeatherApplicationEvent {
    private LocalDate dt;
    private double pressure;
}
