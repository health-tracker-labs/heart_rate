package com.sergtm.health.tracker.integration.openweather.mapper;

import com.sergtm.health.tracker.integration.openweather.model.weather.WeatherModel;
import com.sergtm.health.tracker.persistence.entity.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Arrays;

import static io.netty.util.internal.StringUtil.EMPTY_STRING;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    String OPEN_WEATHER_MAP_URL = "http://openweathermap.org/img/w/%s.png";

    default long roundTemperature(WeatherModel model) {
        return Math.round(model.getMain().getTemp());
    }

    @Mapping(target = "temperature", expression = "java(roundTemperature(model))")
    @Mapping(target = "description", expression = "java(getDescription(model))")
    @Mapping(target = "iconUrl", expression = "java(getIconUrl(model))")
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    void update(WeatherModel model, @MappingTarget Weather weather);

    default String getDescription(WeatherModel model) {
        return Arrays.stream(model.getWeather())
                .map(weather -> weather.getDescription())
                .findFirst()
                .orElse(EMPTY_STRING);
    }

    default String getIconUrl(WeatherModel model) {
        String icon = Arrays.stream(model.getWeather())
                .map(weather -> weather.getIcon())
                .findFirst()
                .orElse(EMPTY_STRING);

        return String.format(
                OPEN_WEATHER_MAP_URL,
                icon);
    }
}
