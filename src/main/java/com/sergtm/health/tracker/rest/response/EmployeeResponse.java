package com.sergtm.health.tracker.rest.response;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponse {
    private Long id;
    private List<PatientResponse> patients;
    private PersonResponse person;
}
