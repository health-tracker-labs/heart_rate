package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.rest.response.PatientResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface PatientMapper {
    List<PatientResponse> toResponses(Iterable<Patient> patients);
    PatientResponse toResponse(Patient patient);
}
