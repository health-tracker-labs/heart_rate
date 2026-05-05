package com.sergtm.health.tracker.integration.openweather;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toUnmodifiableMap;

@Component
public class WeatherPullerProvider {
    private static final String PULLER_TYPE_MUST_NOT_BE_MSG = "Type must not be null";
    private static final String PULLER_TYPE_HAS_DUPLICATE = "Duplicate Weather Data Puller for type: %s";
    private static final String UNSUPPORTED_PULLER_TYPE_MSG = "Unsupported Weather Data Puller: %s";

    private final Map<WeatherPullerType, WeatherDataPuller> weatherDataPullersMap;

    public WeatherPullerProvider(List<WeatherDataPuller> weatherDataPullers) {
        weatherDataPullersMap = weatherDataPullers.stream()
                .collect(toUnmodifiableMap(WeatherDataPuller::getType,
                        Function.identity(),
                        (p1, p2) -> {
                            throw new IllegalArgumentException(String.format(PULLER_TYPE_HAS_DUPLICATE, p1.getType()));
                        }));
    }

    public WeatherDataPuller get(WeatherPullerType type) {
        if (type == null) {
            throw new IllegalArgumentException(PULLER_TYPE_MUST_NOT_BE_MSG);
        }
        WeatherDataPuller weatherDataPuller = weatherDataPullersMap.get(type);
        if (weatherDataPuller == null) {
            throw new IllegalArgumentException(String.format(UNSUPPORTED_PULLER_TYPE_MSG, type));
        }
        return weatherDataPuller;
    }
}
