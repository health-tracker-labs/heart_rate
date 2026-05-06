package com.sergtm.health.tracker.integration.openweather.model.weather;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    private String description;
    private String icon;
}
