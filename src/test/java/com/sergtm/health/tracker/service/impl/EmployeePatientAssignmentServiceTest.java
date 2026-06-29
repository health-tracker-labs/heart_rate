package com.sergtm.health.tracker.service.impl;

import com.sergtm.health.tracker.exception.PatientIsNotAssignedToEmployeeException;
import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static com.sergtm.health.tracker.testsupport.entry.EmployeeEntryFixture.createEmployee;
import static com.sergtm.health.tracker.testsupport.entry.EmployeeEntryFixture.createEmployeeBuilder;
import static com.sergtm.health.tracker.testsupport.entry.PatientEntryFixture.createPatient;
import static com.sergtm.health.tracker.testsupport.entry.PatientEntryFixture.createPatientBuilder;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createFirstPerson;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createSecondPerson;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createThirdPerson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeePatientAssignmentServiceTest {
    private static final String PATIENT_IS_NOT_ASSIGNED_TO_EMPLOYEE_MSG =
            "Patient with id [%s] is not assigned to employee with id [%s]";

    private static final Long EMPLOYEE_ID = 1L;
    private static final Long PATIENT_ID = 2L;
    private static final Long UNASSIGNED_PATIENT_ID = 3L;

    @Mock
    private EmployeeService employeeService;
    @Mock
    private PatientService patientService;

    @InjectMocks
    private EmployeePatientAssignmentService employeePatientAssignmentService;

    @Test
    void assignPatientToEmployee_shouldReturnEmployeeWithAssignedPatient() {
        Patient patient = createPatient(createFirstPerson());
        doReturn(patient)
                .when(patientService)
                .getPatientByIdOrThrowException(PATIENT_ID);

        Employee employee = createEmployee(createThirdPerson(), new HashSet<>());
        doReturn(employee)
                .when(employeeService)
                .getEmployeeByIdOrThrowException(EMPLOYEE_ID);

        Employee actual = employeePatientAssignmentService.assignPatientToEmployee(EMPLOYEE_ID, PATIENT_ID);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(employee);

        verify(employeeService).getEmployeeByIdOrThrowException(EMPLOYEE_ID);
        verify(patientService).getPatientByIdOrThrowException(PATIENT_ID);
    }

    @Test
    void removePatientFromEmployee_shouldReturnEmployeeWithoutAssignedPatient() {
        Person person = createFirstPerson();

        Patient patient = createPatientBuilder()
                .id(PATIENT_ID)
                .person(person)
                .build();
        doReturn(patient)
                .when(patientService)
                .getPatientByIdOrThrowException(PATIENT_ID);

        Employee employee = createEmployee(createThirdPerson(), new HashSet<>(
                Set.of(createPatientBuilder()
                        .id(PATIENT_ID)
                        .person(person)
                        .build())));
        doReturn(employee)
                .when(employeeService)
                .getEmployeeByIdOrThrowException(EMPLOYEE_ID);

        Employee actual = employeePatientAssignmentService.removePatientFromEmployee(EMPLOYEE_ID, PATIENT_ID);

        assertThat(actual.getPatients()).isEmpty();
        verify(employeeService).getEmployeeByIdOrThrowException(EMPLOYEE_ID);
        verify(patientService).getPatientByIdOrThrowException(PATIENT_ID);
    }

    @Test
    void removePatientFromEmployee_shouldThrowException_whenPatientWasNotAssignedToEmployee() {
        Person unassignedPatientPerson = createFirstPerson();
        Patient unassignedPatient = createPatientBuilder()
                .id(UNASSIGNED_PATIENT_ID)
                .person(unassignedPatientPerson)
                .build();
        doReturn(unassignedPatient)
                .when(patientService)
                .getPatientByIdOrThrowException(UNASSIGNED_PATIENT_ID);

        Person employeePerson = createThirdPerson();
        Person patientPerson = createSecondPerson();

        Patient employeePatient = createPatientBuilder()
                .id(PATIENT_ID)
                .person(patientPerson)
                .build();

        Employee employee = createEmployeeBuilder()
                .id(EMPLOYEE_ID)
                .person(employeePerson)
                .patients(new HashSet<>(Set.of(employeePatient)))
                .build();
        doReturn(employee)
                .when(employeeService)
                .getEmployeeByIdOrThrowException(EMPLOYEE_ID);

        Throwable exception = assertThrows(PatientIsNotAssignedToEmployeeException.class,
                () -> employeePatientAssignmentService.removePatientFromEmployee(EMPLOYEE_ID, UNASSIGNED_PATIENT_ID));

        assertEquals(String.format(PATIENT_IS_NOT_ASSIGNED_TO_EMPLOYEE_MSG, UNASSIGNED_PATIENT_ID, EMPLOYEE_ID),
                exception.getMessage());

        verify(employeeService).getEmployeeByIdOrThrowException(EMPLOYEE_ID);
        verify(patientService).getPatientByIdOrThrowException(UNASSIGNED_PATIENT_ID);
    }
}
