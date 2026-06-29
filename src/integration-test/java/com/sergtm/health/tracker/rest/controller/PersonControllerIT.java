package com.sergtm.health.tracker.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.persistence.repository.EmployeeRepository;
import com.sergtm.health.tracker.persistence.repository.PatientRepository;
import com.sergtm.health.tracker.persistence.repository.PersonRepository;
import com.sergtm.health.tracker.rest.request.PersonRequest;
import com.sergtm.health.tracker.rest.response.PersonResponse;
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
import static com.sergtm.health.tracker.testsupport.request.PersonRequestFixture.createThirdPersonRequestBuilder;
import static java.lang.Integer.MAX_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PersonControllerIT extends AbstractRestControllerIT {
    private static final String PERSONS_URL = "/persons";
    private static final String DELETE_PERSON_URL = "/persons/delete/{personId}";
    private static final String GET_PERSONS_BY_USER_NAME_URL = "/persons/{userName}";

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    void get_shouldReturnAllPersons(
            @Value("classpath:person/responses/getPersons.json")
            Resource response
    ) throws Exception {
        Person firstPerson = personRepository.save(createFirstPerson());
        Person secondPerson = personRepository.save(createSecondPerson());

        Set<Person> persons = Set.of(firstPerson, secondPerson);

        String actualJson = mockMvc.perform(MockMvcRequestBuilders
                        .get(PERSONS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PersonResponse> responses = objectMapper.readValue(actualJson, new TypeReference<>() {});
        List<PersonResponse> actual = responses.stream()
                .filter(resp -> persons.stream()
                        .anyMatch(p -> p.getId().equals(resp.getId())))
                .toList();

        JSONAssert.assertEquals(
                loadJson(response),
                writeValueAsString(actual),
                JSONCompareMode.LENIENT);
    }

    @Test
    void get_shouldReturnPersonsAssignedToUser(
            @Value("classpath:person/responses/getPersonsAssignedToUser.json")
            Resource response
    ) throws Exception {
        Set<Patient> patients = Set.of(createPatient(createSecondPerson()),
                createPatient(createThirdPerson()));
        patientRepository.saveAll(patients);

        Employee employee = createEmployee(createFirstPerson(), patients);
        employeeRepository.save(employee);

        User user = userRepository.findOneByUsername(USER_NAME).orElseThrow();
        user.setEmployee(employee);

        String responsesJson = mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_PERSONS_BY_USER_NAME_URL, USER_NAME)
                        .accept(APPLICATION_JSON)
                )
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
    void post_shouldCreatePerson(
            @Value("classpath:person/responses/postPersons.json")
            Resource response) throws Exception {
        PersonRequest request = createThirdPersonRequestBuilder()
                .build();

        String responsesJson = mockMvc.perform(MockMvcRequestBuilders
                        .post(PERSONS_URL)
                        .queryParams(convertRequestToMultiValueMap(request))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PersonResponse actual = objectMapper.readValue(responsesJson, new TypeReference<>() {});

        JSONAssert.assertEquals(
                loadJson(response),
                writeValueAsString(actual),
                JSONCompareMode.LENIENT);
    }

    @Test
    void delete_shouldReturnNoContent_whenPersonExists() throws Exception {
        Person person = personRepository.save(createFirstPerson());
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PERSON_URL, person.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_shouldReturnNotFound_whenPersonDoesNotExist() throws Exception {
        int personId = ThreadLocalRandom.current().nextInt(0, MAX_VALUE);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PERSON_URL, personId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}