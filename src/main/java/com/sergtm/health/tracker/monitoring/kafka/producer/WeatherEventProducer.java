package com.sergtm.health.tracker.monitoring.kafka.producer;

import com.sergtm.health.tracker.monitoring.kafka.event.WeatherEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class WeatherEventProducer extends EventProducer<WeatherEvent> {
    @Value("${open-weather.forecast.5days.topic}")
    private String topic;

    public WeatherEventProducer(KafkaTemplate<String, WeatherEvent> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public String getTopicName() {
        return topic;
    }
}
