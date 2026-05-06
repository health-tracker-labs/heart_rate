package com.sergtm.health.tracker.monitoring.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserBpApplicationEvent {
    private LocalDateTime timestamp;
    private int systolic;
    private int diastolic;
}
