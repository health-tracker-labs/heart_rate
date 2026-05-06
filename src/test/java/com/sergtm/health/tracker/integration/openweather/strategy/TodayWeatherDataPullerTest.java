package com.sergtm.health.tracker.integration.openweather.strategy;

import com.sergtm.dao.IWeatherDao;
import com.sergtm.health.tracker.integration.openweather.client.OpenWeatherApiClient;
import com.sergtm.health.tracker.integration.openweather.mapper.WeatherMapper;
import com.sergtm.health.tracker.integration.openweather.model.weather.Main;
import com.sergtm.health.tracker.integration.openweather.model.weather.Weather;
import com.sergtm.health.tracker.integration.openweather.model.weather.WeatherModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.TODAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class TodayWeatherDataPullerTest {
    private static final String TODAY_WEATHER_NAME = "todayWeatherUrl";
    private static final String TODAY_WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/weather?units=metric";

    private static final String OPEN_WEATHER_ICON_URL = "http://openweathermap.org/img/w/%s.png";

    private static final String SUNNY_WEATHER_ICON = "sunny_light";
    private static final String RAINY_WEATHER_ICON = "rainy_light";

    private static final String SUNNY_WEATHER_DESCRIPTION = "Sunny";
    private static final String RAINY_WEATHER_DESCRIPTION = "Rainy";

    private static final Double TODAY_WEATHER_TEMPERATURE = 28.0d;

    private static final String SUNNY_WEATHER_ICON_URL = String.format(
            OPEN_WEATHER_ICON_URL,
            SUNNY_WEATHER_ICON);
    private static final String RAINY_WEATHER_ICON_URL = String.format(
            OPEN_WEATHER_ICON_URL,
            RAINY_WEATHER_ICON);

    private static final Long WEATHER_ENTITY_ID = 1L;

    @Mock
    private IWeatherDao weatherDao;
    @Mock
    private OpenWeatherApiClient helper;
    @Spy
    private WeatherMapper weatherMapper = Mappers.getMapper(WeatherMapper.class);
    @Captor
    private ArgumentCaptor<com.sergtm.health.tracker.persistence.entity.Weather> weatherEntityCaptor;

    @InjectMocks
    private TodayWeatherDataPuller testedInstance;

    @BeforeEach
    void setUp() {
        setField(testedInstance, TODAY_WEATHER_NAME, TODAY_WEATHER_URL);
    }

    @Test
    void updatesWeatherData_shouldPersistTodayWeather_whenWeatherEntryDoesNotExistInDB() {
        doReturn(createWeatherModel(
                new Weather[]{createTodayWeather(SUNNY_WEATHER_ICON, SUNNY_WEATHER_DESCRIPTION)}))
                .when(helper)
                .exchange(TODAY_WEATHER_URL, WeatherModel.class);
        doReturn(Optional.empty())
                .when(weatherDao)
                .getLatestWeather();

        testedInstance.updateWeatherData();
        verify(weatherDao).saveOrUpdate(weatherEntityCaptor.capture());

        com.sergtm.health.tracker.persistence.entity.Weather entity = weatherEntityCaptor.getValue();

        assertNull(entity.getId());
        assertEquals(SUNNY_WEATHER_ICON_URL, entity.getIconUrl());
        assertEquals(SUNNY_WEATHER_DESCRIPTION, entity.getDescription());
        assertEquals(TODAY_WEATHER_TEMPERATURE, entity.getTemperature());
        assertNotNull(entity.getDate());
    }

    @Test
    void updatesWeatherData_shouldUpdateTodayWeather_whenWeatherEntryExistInDB() {
        doReturn(createWeatherModel(new Weather[]{createTodayWeather(RAINY_WEATHER_ICON, RAINY_WEATHER_DESCRIPTION)}))
                .when(helper)
                .exchange(TODAY_WEATHER_URL, WeatherModel.class);
        doReturn(Optional.of(createWeatherEntity()))
                .when(weatherDao)
                .getLatestWeather();

        testedInstance.updateWeatherData();
        verify(weatherDao).saveOrUpdate(weatherEntityCaptor.capture());

        com.sergtm.health.tracker.persistence.entity.Weather entity = weatherEntityCaptor.getValue();

        assertEquals(WEATHER_ENTITY_ID, entity.getId());
        assertEquals(RAINY_WEATHER_ICON_URL, entity.getIconUrl());
        assertEquals(RAINY_WEATHER_DESCRIPTION, entity.getDescription());
        assertEquals(TODAY_WEATHER_TEMPERATURE, entity.getTemperature());
        assertNotNull(entity.getDate());
    }

    @Test
    void getType_shouldReturnTodayWeatherType() {
        assertEquals(TODAY, testedInstance.getType());
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

    private com.sergtm.health.tracker.persistence.entity.Weather createWeatherEntity() {
        com.sergtm.health.tracker.persistence.entity.Weather weather =
                new com.sergtm.health.tracker.persistence.entity.Weather();

        weather.setId(WEATHER_ENTITY_ID);
        weather.setIconUrl(SUNNY_WEATHER_ICON_URL);
        weather.setTemperature(TODAY_WEATHER_TEMPERATURE.longValue());
        weather.setDescription(SUNNY_WEATHER_DESCRIPTION);

        return weather;
    }
}
