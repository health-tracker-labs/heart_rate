package com.sergtm.health.tracker.monitoring.kafka.consumer;

import com.sergtm.health.tracker.monitoring.kafka.event.UserBpEvent;
import com.sergtm.health.tracker.monitoring.service.AssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserBpConsumer {
    private static final String RECEIVED_MSG = "Received message: %s - %s - %s";

    @Value("${health-tracker.user.blood.pressure.topic}")
    private String topic;

    @Autowired
    private AssessmentService assessmentService;

    @KafkaListener(topics = "${health-tracker.user.blood.pressure.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserBpEvent event) {
        String messageInfo = String.format(RECEIVED_MSG,
                event.getTimestamp(),
                event.getSystolic(),
                event.getDiastolic());
        log.info(messageInfo);
        assessmentService.updateUserBp(event.getSystolic());
    }
}
