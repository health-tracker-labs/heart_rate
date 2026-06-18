package com.sergtm.health.tracker.rest.controller;

import com.sergtm.entities.Role;
import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.persistence.repository.EmployeeRepository;
import com.sergtm.health.tracker.persistence.repository.HeartRateRepository;
import com.sergtm.health.tracker.persistence.repository.OccasionRepository;
import com.sergtm.health.tracker.persistence.repository.PatientRepository;
import com.sergtm.health.tracker.persistence.repository.PersonRepository;
import com.sergtm.health.tracker.persistence.repository.RoleRepository;
import com.sergtm.health.tracker.persistence.repository.StaffMemberRepository;
import com.sergtm.health.tracker.persistence.repository.UserRepository;
import com.sergtm.health.tracker.persistence.repository.WeightRepository;
import com.sergtm.health.tracker.rest.request.PersonRequest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.MAX_VALUE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class PersonControllerIT extends AbstractRestControllerIT {
    private static final String USER_NAME = "admin";
    private static final String USER_PASSWORD = "admin";
    private static final String ADMIN_ROLE_NAME = "ADMIN";

    private static final String HEALTH_TRACKER_API_BASE_URL = "health-tracker.api.base-url";

    private static final String GET_ALL_PERSONS_URL = "/persons";
    private static final String CREATE_PERSON_URL = "/persons/create";
    private static final String DELETE_PERSON_URL = "/persons/delete/%s";
    private static final String GET_PERSONS_BY_USER_NAME_URL = String.format("/persons/%s", USER_NAME);

    private static final String PERSON_1_FIRST_NAME_VALUE = "person1FN";
    private static final String PERSON_1_LAST_NAME_VALUE = "person1LN";
    private static final String PERSON_1_MIDDLE_NAME_VALUE = "person1MN";
    private static final String PERSON_1_COUNTRY_NAME_VALUE = "Ukraine";
    private static final String PERSON_2_FIRST_NAME_VALUE = "person2FN";
    private static final String PERSON_2_MIDDLE_NAME_VALUE = "person2MN";
    private static final String PERSON_2_LAST_NAME_VALUE = "person2LN";
    private static final String PERSON_2_COUNTRY_NAME_VALUE = "Afghanistan";
    private static final String PERSON_3_FIRST_NAME_VALUE = "person3FN";
    private static final String PERSON_3_MIDDLE_NAME_VALUE = "person3MN";
    private static final String PERSON_3_LAST_NAME_VALUE = "person3LN";
    private static final String PERSON_3_COUNTRY_NAME_VALUE = "Ukraine";

    @Autowired
    private HeartRateRepository heartRateRepository;
    @Autowired
    private StaffMemberRepository staffMemberRepository;
    @Autowired
    private OccasionRepository occasionRepository;
    @Autowired
    private WeightRepository weightRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public static MockWebServer mockWebServer;

    @BeforeAll
    static void start() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void shutdown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void setUp() {
        heartRateRepository.deleteAll();
        staffMemberRepository.deleteAll();
        occasionRepository.deleteAll();
        weightRepository.deleteAll();

        patientRepository.deleteAll();
        employeeRepository.deleteAll();
        personRepository.deleteAll();

        userRepository.deleteAll();
        roleRepository.deleteAll();

        createDefaultAdminUser();
    }

    @Test
    void getAllPersons_shouldReturnAllPersons(
            @Value("classpath:person/responses/getPersons.json")
            Resource response
    ) throws Exception {
        personRepository.save(Person.builder()
                .firstName(PERSON_1_FIRST_NAME_VALUE)
                .middleName(PERSON_1_MIDDLE_NAME_VALUE)
                .secondName(PERSON_1_LAST_NAME_VALUE)
                .country(PERSON_1_COUNTRY_NAME_VALUE)
                .build());
        personRepository.save(Person.builder()
                .firstName(PERSON_2_FIRST_NAME_VALUE)
                .middleName(PERSON_2_MIDDLE_NAME_VALUE)
                .secondName(PERSON_2_LAST_NAME_VALUE)
                .country(PERSON_2_COUNTRY_NAME_VALUE)
                .build());

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(loadJson(response)));

        String actualJson = mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_ALL_PERSONS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(
                loadJson(response),
                actualJson,
                JSONCompareMode.LENIENT);
    }

    @Test
    void getPersonsByUserName_shouldReturnPersonsByUserName(
            @Value("classpath:person/responses/getPersonsByUserName.json")
            Resource response
    ) throws Exception {
        Patient patient1 = Patient.builder()
                .person(
                        Person.builder()
                                .firstName(PERSON_2_FIRST_NAME_VALUE)
                                .middleName(PERSON_2_MIDDLE_NAME_VALUE)
                                .secondName(PERSON_2_LAST_NAME_VALUE)
                                .country(PERSON_2_COUNTRY_NAME_VALUE)
                                .build()
                ).build();
        Patient patient2 = Patient.builder()
                .person(Person.builder()
                        .firstName(PERSON_3_FIRST_NAME_VALUE)
                        .middleName(PERSON_3_MIDDLE_NAME_VALUE)
                        .secondName(PERSON_3_LAST_NAME_VALUE)
                        .country(PERSON_3_COUNTRY_NAME_VALUE)
                        .build()
                ).build();
        Employee employee = Employee.builder()
                .patients(Set.of(patient1, patient2))
                .person(Person.builder()
                        .firstName(PERSON_1_FIRST_NAME_VALUE)
                        .middleName(PERSON_1_MIDDLE_NAME_VALUE)
                        .secondName(PERSON_1_LAST_NAME_VALUE)
                        .country(PERSON_1_COUNTRY_NAME_VALUE)
                        .build()
                ).build();
        employeeRepository.save(employee);

        User user = userRepository.findOneByUsername(USER_NAME).get();
        user.setEmployee(employee);

        String actualJson = mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_PERSONS_BY_USER_NAME_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(
                loadJson(response),
                actualJson,
                JSONCompareMode.LENIENT);
    }

    @Test
    void createPerson_shouldReturnCreatedPerson() throws Exception {
        PersonRequest request = PersonRequest.builder()
                .firstName(PERSON_1_FIRST_NAME_VALUE)
                .secondName(PERSON_1_LAST_NAME_VALUE)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(CREATE_PERSON_URL)
                        .queryParams(convertRequestToMultiValueMap(request))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void deletePerson_shouldReturnSuccessResponse_whenPersonWasFound() throws Exception {
        Person person = personRepository.save(Person.builder()
                .firstName(PERSON_1_FIRST_NAME_VALUE)
                .middleName(PERSON_1_MIDDLE_NAME_VALUE)
                .secondName(PERSON_1_LAST_NAME_VALUE)
                .country(PERSON_1_COUNTRY_NAME_VALUE)
                .build());

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(DELETE_PERSON_URL, person.getId())))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePerson_shouldReturnNotFoundResponse_whenPersonWasNotFound() throws Exception {
        int personId = ThreadLocalRandom.current().nextInt(0, MAX_VALUE);
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format(DELETE_PERSON_URL, personId)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private User createDefaultAdminUser() {
        Role adminRole = roleRepository.save(Role.builder()
                .name(ADMIN_ROLE_NAME)
                .build());
        return userRepository.save(User.builder()
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .roles(Set.of(adminRole))
                .state(Boolean.TRUE)
                .build());
    }

    private static String loadJson(Resource response) throws IOException {
        return new String(
                response.getInputStream().readAllBytes(),
                UTF_8);
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add(
                HEALTH_TRACKER_API_BASE_URL,
                () -> mockWebServer.url("/").toString()
        );
    }
}