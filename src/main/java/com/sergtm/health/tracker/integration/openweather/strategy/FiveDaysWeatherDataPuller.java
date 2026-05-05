package com.sergtm.health.tracker.integration.openweather.strategy;

import com.sergtm.dao.IPressureDao;
import com.sergtm.entities.Pressure;
import com.sergtm.health.tracker.integration.openweather.WeatherDataPuller;
import com.sergtm.health.tracker.integration.openweather.WeatherPullerType;
import com.sergtm.health.tracker.integration.openweather.client.OpenWeatherApiClient;
import com.sergtm.health.tracker.integration.openweather.model.pressure.RestPostsModel;
import com.sergtm.health.tracker.monitoring.kafka.event.WeatherEvent;
import com.sergtm.health.tracker.monitoring.kafka.producer.WeatherEventProducer;
import com.sergtm.service.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.FIVE_DAYS_FORECAST;
import static com.sergtm.model.ServiceName.PressureService;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Component
public class FiveDaysWeatherDataPuller implements WeatherDataPuller {
    private static final double MM_HG_TRANSLATION = 1.33322387415;
    private static final DateTimeFormatter DT_TEXT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private IStatusService statusService;
    @Autowired
    private IPressureDao pressureDao;

    private final OpenWeatherApiClient client;
    private final WeatherEventProducer weatherEventProducer;

    @Value("${openWeatherMapUrl}")
    private String openWhetherMapUrl;

    public FiveDaysWeatherDataPuller(
            OpenWeatherApiClient client,
            WeatherEventProducer weatherEventProducer
    ) {
        this.client = client;
        this.weatherEventProducer = weatherEventProducer;
    }

    @Override
    @Transactional
    public void updateWeatherData() {
        System.out.println("Entered");

        RestPostsModel rpm = client.exchange(openWhetherMapUrl, RestPostsModel.class);
        Map<LocalDate, Double> forecast5Days = calculateDailyAverageWeatherPressure(rpm);

        if (isNotEmpty(forecast5Days)) {
            try {
                save(forecast5Days);
            } finally {
                statusService.updateAndSave(PressureService);
            }
        }

        forecast5Days.forEach((key, value) -> {
            WeatherEvent weatherEvent = WeatherEvent.builder()
                    .dt(key)
                    .pressure(value)
                    .build();
            weatherEventProducer.sendMessage(weatherEvent);
        });

    }

    @Override
    public WeatherPullerType getType() {
        return FIVE_DAYS_FORECAST;
    }

    private void save(Map<LocalDate, Double> weatherMap) {
        for (LocalDate lc : weatherMap.keySet()) {
            Pressure pressure = pressureDao.getByDate(lc);
            if (pressure != null) {
                pressureDao.deletePressure(pressure);
            }
            Pressure pressureAdd = new Pressure();
            pressureAdd.setDate(lc);
            pressureAdd.setPressure(weatherMap.get(lc));
            pressureDao.addPressure(pressureAdd);
        }
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
}
