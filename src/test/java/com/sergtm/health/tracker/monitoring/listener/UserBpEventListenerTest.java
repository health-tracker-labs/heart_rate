package com.sergtm.health.tracker.monitoring.listener;

import com.sergtm.health.tracker.monitoring.event.UserBpApplicationEvent;
import com.sergtm.health.tracker.monitoring.kafka.event.UserBpEvent;
import com.sergtm.health.tracker.monitoring.kafka.producer.UserBpProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserBpEventListenerTest {
    @Mock
    private UserBpProducer userBpProducer;
    @InjectMocks
    private UserBpEventListener userBpEventListener;
    @Captor
    private ArgumentCaptor<UserBpEvent> userBpEventCaptor;

    @Test
    void onApplicationEvent_shouldSendUserBpEvent_whenApplicationEventReceived() {
        UserBpApplicationEvent applicationEvent = UserBpApplicationEvent.builder()
                .timestamp(LocalDateTime.now())
                .systolic(120)
                .diastolic(80)
                .build();

        userBpEventListener.onApplicationEvent(applicationEvent);

        verify(userBpProducer).sendMessage(userBpEventCaptor.capture());
        UserBpEvent sentEvent = userBpEventCaptor.getValue();

        assertEquals(applicationEvent.getTimestamp(), sentEvent.getTimestamp());
        assertEquals(applicationEvent.getSystolic(), sentEvent.getSystolic());
        assertEquals(applicationEvent.getDiastolic(), sentEvent.getDiastolic());
    }
}
