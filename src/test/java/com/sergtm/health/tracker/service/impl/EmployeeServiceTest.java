package com.sergtm.health.tracker.service.impl;

import com.sergtm.health.tracker.exception.EmployeeNotFoundException;
import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.sergtm.health.tracker.testsupport.entry.EmployeeEntryFixture.createEmployee;
import static com.sergtm.health.tracker.testsupport.entry.PatientEntryFixture.createPatient;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createFirstPerson;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createFirstPersonBuilder;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createSecondPerson;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createSecondPersonBuilder;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createThirdPerson;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createThirdPersonBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    private static final String CAN_NOT_FIND_EMPLOYEE_BY_EMPLOYEE_ID_MSG = "Can't find employee by employee id = %s";

    private static final Long EMPLOYEE_ID = 1L;
    private static final Long FIRST_PATIENT_ID = 2L;
    private static final Long SECOND_PATIENT_ID = 3L;

    @Mock
    private EmployeeRepository employeeRepository;
    @Captor
    private ArgumentCaptor<Employee> employeeEntityCaptor;

    @InjectMocks
    private EmployeeService testedInstance;

    @Test
    void getEmployees_shouldReturnEmptyCollection_whenNoEmployeesWereFound() {
        doReturn(List.of())
                .when(employeeRepository)
                .findAll();

        Collection<Employee> actual = testedInstance.getEmployees();
        assertThat(actual).isEmpty();
    }

    @Test
    void getEmployees_shouldReturnEmployees_whenAnyWereFound() {
        Set<Patient> firstEmployeePatients = Set.of(
                createPatient(createFirstPerson()),
                createPatient(createSecondPerson()));
        Employee firstEmployee = createEmployee(createThirdPerson(), firstEmployeePatients);

        Set<Patient> secondEmployeePatients = Set.of(
                createPatient(createSecondPerson()),
                createPatient(createThirdPerson()));
        Employee secondEmployee = createEmployee(createFirstPerson(), secondEmployeePatients);

        List<Employee> expected = List.of(firstEmployee, secondEmployee);

        doReturn(expected)
                .when(employeeRepository)
                .findAll();

        Collection<Employee> actual = testedInstance.getEmployees();
        assertThat(actual)
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void saveEmployee_shouldThrowException_whenEmployeeIsNull() {
        assertThrows(NullPointerException.class, () -> testedInstance.saveEmployee(null));
    }

    @Test
    void saveEmployee_shouldReturnEmployee_whenEmployeeIsNotNull() {
        Person employeePerson = createThirdPersonBuilder()
                .id(EMPLOYEE_ID)
                .build();
        Set<Patient> patients = Set.of(
                createPatient(createFirstPersonBuilder()
                        .id(FIRST_PATIENT_ID)
                        .build()),
                createPatient(createSecondPersonBuilder()
                        .id(SECOND_PATIENT_ID)
                        .build()));
        Employee employee = createEmployee(employeePerson, patients);

        doAnswer(invocation -> invocation.getArgument(0))
                .when(employeeRepository)
                .save(any(Employee.class));

        Employee actual = testedInstance.saveEmployee(employee);

        verify(employeeRepository).save(employeeEntityCaptor.capture());

        Employee captured = employeeEntityCaptor.getValue();

        assertThat(actual.getPatients())
                .hasSize(2)
                .usingRecursiveComparison()
                .isEqualTo(captured.getPatients());

        assertThat(actual.getPerson())
                .usingRecursiveComparison()
                .isEqualTo(captured.getPerson());
    }

    @Test
    void getEmployeeByIdOrThrowException_shouldReturnEmployee_whenEmployeeWasFound() {
        Set<Patient> patients = Set.of(
                createPatient(createFirstPerson()),
                createPatient(createSecondPerson()));
        Employee employee = createEmployee(createThirdPerson(), patients);

        doReturn(Optional.of(employee))
                .when(employeeRepository)
                .findById(EMPLOYEE_ID);

        Employee actual = testedInstance.getEmployeeByIdOrThrowException(EMPLOYEE_ID);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(employee);
        verify(employeeRepository).findById(EMPLOYEE_ID);
    }

    @Test
    void getEmployeeByIdOrThrowException_shouldThrowException_whenEmployeeWasNotFound() {
        doReturn(Optional.empty())
                .when(employeeRepository)
                .findById(EMPLOYEE_ID);

        Throwable exception = assertThrows(EmployeeNotFoundException.class,
                () -> testedInstance.getEmployeeByIdOrThrowException(EMPLOYEE_ID));

        assertEquals(String.format(CAN_NOT_FIND_EMPLOYEE_BY_EMPLOYEE_ID_MSG, EMPLOYEE_ID),
                exception.getMessage());
    }
}
