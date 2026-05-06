package com.sergtm.health.tracker.monitoring.listener;

import com.sergtm.health.tracker.monitoring.event.WeatherBatchApplicationEvent;
import com.sergtm.health.tracker.monitoring.kafka.event.WeatherEvent;
import com.sergtm.health.tracker.monitoring.kafka.producer.WeatherEventProducer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
public class WeatherEventListener {
    private final WeatherEventProducer weatherEventProducer;

    public WeatherEventListener(WeatherEventProducer weatherEventProducer) {
        this.weatherEventProducer = weatherEventProducer;
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onApplicationEvent(WeatherBatchApplicationEvent batch) {
        batch.getEventList().forEach(event -> {
            WeatherEvent weatherEvent = WeatherEvent.builder()
                    .dt(event.getDt())
                    .pressure(event.getPressure())
                    .build();
            weatherEventProducer.sendMessage(weatherEvent);
        });
    }
}
