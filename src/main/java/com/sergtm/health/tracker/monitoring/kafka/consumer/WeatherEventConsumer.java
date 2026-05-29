package com.sergtm.health.tracker.monitoring.kafka.consumer;

import com.sergtm.health.tracker.monitoring.kafka.event.WeatherEvent;
import com.sergtm.health.tracker.monitoring.service.AssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeatherEventConsumer {
    private static final String RECEIVED_MSG = "Received message: %s - %s";

    @Value("${open-weather.forecast.5days.topic}")
    private String topic;

    @Autowired
    private AssessmentService assessmentService;

    @KafkaListener(topics = "${open-weather.forecast.5days.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void listen(WeatherEvent event) {
        String messageInfo = String.format(RECEIVED_MSG,
                event.getDt(),
                event.getPressure());
        log.info(messageInfo);
        assessmentService.updateWeather(event.getPressure());
    }
}
