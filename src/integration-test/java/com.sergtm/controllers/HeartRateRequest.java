package com.sergtm.controllers;

import com.sergtm.TimeOfDay;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HeartRateRequest {
    private Long id;
    private Long personId;
    private Integer upperPressure;
    private Integer lowerPressure;
    private Integer bpm;
    private TimeOfDay timeOfDay;
}
