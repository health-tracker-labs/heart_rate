package com.sergtm.health.tracker.integration.openweather.mapper;

import com.sergtm.health.tracker.persistence.entity.Weather;
import com.sergtm.health.tracker.integration.openweather.model.weather.WeatherModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    String OPEN_WEATHER_MAP_URL = "http://openweathermap.org/img/w/%s.png";

    @Mapping(target = "temperature", expression = "java(roundTemperature(model))")
    @Mapping(target = "description", expression = "java(getDescription(model))")
    @Mapping(target = "iconUrl", expression = "java(getIconUrl(model))")
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    void update(WeatherModel model, @MappingTarget Weather weather);

    default long roundTemperature(WeatherModel model) {
        return Math.round(model.getMain().getTemp());
    }

    default String getDescription(WeatherModel model) {
        return model.getWeather()[0].getDescription();
    }

    default String getIconUrl(WeatherModel model) {
        return String.format(
                OPEN_WEATHER_MAP_URL,
                model.getWeather()[0].getIcon()
        );
    }
}
