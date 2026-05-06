package com.sergtm.health.tracker.integration.openweather.mapper;

import com.sergtm.health.tracker.integration.openweather.model.weather.Main;
import com.sergtm.health.tracker.integration.openweather.model.weather.Weather;
import com.sergtm.health.tracker.integration.openweather.model.weather.WeatherModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class WeatherMapperTest {
    private static final String OPEN_WEATHER_ICON_URL = "http://openweathermap.org/img/w/%s.png";

    private static final Double TODAY_WEATHER_TEMPERATURE = 28.0d;

    private static final String SUNNY_WEATHER_ICON = "sunny_light";
    private static final String RAINY_WEATHER_ICON = "rainy_light";

    private static final String SUNNY_WEATHER_DESCRIPTION = "Sunny";
    private static final String RAINY_WEATHER_DESCRIPTION = "Rainy";

    private static final String SUNNY_WEATHER_ICON_URL = String.format(
            OPEN_WEATHER_ICON_URL,
            SUNNY_WEATHER_ICON);
    private static final String RAINY_WEATHER_ICON_URL = String.format(
            OPEN_WEATHER_ICON_URL,
            RAINY_WEATHER_ICON);

    private static final Long WEATHER_ENTITY_ID = 1L;

    private WeatherMapper weatherMapper = Mappers.getMapper(WeatherMapper.class);

    @Test
    void update_shouldPopulateWeatherEntity_whenWeatherEntityDoesNotExistInDB() {
        Weather[] weathers = new Weather[]{
                createTodayWeather(RAINY_WEATHER_ICON, RAINY_WEATHER_DESCRIPTION)
        };
        WeatherModel weatherModel = createWeatherModel(weathers);
        com.sergtm.health.tracker.persistence.entity.Weather weatherEntity =
                new com.sergtm.health.tracker.persistence.entity.Weather();

        weatherMapper.update(weatherModel, weatherEntity);

        assertNull(weatherEntity.getId());
        assertEquals(RAINY_WEATHER_ICON_URL, weatherEntity.getIconUrl());
        assertEquals(RAINY_WEATHER_DESCRIPTION, weatherEntity.getDescription());
        assertEquals(TODAY_WEATHER_TEMPERATURE, weatherEntity.getTemperature());
    }

    @Test
    void update_shouldUpdateWeatherEntity_whenWeatherEntityExistInDB() {
        Weather[] weathers = new Weather[]{
                createTodayWeather(SUNNY_WEATHER_ICON, SUNNY_WEATHER_DESCRIPTION)
        };
        WeatherModel weatherModel = createWeatherModel(weathers);
        com.sergtm.health.tracker.persistence.entity.Weather weatherEntity = createWeatherEntity(
                RAINY_WEATHER_ICON,
                RAINY_WEATHER_DESCRIPTION);

        weatherMapper.update(weatherModel, weatherEntity);

        assertNotNull(weatherEntity.getId());
        assertEquals(SUNNY_WEATHER_ICON_URL, weatherEntity.getIconUrl());
        assertEquals(SUNNY_WEATHER_DESCRIPTION, weatherEntity.getDescription());
        assertEquals(TODAY_WEATHER_TEMPERATURE, weatherEntity.getTemperature());
    }

    private static WeatherModel createWeatherModel(Weather[] weathers) {
        return WeatherModel.builder()
                .weather(weathers)
                .main(createTodayWeatherMain())
                .build();
    }

    private static Main createTodayWeatherMain() {
        return new Main(TODAY_WEATHER_TEMPERATURE);
    }

    private static Weather createTodayWeather(String icon, String description) {
        return Weather.builder()
                .description(description)
                .icon(icon)
                .build();
    }

    private com.sergtm.health.tracker.persistence.entity.Weather createWeatherEntity(
            String icon,
            String description
    ) {
        com.sergtm.health.tracker.persistence.entity.Weather weather =
                new com.sergtm.health.tracker.persistence.entity.Weather();

        weather.setId(WEATHER_ENTITY_ID);
        weather.setIconUrl(icon);
        weather.setTemperature(TODAY_WEATHER_TEMPERATURE.longValue());
        weather.setDescription(description);

        return weather;
    }
}
