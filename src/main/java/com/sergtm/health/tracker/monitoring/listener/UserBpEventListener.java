package com.sergtm.health.tracker.monitoring.listener;

import com.sergtm.health.tracker.monitoring.event.UserBpApplicationEvent;
import com.sergtm.health.tracker.monitoring.kafka.event.UserBpEvent;
import com.sergtm.health.tracker.monitoring.kafka.producer.UserBpProducer;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

public class UserBpEventListener {
    private final UserBpProducer userBpProducer;

    public UserBpEventListener(UserBpProducer userBpProducer) {
        this.userBpProducer = userBpProducer;
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onApplicationEvent(UserBpApplicationEvent event) {
        UserBpEvent userBpEvent = UserBpEvent.builder()
                .timestamp(event.getTimestamp())
                .systolic(event.getSystolic())
                .diastolic(event.getDiastolic())
                .build();
        userBpProducer.sendMessage(userBpEvent);
    }
}
