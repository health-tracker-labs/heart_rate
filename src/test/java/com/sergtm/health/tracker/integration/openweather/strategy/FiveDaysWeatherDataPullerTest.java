package com.sergtm.health.tracker.integration.openweather.strategy;

import com.sergtm.health.tracker.integration.openweather.client.OpenWeatherApiClient;
import com.sergtm.health.tracker.integration.openweather.model.pressure.AtmosphericPressureModel;
import com.sergtm.health.tracker.integration.openweather.model.pressure.AtmosphericPressureRecord;
import com.sergtm.health.tracker.integration.openweather.model.pressure.Main;
import com.sergtm.health.tracker.monitoring.event.WeatherApplicationEvent;
import com.sergtm.health.tracker.monitoring.event.WeatherBatchApplicationEvent;
import com.sergtm.health.tracker.persistence.entity.Pressure;
import com.sergtm.health.tracker.persistence.repository.PressureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.FIVE_DAYS_FORECAST;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class FiveDaysWeatherDataPullerTest {
    private static final double MM_HG_TRANSLATION = 1.33322387415;

    private static final String FORECAST_5_DAYS_NAME = "forecastUrl";
    private static final String FORECAST_5_DAYS_URL = "http://api.openweathermap.org/data/2.5/forecast";

    private static final double ATMOSPHERIC_PRESSURE_750 = 750;
    private static final double ATMOSPHERIC_PRESSURE_752 = 752;
    private static final double ATMOSPHERIC_PRESSURE_740 = 740;

    public static final LocalDate ATMOSPHERIC_LD_2026_05_12 = LocalDate.of(2026, 5, 12);
    public static final LocalDate ATMOSPHERIC_LD_2026_05_13 = LocalDate.of(2026, 5, 13);

    @Mock
    private OpenWeatherApiClient helper;
    @Mock
    private PressureRepository pressureRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Captor
    private ArgumentCaptor<List<Pressure>> pressureEntriesCaptor;
    @Captor
    private ArgumentCaptor<WeatherBatchApplicationEvent> weatherBatchApplicationEventCaptor;

    @InjectMocks
    private FiveDaysWeatherDataPuller testedInstance;

    @BeforeEach
    void setUp() {
        setField(testedInstance, FORECAST_5_DAYS_NAME, FORECAST_5_DAYS_URL);
    }

    @Test
    void updatesWeatherData_shouldCalculateAveragePressurePerDay_whenRecordsForOneDayReceived() {
        AtmosphericPressureRecord[] pressureRecordsOn12OfMay = new AtmosphericPressureRecord[] {
                createAtmosphericPressureRecord(ATMOSPHERIC_PRESSURE_750, "2026-05-12 12:00:00"),
                createAtmosphericPressureRecord(ATMOSPHERIC_PRESSURE_752, "2026-05-12 14:00:00"),
                createAtmosphericPressureRecord(ATMOSPHERIC_PRESSURE_740, "2026-05-12 16:00:00")
        };

        doReturn(createAtmosphericPressureModel(pressureRecordsOn12OfMay))
                .when(helper)
                .exchange(FORECAST_5_DAYS_URL, AtmosphericPressureModel.class);
        doNothing().when(pressureRepository).deleteAllByDateIn(anyCollection());

        testedInstance.updateWeatherData();

        verify(pressureRepository).saveAll(pressureEntriesCaptor.capture());

        List<Pressure> pressureEntities = pressureEntriesCaptor.getValue();
        assertEquals(1, pressureEntities.size());
        assertPressureEntity(ATMOSPHERIC_LD_2026_05_12,
                pressureRecordsOn12OfMay,
                pressureEntities.get(0));

        verify(applicationEventPublisher).publishEvent(weatherBatchApplicationEventCaptor.capture());

        WeatherBatchApplicationEvent weatherBatchApplicationEvent = weatherBatchApplicationEventCaptor.getValue();
        List<WeatherApplicationEvent> weatherApplicationEvents = new ArrayList<>(weatherBatchApplicationEvent.getEventList());

        assertNotNull(weatherBatchApplicationEvent);
        assertEquals(1, weatherApplicationEvents.size());
        assertWeatherApplicationEvent(ATMOSPHERIC_LD_2026_05_12,
                pressureRecordsOn12OfMay,
                weatherApplicationEvents.get(0));
    }

    @Test
    void updatesWeatherData_shouldCalculateAveragePressurePerDay_whenRecordsForSeveralDaysReceived() {
        AtmosphericPressureRecord[] pressureRecordsOn12OfMay = new AtmosphericPressureRecord[] {
                createAtmosphericPressureRecord(ATMOSPHERIC_PRESSURE_750, "2026-05-12 12:00:00"),
                createAtmosphericPressureRecord(ATMOSPHERIC_PRESSURE_752, "2026-05-12 14:00:00")
        };
        AtmosphericPressureRecord[] pressureRecordsOn13OfMay = new AtmosphericPressureRecord[] {
                createAtmosphericPressureRecord(ATMOSPHERIC_PRESSURE_740, "2026-05-13 12:00:00")
        };

        doReturn(createAtmosphericPressureModel(Stream.concat(
                        stream(pressureRecordsOn12OfMay),
                        stream(pressureRecordsOn13OfMay))
                .toArray(AtmosphericPressureRecord[]::new)))
                .when(helper)
                .exchange(FORECAST_5_DAYS_URL, AtmosphericPressureModel.class);
        doNothing().when(pressureRepository).deleteAllByDateIn(anyCollection());

        testedInstance.updateWeatherData();

        verify(pressureRepository).saveAll(pressureEntriesCaptor.capture());

        List<Pressure> pressureEntities = new ArrayList<>(pressureEntriesCaptor.getValue());
        assertEquals(2, pressureEntities.size());

        pressureEntities.sort(Comparator.comparing(Pressure::getDate));

        assertPressureEntity(
                ATMOSPHERIC_LD_2026_05_12,
                pressureRecordsOn12OfMay,
                pressureEntities.get(0));

        assertPressureEntity(
                ATMOSPHERIC_LD_2026_05_13,
                pressureRecordsOn13OfMay,
                pressureEntities.get(1));

        verify(applicationEventPublisher).publishEvent(weatherBatchApplicationEventCaptor.capture());

        WeatherBatchApplicationEvent weatherBatchApplicationEvent = weatherBatchApplicationEventCaptor.getValue();

        List<WeatherApplicationEvent> weatherApplicationEvents = new ArrayList<>(weatherBatchApplicationEvent.getEventList());
        weatherApplicationEvents
                .sort(Comparator.comparing(WeatherApplicationEvent::getDt));

        assertNotNull(weatherBatchApplicationEvent);
        assertEquals(2, weatherApplicationEvents.size());

        assertWeatherApplicationEvent(ATMOSPHERIC_LD_2026_05_12,
                pressureRecordsOn12OfMay,
                weatherBatchApplicationEvent.getEventList().get(0));
        assertWeatherApplicationEvent(ATMOSPHERIC_LD_2026_05_13,
                pressureRecordsOn13OfMay,
                weatherBatchApplicationEvent.getEventList().get(1));
    }

    @Test
    void getType_shouldReturnFiveDaysWeatherType() {
        assertEquals(FIVE_DAYS_FORECAST, testedInstance.getType());
    }

    private static void assertPressureEntity(
            LocalDate date,
            AtmosphericPressureRecord[] atmosphericPressureRecords,
            Pressure pressure) {
        double expectedPressure = calculateExpectedPressure(atmosphericPressureRecords);

        assertEquals(date, pressure.getDate());
        assertEquals(expectedPressure, pressure.getPressure(), 0.01);
    }

    private static void assertWeatherApplicationEvent(
            LocalDate date,
            AtmosphericPressureRecord[] atmosphericPressureRecords,
            WeatherApplicationEvent event
    ) {
        double expectedPressure = calculateExpectedPressure(atmosphericPressureRecords);

        assertEquals(date, event.getDt());
        assertEquals(expectedPressure, event.getPressure(), 0.01);
    }

    private static double calculateExpectedPressure(AtmosphericPressureRecord[] atmosphericPressureRecords) {
        return stream(atmosphericPressureRecords)
                .map(AtmosphericPressureRecord::getMain)
                .mapToDouble(Main::getPressure)
                .reduce(0, Double::sum) / atmosphericPressureRecords.length / MM_HG_TRANSLATION;
    }

    private static AtmosphericPressureRecord createAtmosphericPressureRecord(
            double pressure,
            String dt
    ) {
        return AtmosphericPressureRecord.builder()
                .dt_txt(dt)
                .main(new Main(pressure))
                .build();
    }

    private static AtmosphericPressureModel createAtmosphericPressureModel(
            AtmosphericPressureRecord[] pressureRecords
    ) {
        return AtmosphericPressureModel.builder()
                .message("")
                .list(pressureRecords)
                .build();
    }

}
