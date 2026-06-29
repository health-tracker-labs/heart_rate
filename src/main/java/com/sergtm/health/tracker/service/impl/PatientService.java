package com.sergtm.health.tracker.service.impl;

import com.sergtm.health.tracker.exception.PatientNotFoundException;
import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.repository.PatientRepository;
import com.sergtm.health.tracker.service.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PatientService {
    private static final String CAN_NOT_FIND_PATIENT_BY_PATIENT_ID_MSG = "Can't find patient by patient id = %s";

    private final PatientRepository patientRepository;
    private final IPersonService personService;

    public Collection<Patient> getPatients() {
        return patientRepository.findAll();
    }

    @Transactional
    public Patient createPatient(Long personId) {
        Person person = personService.findByIdOrThrowException(personId);
        Patient patient = Patient.builder()
                .person(person)
                .build();
        return patientRepository.save(patient);
    }

    public Patient getPatientByIdOrThrowException(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(
                        String.format(CAN_NOT_FIND_PATIENT_BY_PATIENT_ID_MSG, patientId)));
    }
}
