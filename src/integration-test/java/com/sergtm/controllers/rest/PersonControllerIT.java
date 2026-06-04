package com.sergtm.controllers.rest;

import com.sergtm.controllers.rest.request.PersonRequest;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.repository.HeartRateRepository;
import com.sergtm.health.tracker.persistence.repository.OccasionRepository;
import com.sergtm.health.tracker.persistence.repository.PersonRepository;
import com.sergtm.health.tracker.persistence.repository.StaffMemberRepository;
import com.sergtm.health.tracker.persistence.repository.WeightRepository;
import com.sergtm.service.IHeartRateService;
import com.sergtm.service.IOccasionService;
import com.sergtm.service.IStaffMemberService;
import com.sergtm.service.IWeightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class PersonControllerIT extends AbstractRestControllerIT {
    private static final String GET_ALL_PERSONS_URL = "/persons";
    private static final String CREATE_PERSON_URL = "/persons/create";
    private static final Long PERSON_ID = 1L;
    private static final String USER_NAME = "admin";
    private static final String DELETE_PERSON_URL = String.format("/persons/delete/%s", PERSON_ID);
    private static final String GET_PERSONS_BY_USER_NAME_URL = String.format("/persons/%s", USER_NAME);

    private static final String PERSON_1_FIRST_NAME_VALUE = "person1FN";
    private static final String PERSON_1_LAST_NAME_VALUE = "person1LN";
    private static final String PERSON_1_MIDDLE_NAME_VALUE = "person1MN";
    private static final String PERSON_1_COUNTRY_NAME_VALUE = "Ukraine";
    private static final String PERSON_2_FIRST_NAME_VALUE = "person2FN";
    private static final String PERSON_2_MIDDLE_NAME_VALUE = "person2MN";
    private static final String PERSON_2_LAST_NAME_VALUE = "person2LN";
    private static final String PERSON_2_COUNTRY_NAME_VALUE = "Afghanistan";

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

    @MockBean
    private IStaffMemberService staffMemberService;
    @MockBean
    private IWeightService weightService;
    @MockBean
    private IHeartRateService heartRateService;
    @MockBean
    private IOccasionService occasionService;

    @Mock
    private Person person;

    @BeforeEach
    void setUp() {
        heartRateRepository.deleteAll();
        staffMemberRepository.deleteAll();
        occasionRepository.deleteAll();
        weightRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    void shouldReturnAllPersons(
            @Value("classpath:person/responses/getPersons.json")
            Resource personsResponse
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
                loadJson(personsResponse),
                actualJson,
                JSONCompareMode.LENIENT);
    }

    @Test
    void shouldReturnPersonsByUserName() throws Exception {
        personRepository.save(Person.builder()
                .firstName(PERSON_1_FIRST_NAME_VALUE)
                .middleName(PERSON_1_MIDDLE_NAME_VALUE)
                .secondName(PERSON_1_LAST_NAME_VALUE)
                .country(PERSON_1_COUNTRY_NAME_VALUE)
                .build());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_PERSONS_BY_USER_NAME_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreatePerson() throws Exception {
        PersonRequest request = PersonRequest.builder()
                .firstName(PERSON_1_FIRST_NAME_VALUE)
                .secondName(PERSON_1_LAST_NAME_VALUE)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(CREATE_PERSON_URL)
                        .queryParams(convertRequestToMultiValueMap(request))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeletePerson() throws Exception {
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.of(person));
        when(person.getId()).thenReturn(PERSON_ID);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(DELETE_PERSON_URL)
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(personRepository).deleteByPerson(PERSON_ID);
        verify(staffMemberService).deleteByPerson(person);
        verify(weightService).deleteByPerson(person);
        verify(heartRateService).deleteByPerson(person);
        verify(occasionService).deleteByPerson(person);
    }

    private String loadJson(Resource response) throws IOException {
        return new String(
                response.getInputStream().readAllBytes(),
                UTF_8);
    }
}