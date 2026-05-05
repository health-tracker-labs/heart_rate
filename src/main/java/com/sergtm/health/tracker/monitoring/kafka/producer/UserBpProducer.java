package com.sergtm.health.tracker.monitoring.kafka.producer;

import com.sergtm.health.tracker.monitoring.kafka.event.UserBpEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserBpProducer extends EventProducer<UserBpEvent> {
    @Value("${health-tracker.user.blood.pressure.topic}")
    private String topic;

    public UserBpProducer(KafkaTemplate<String, UserBpEvent> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public String getTopicName() {
        return topic;
    }
}
