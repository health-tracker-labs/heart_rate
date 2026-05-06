package com.sergtm.health.tracker.integration.openweather.model.pressure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtmosphericPressureRecord {
    private String dt_txt;
    private Main main;
}
