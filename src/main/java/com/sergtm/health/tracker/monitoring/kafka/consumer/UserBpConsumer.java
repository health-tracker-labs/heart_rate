package com.sergtm.health.tracker.monitoring.kafka.consumer;

import com.sergtm.health.tracker.monitoring.kafka.event.UserBpEvent;
import com.sergtm.health.tracker.monitoring.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserBpConsumer {
    @Value("${health-tracker.user.blood.pressure.topic}")
    private String topic;

    @Autowired
    private AssessmentService assessmentService;

    @KafkaListener(topics = "${health-tracker.user.blood.pressure.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserBpEvent event) {
        System.out.println("Received message: " + event.getTimestamp() + " - " +
                event.getSystolic() + " - " + event.getDiastolic());
        assessmentService.updateUserBp(event.getSystolic());
    }
}
