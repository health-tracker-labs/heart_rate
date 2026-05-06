package com.sergtm.health.tracker.integration.openweather.model.pressure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestPostsModel {
    private String message;
    private List[] list;
}