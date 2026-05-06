package com.sergtm.health.tracker.integration.openweather.model.weather;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherModel {
    private Weather[] weather;
    private Main main;
}
