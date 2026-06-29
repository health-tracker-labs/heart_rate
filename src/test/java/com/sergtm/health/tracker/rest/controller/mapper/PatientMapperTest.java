package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.rest.response.PatientResponse;
import com.sergtm.health.tracker.rest.response.PersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.sergtm.health.tracker.testsupport.entry.PatientEntryFixture.createPatientBuilder;
import static com.sergtm.health.tracker.testsupport.response.PatientResponseFixture.createPatientResponseBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PatientMapperTest {
    private static final Long FIRST_PATIENT_ID = 1L;
    private static final Long SECOND_PATIENT_ID = 2L;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PatientMapperImpl patientMapper;

    @Mock
    private Person person;
    @Mock
    private PersonResponse personResponse;

    @BeforeEach
    void setUp() {
        // PersonMapper is tested separately
        doReturn(personResponse)
                .when(personMapper)
                .toResponse(any(Person.class));
    }

    @Test
    void toResponse_shouldMapPatientToPatientResponse() {
        Patient patient = createPatientBuilder()
                .id(FIRST_PATIENT_ID)
                .person(person)
                .build();

        PatientResponse response = patientMapper.toResponse(patient);
        PatientResponse expectedResponse = createPatientResponseBuilder()
                .id(FIRST_PATIENT_ID)
                .person(personResponse)
                .build();

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void toResponses_shouldMapPatientsToPatientResponses() {
        List<PatientResponse> responses = patientMapper.toResponses(List.of(
                createPatientBuilder()
                        .id(FIRST_PATIENT_ID)
                        .person(person)
                        .build(),
                createPatientBuilder()
                        .id(SECOND_PATIENT_ID)
                        .person(person)
                        .build()));
        List<PatientResponse> expectedResponses = List.of(
                createPatientResponseBuilder()
                        .id(FIRST_PATIENT_ID)
                        .person(personResponse)
                        .build(),
                createPatientResponseBuilder()
                        .id(SECOND_PATIENT_ID)
                        .person(personResponse)
                        .build());

        assertThat(responses)
                .containsExactlyInAnyOrderElementsOf(expectedResponses);
    }
}
