package com.sergtm.health.tracker.monitoring.kafka.event;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserBpEvent {
    private LocalDateTime timestamp;

    private int systolic;
    private int diastolic;
}
