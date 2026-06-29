package com.sergtm.health.tracker.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.repository.EmployeeRepository;
import com.sergtm.health.tracker.persistence.repository.PatientRepository;
import com.sergtm.health.tracker.rest.response.EmployeeResponse;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.sergtm.health.tracker.testsupport.entry.EmployeeEntryFixture.createEmployee;
import static com.sergtm.health.tracker.testsupport.entry.PatientEntryFixture.createPatient;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createFirstPerson;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createSecondPerson;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createThirdPerson;
import static java.lang.Integer.MAX_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class EmployeeControllerIT extends AbstractRestControllerIT {
    private static final String EMPLOYEES_URL = "/employees";
    private static final String PATIENT_TO_EMPLOYEE_URL = "/employees/{employeeId}/patients/{patientId}";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    void get_shouldReturnAllEmployees(
            @Value("classpath:employee/responses/getEmployees.json")
            Resource response
    ) throws Exception {
        Set<Patient> firstEmployeePatients = Set.of(createPatient(createSecondPerson()),
                createPatient(createThirdPerson()));
        Set<Patient> secondEmployeePatients = Set.of(createPatient(createFirstPerson()),
                createPatient(createThirdPerson()));

        Set<Employee> employees = Set.of(createEmployee(createFirstPerson(), firstEmployeePatients),
                createEmployee(createSecondPerson(), secondEmployeePatients));
        employeeRepository.saveAll(employees);

        String responsesJson = mockMvc.perform(MockMvcRequestBuilders
                        .get(EMPLOYEES_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<EmployeeResponse> responses = objectMapper.readValue(responsesJson, new TypeReference<>() {});
        List<EmployeeResponse> actual = responses.stream()
                .filter(resp -> employees.stream()
                        .anyMatch(p -> p.getId().equals(resp.getId())))
                .toList();

        JSONAssert.assertEquals(
                loadJson(response),
                writeValueAsString(actual),
                JSONCompareMode.LENIENT);
    }

    @Test
    void put_shouldReturnNotFound_whenEmployeeDoesNotExist() throws Exception {
        long employeeId = ThreadLocalRandom.current().nextLong(0, MAX_VALUE);
        long personId = ThreadLocalRandom.current().nextLong(0, MAX_VALUE);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(PATIENT_TO_EMPLOYEE_URL, employeeId, personId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void put_shouldReturnNotFound_whenPatientDoesNotExist() throws Exception {
        Employee employee = createEmployee(createFirstPerson(), Set.of());
        employeeRepository.save(employee);

        long personId = ThreadLocalRandom.current().nextLong(0, MAX_VALUE);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(PATIENT_TO_EMPLOYEE_URL, employee.getId(), personId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void put_shouldReturnEmployeeWithAssignedPatient(
            @Value("classpath:employee/responses/putEmployeeWithAssignedPatient.json")
            Resource response
    ) throws Exception {
        Patient patient = patientRepository.save(createPatient(createSecondPerson()));
        Employee employee = createEmployee(createFirstPerson(), new HashSet<>());

        employeeRepository.save(employee);

        String responsesJson = mockMvc.perform(MockMvcRequestBuilders
                        .put(PATIENT_TO_EMPLOYEE_URL, employee.getId(), patient.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(
                loadJson(response),
                responsesJson,
                JSONCompareMode.LENIENT);
    }

    @Test
    void delete_shouldReturnNotFound_whenEmployeeDoesNotExist() throws Exception {
        long employeeId = ThreadLocalRandom.current().nextLong(0, MAX_VALUE);
        long personId = ThreadLocalRandom.current().nextLong(0, MAX_VALUE);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(PATIENT_TO_EMPLOYEE_URL, employeeId, personId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldReturnNotFound_whenPatientDoesNotExist() throws Exception {
        Employee employee = createEmployee(createFirstPerson(), Set.of());
        employeeRepository.save(employee);

        long personId = ThreadLocalRandom.current().nextLong(0, MAX_VALUE);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(PATIENT_TO_EMPLOYEE_URL, employee.getId(), personId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldReturnEmployeeWithPatientRemoved(
            @Value("classpath:employee/responses/deleteEmployeeWithPatientRemoved.json")
            Resource response
    ) throws Exception {
        Patient patient = patientRepository.save(createPatient(createSecondPerson()));
        Employee employee = createEmployee(createFirstPerson(), new HashSet<>(Set.of(patient)));

        employeeRepository.save(employee);

        String responsesJson = mockMvc.perform(MockMvcRequestBuilders
                        .delete(PATIENT_TO_EMPLOYEE_URL, employee.getId(), patient.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(
                loadJson(response),
                responsesJson,
                JSONCompareMode.LENIENT);
    }

}
