package com.sergtm.health.tracker.integration.openweather.strategy;

import com.sergtm.health.tracker.integration.openweather.WeatherDataPuller;
import com.sergtm.health.tracker.integration.openweather.WeatherPullerType;
import com.sergtm.health.tracker.integration.openweather.client.OpenWeatherApiClient;
import com.sergtm.health.tracker.integration.openweather.model.pressure.RestPostsModel;
import com.sergtm.health.tracker.monitoring.event.WeatherApplicationEvent;
import com.sergtm.health.tracker.monitoring.event.WeatherBatchApplicationEvent;
import com.sergtm.health.tracker.persistence.entity.Pressure;
import com.sergtm.health.tracker.persistence.repository.PressureRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.FIVE_DAYS_FORECAST;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Component
public class FiveDaysWeatherDataPuller implements WeatherDataPuller {
    private static final double MM_HG_TRANSLATION = 1.33322387415;
    private static final DateTimeFormatter DT_TEXT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final OpenWeatherApiClient client;
    private final PressureRepository pressureRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${open-weather.5days-forecast-url}")
    private String forecastUrl;

    public FiveDaysWeatherDataPuller(
            OpenWeatherApiClient client,
            ApplicationEventPublisher applicationEventPublisher,
            PressureRepository pressureRepository
    ) {
        this.client = client;
        this.pressureRepository = pressureRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @Transactional
    public void updateWeatherData() {
        System.out.println("Entered");

        RestPostsModel rpm = client.exchange(forecastUrl, RestPostsModel.class);
        Map<LocalDate, Double> forecast5Days = calculateDailyAverageWeatherPressure(rpm);

        if (isNotEmpty(forecast5Days)) {
            saveForecast(forecast5Days);

            List<WeatherApplicationEvent> events = forecast5Days
                    .entrySet()
                    .stream()
                    .map(this::createWeatherApplicationEvent)
                    .toList();
            applicationEventPublisher.publishEvent(
                    new WeatherBatchApplicationEvent(events)
            );
        }
    }

    private WeatherApplicationEvent createWeatherApplicationEvent(Map.Entry<LocalDate, Double> forecastEntry) {
        return WeatherApplicationEvent.builder()
                .dt(forecastEntry.getKey())
                .pressure(forecastEntry.getValue())
                .build();
    }

    private void saveForecast(Map<LocalDate, Double> weatherMap) {
        pressureRepository.deleteAllByDateIn(weatherMap.keySet());
        List<Pressure> pressures = weatherMap.entrySet().stream()
                .map(this::createPressure)
                .toList();
        pressureRepository.saveAll(pressures);
    }

    private Pressure createPressure(Map.Entry<LocalDate, Double> forecastEntry) {
        Pressure pressure = new Pressure();
        pressure.setDate(forecastEntry.getKey());
        pressure.setPressure(forecastEntry.getValue());

        return pressure;
    }

    private Map<LocalDate, Double> calculateDailyAverageWeatherPressure(RestPostsModel rpm) {
        return Arrays.stream(rpm.getList())
                .collect(Collectors.groupingBy(item ->
                                LocalDateTime.parse(item.getDt_txt(), DT_TEXT_FORMATTER).toLocalDate(),
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.summarizingDouble(item -> item.getMain().getPressure()),
                                (stat) -> stat.getAverage() / MM_HG_TRANSLATION
                        )
                ));
    }

    @Override
    public WeatherPullerType getType() {
        return FIVE_DAYS_FORECAST;
    }
}
