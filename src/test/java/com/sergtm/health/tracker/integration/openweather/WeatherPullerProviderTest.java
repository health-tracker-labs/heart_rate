package com.sergtm.health.tracker.integration.openweather;

import com.sergtm.health.tracker.integration.openweather.strategy.FiveDaysWeatherDataPuller;
import com.sergtm.health.tracker.integration.openweather.strategy.TodayWeatherDataPuller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.FIVE_DAYS_FORECAST;
import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.TODAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class WeatherPullerProviderTest {
    private static final String PULLER_TYPE_MUST_NOT_BE_MSG = "Type must not be null";
    private static final String UNSUPPORTED_PULLER_TYPE_MSG = "Unsupported Weather Data Puller: %s";
    private static final String PULLER_TYPE_HAS_DUPLICATE = "Duplicate Weather Data Puller for type: %s";

    @Mock
    private TodayWeatherDataPuller todayWeatherDataPuller;
    @Mock
    private FiveDaysWeatherDataPuller fiveDaysWeatherDataPuller;

    private WeatherPullerProvider provider;

    @BeforeEach
    void setUp() {
        doReturn(TODAY)
                .when(todayWeatherDataPuller)
                .getType();
        doReturn(FIVE_DAYS_FORECAST)
                .when(fiveDaysWeatherDataPuller)
                .getType();

        provider = new WeatherPullerProvider(List.of(
                todayWeatherDataPuller,
                fiveDaysWeatherDataPuller));
    }

    @Test
    void get_shouldThrowException_whenWeatherDataPullerTypeIsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> provider.get(null));
        assertEquals(PULLER_TYPE_MUST_NOT_BE_MSG, exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenWeatherPullersHaveDuplicateTypes() {
        doReturn(TODAY)
                .when(todayWeatherDataPuller)
                .getType();
        doReturn(TODAY)
                .when(fiveDaysWeatherDataPuller)
                .getType();

        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new WeatherPullerProvider(List.of(todayWeatherDataPuller, fiveDaysWeatherDataPuller)));
        assertEquals(String.format(PULLER_TYPE_HAS_DUPLICATE, TODAY), exception.getMessage());
    }

    @Test
    void get_shouldThrowException_whenWeatherDataPullerDoesNotExists() {
        provider = new WeatherPullerProvider(List.of());

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> provider.get(FIVE_DAYS_FORECAST));
        assertEquals(String.format(UNSUPPORTED_PULLER_TYPE_MSG, FIVE_DAYS_FORECAST), exception.getMessage());
    }

    @Test
    void get_shouldReturnWeatherDataPuller_whenWeatherDataPullerExists() {
        WeatherDataPuller weatherDataPuller = provider.get(FIVE_DAYS_FORECAST);
        assertSame(fiveDaysWeatherDataPuller, weatherDataPuller);
    }
}
