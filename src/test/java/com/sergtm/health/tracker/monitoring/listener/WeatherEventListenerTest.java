package com.sergtm.health.tracker.monitoring.listener;

import com.sergtm.health.tracker.monitoring.event.WeatherApplicationEvent;
import com.sergtm.health.tracker.monitoring.event.WeatherBatchApplicationEvent;
import com.sergtm.health.tracker.monitoring.kafka.event.WeatherEvent;
import com.sergtm.health.tracker.monitoring.kafka.producer.WeatherEventProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class WeatherEventListenerTest {
    @Mock
    private WeatherEventProducer weatherEventProducer;
    @InjectMocks
    private WeatherEventListener weatherEventListener;
    @Captor
    private ArgumentCaptor<WeatherEvent> weatherEventCaptor;

    @Test
    void onApplicationEvent_shouldSendWeatherEventsForEachEventInBatch_whenApplicationEventReceived() {
        WeatherApplicationEvent event1 = WeatherApplicationEvent.builder()
                .dt(LocalDate.now())
                .pressure(1013.25)
                .build();
        WeatherApplicationEvent event2 = WeatherApplicationEvent.builder()
                .dt(LocalDate.now().plusDays(1))
                .pressure(1015.0)
                .build();
        WeatherBatchApplicationEvent batch = new WeatherBatchApplicationEvent(List.of(event1, event2));

        weatherEventListener.onApplicationEvent(batch);

        verify(weatherEventProducer, times(2)).sendMessage(weatherEventCaptor.capture());

        List<WeatherEvent> sentEvents = weatherEventCaptor.getAllValues();

        assertEquals(2, sentEvents.size());
        assertEquals(event1.getDt(), sentEvents.get(0).getDt());
        assertEquals(event1.getPressure(), sentEvents.get(0).getPressure());
        assertEquals(event2.getDt(), sentEvents.get(1).getDt());
        assertEquals(event2.getPressure(), sentEvents.get(1).getPressure());
    }

    @Test
    void onApplicationEvent_shouldNotSendEvents_whenEventListIsEmpty() {
        WeatherBatchApplicationEvent batch = new WeatherBatchApplicationEvent(List.of());
        weatherEventListener.onApplicationEvent(batch);
        verifyNoInteractions(weatherEventProducer);
    }
}
