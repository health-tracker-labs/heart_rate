package com.sergtm.health.tracker.integration.openweather.model.pressure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AtmosphericPressureModel {
    private String message;
    private AtmosphericPressureRecord[] list;
}