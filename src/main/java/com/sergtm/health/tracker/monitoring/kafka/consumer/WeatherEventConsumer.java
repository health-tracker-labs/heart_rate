package com.sergtm.health.tracker.monitoring.kafka.consumer;

import com.sergtm.health.tracker.monitoring.kafka.event.WeatherEvent;
import com.sergtm.health.tracker.monitoring.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class WeatherEventConsumer {
    @Value("${open-weather.forecast.5days.topic}")
    private String topic;

    @Autowired
    private AssessmentService assessmentService;

    @KafkaListener(topics = "${open-weather.forecast.5days.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void listen(WeatherEvent event) {
        System.out.println("Received message: " + event.getDt() + " - " + event.getPressure());
        assessmentService.updateWeather(event.getPressure());
    }
}
