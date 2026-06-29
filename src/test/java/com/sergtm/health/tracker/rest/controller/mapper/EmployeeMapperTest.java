package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.rest.response.EmployeeResponse;
import com.sergtm.health.tracker.rest.response.PatientResponse;
import com.sergtm.health.tracker.rest.response.PersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static com.sergtm.health.tracker.testsupport.entry.EmployeeEntryFixture.createEmployeeBuilder;
import static com.sergtm.health.tracker.testsupport.response.EmployeeResponseFixture.createEmployeeResponseBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {
    private static final Long FIRST_EMPLOYEE_ID = 1L;
    private static final Long SECOND_EMPLOYEE_ID = 2L;

    @Mock
    private PersonMapper personMapper;
    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private EmployeeMapperImpl employeeMapper;

    @Mock
    private Person person;
    @Mock
    private PersonResponse personResponse;
    @Mock
    private Patient patient;
    @Mock
    private PatientResponse patientResponse;

    @BeforeEach
    void setUp() {
        // PersonMapper is tested separately
        doReturn(personResponse)
                .when(personMapper)
                .toResponse(any(Person.class));

        // PatientMapper is tested separately
        doReturn(List.of(patientResponse))
                .when(patientMapper)
                .toResponses(any());
    }

    @Test
    void toResponse_shouldMapEmployeeToEmployeeResponse() {
        Employee employee = createEmployeeBuilder()
                .id(FIRST_EMPLOYEE_ID)
                .patients(Set.of(patient))
                .person(person)
                .build();

        EmployeeResponse response = employeeMapper.toResponse(employee);
        EmployeeResponse expectedResponse = createEmployeeResponseBuilder()
                .id(FIRST_EMPLOYEE_ID)
                .patients(List.of(patientResponse))
                .person(personResponse)
                .build();

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void toResponses_shouldMapEmployeesToEmployeeResponses() {
        List<EmployeeResponse> responses = employeeMapper.toResponses(List.of(
                createEmployeeBuilder()
                        .id(FIRST_EMPLOYEE_ID)
                        .patients(Set.of(patient))
                        .person(person)
                        .build(),
                createEmployeeBuilder()
                        .id(SECOND_EMPLOYEE_ID)
                        .patients(Set.of(patient))
                        .person(person)
                        .build()));
        List<EmployeeResponse> expectedResponses = List.of(
                createEmployeeResponseBuilder()
                        .id(FIRST_EMPLOYEE_ID)
                        .patients(List.of(patientResponse))
                        .person(personResponse)
                        .build(),
                createEmployeeResponseBuilder()
                        .id(SECOND_EMPLOYEE_ID)
                        .patients(List.of(patientResponse))
                        .person(personResponse)
                        .build());

        assertThat(responses)
                .containsExactlyInAnyOrderElementsOf(expectedResponses);
    }
}
