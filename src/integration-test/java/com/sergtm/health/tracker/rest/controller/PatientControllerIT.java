package com.sergtm.health.tracker.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.persistence.repository.EmployeeRepository;
import com.sergtm.health.tracker.persistence.repository.PatientRepository;
import com.sergtm.health.tracker.persistence.repository.PersonRepository;
import com.sergtm.health.tracker.rest.request.PatientRequest;
import com.sergtm.health.tracker.rest.response.PatientResponse;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
class PatientControllerIT extends AbstractRestControllerIT {
    private static final String PATIENTS_URL = "/patients";

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    void get_shouldReturnAllPatients(
            @Value("classpath:patient/responses/getPatients.json")
            Resource response
    ) throws Exception {
        Set<Patient> patients = Set.of(createPatient(createFirstPerson()),
                createPatient(createSecondPerson()));
        patientRepository.saveAll(patients);

        Employee employee = createEmployee(createThirdPerson(), patients);
        employeeRepository.save(employee);

        User user = userRepository.findOneByUsername(USER_NAME).orElseThrow();
        user.setEmployee(employee);

        String responsesJson = mockMvc.perform(MockMvcRequestBuilders
                        .get(PATIENTS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PatientResponse> responses = objectMapper.readValue(responsesJson, new TypeReference<>() {});
        List<PatientResponse> actual = responses.stream()
                .filter(resp -> patients.stream()
                        .anyMatch(p -> p.getId().equals(resp.getId())))
                .toList();

        JSONAssert.assertEquals(
                loadJson(response),
                writeValueAsString(actual),
                JSONCompareMode.LENIENT);
    }

    @Test
    void post_shouldCreatePatient(
            @Value("classpath:patient/responses/postPatients.json")
            Resource response
    ) throws Exception {
        Person person = personRepository.save(createFirstPerson());

        Employee employee = createEmployee(createThirdPerson(), Set.of());
        employeeRepository.save(employee);

        User user = userRepository.findOneByUsername(USER_NAME).orElseThrow();
        user.setEmployee(employee);

        String responsesJson = mockMvc.perform(MockMvcRequestBuilders
                        .post(PATIENTS_URL)
                        .content(writeValueAsString(PatientRequest.builder()
                                .personId(person.getId())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PatientResponse actual = objectMapper.readValue(responsesJson, new TypeReference<>() {});

        JSONAssert.assertEquals(
                loadJson(response),
                writeValueAsString(actual),
                JSONCompareMode.LENIENT);
    }

    @Test
    void post_shouldReturnNotFound_whenPersonDoesNotExist() throws Exception {
        long personId = ThreadLocalRandom.current().nextLong(0, MAX_VALUE);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(PATIENTS_URL)
                        .content(writeValueAsString(PatientRequest.builder()
                                .personId(personId)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
