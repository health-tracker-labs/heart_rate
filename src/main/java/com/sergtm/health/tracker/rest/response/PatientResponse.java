package com.sergtm.health.tracker.rest.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientResponse {
    private Long id;
    private PersonResponse person;
}
