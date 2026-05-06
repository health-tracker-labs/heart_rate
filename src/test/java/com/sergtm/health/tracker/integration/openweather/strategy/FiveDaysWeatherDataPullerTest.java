package com.sergtm.health.tracker.integration.openweather.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.FIVE_DAYS_FORECAST;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FiveDaysWeatherDataPullerTest {
    @InjectMocks
    private FiveDaysWeatherDataPuller testedInstance;

    @Test
    void shouldReturnFiveDaysWeatherTypeForFiveDaysWeatherDataPuller() {
        assertEquals(FIVE_DAYS_FORECAST, testedInstance.getType());
    }
}
