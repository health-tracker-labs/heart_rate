package com.sergtm.health.tracker.rest.controller;

import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.rest.controller.mapper.PatientMapper;
import com.sergtm.health.tracker.rest.request.PatientRequest;
import com.sergtm.health.tracker.rest.response.PatientResponse;
import com.sergtm.health.tracker.service.impl.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/patients", produces = "application/json")
@RequiredArgsConstructor
public class PatientController {
    private final PatientMapper patientMapper;
    private final PatientService patientService;

    @GetMapping
    public Collection<PatientResponse> getPatients() {
        Collection<Patient> patients = patientService.getPatients();
        return patientMapper.toResponses(patients);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponse createPatient(@RequestBody @Valid PatientRequest request) {
        Patient patient = patientService.createPatient(request.getPersonId());
        return patientMapper.toResponse(patient);
    }
}
