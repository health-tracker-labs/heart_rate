package com.sergtm.health.tracker.rest.response;

import lombok.Data;

@Data
public class PatientResponse {
    private Long id;
    private PersonResponse person;
}
