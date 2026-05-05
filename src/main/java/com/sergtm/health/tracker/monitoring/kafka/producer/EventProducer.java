package com.sergtm.health.tracker.monitoring.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;

public abstract class EventProducer<T> {
    private final KafkaTemplate<String, T> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(T event) {
        kafkaTemplate.send(getTopicName(), event);
    }

    public abstract String getTopicName();
}
