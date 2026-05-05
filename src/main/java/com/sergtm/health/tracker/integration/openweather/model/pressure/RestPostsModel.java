package com.sergtm.health.tracker.integration.openweather.model.pressure;

import lombok.Getter;

@Getter
public class RestPostsModel {
    private String message;
    private List[] list;
}