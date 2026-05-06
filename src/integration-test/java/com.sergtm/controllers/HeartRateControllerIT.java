package com.sergtm.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergtm.TimeOfDay;
import com.sergtm.dao.IPersonDao;
import com.sergtm.entities.HeartRate;
import com.sergtm.entities.Person;
import com.sergtm.health.tracker.persistence.repository.HeartRateRepository;
import com.sergtm.health.tracker.persistence.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HeartRateControllerIT {
    private static final String HEART_RATE_CONTROLLER_URL = "/heartRate";
    private static final String METHOD_HEART_RATE_URL_FORMAT = "%s/%s";

    private static final String ADD_HEART_RATE_URL =
            String.format(METHOD_HEART_RATE_URL_FORMAT, HEART_RATE_CONTROLLER_URL, "add.json");
    private static final String SAVE_HEART_RATE_URL =
            String.format(METHOD_HEART_RATE_URL_FORMAT, HEART_RATE_CONTROLLER_URL, "save.do");

    private static final String UPPER_PRESSURE_PARAM_NAME = "upperPressure";
    private static final String LOWER_PRESSURE_PARAM_NAME = "lowerPressure";
    private static final String BPM_PARAM_NAME = "beatsPerMinute";
    private static final String FIRST_NAME_PARAM_NAME = "firstName";
    private static final String LAST_NAME_PARAM_NAME = "secondName";

    private static final String PERSON_1_FIRST_NAME_PARAM_VALUE = "person1FN";
    private static final String PERSON_1_LAST_NAME_PARAM_VALUE = "person1LN";
    private static final String PERSON_2_FIRST_NAME_PARAM_VALUE = "person2FN";
    private static final String PERSON_2_LAST_NAME_PARAM_VALUE = "person2LN";

    private static final Integer UPPER_PRESSURE_PARAM_VALUE = 140;
    private static final Integer LOWER_PRESSURE_PARAM_VALUE = 91;
    private static final Integer BPM_PARAM_VALUE = 80;

    private static final Integer UPDATED_UPPER_PRESSURE_PARAM_VALUE = 190;
    private static final Integer UPDATED_LOWER_PRESSURE_PARAM_VALUE = 100;
    private static final Integer UPDATED_BPM_PARAM_VALUE = 87;

    private static final String HEART_RATE_ID = "id";
    private static final Long HEART_RATE_VALUE = 1L;

    private static final String TIME_OF_DAY_PARAM_NAME = "timeOfDay";
    private static final String PERSON_ID_NAME = "personId";

    private static final LocalDateTime DATE = LocalDateTime.of(2026, 3, 9, 12, 0, 0);
    private static final String DATE_NAME = "date";
    private static final String DATE_VALUE = DATE.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));

    private static final String ERROR_PAGE = "error";
    private static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "error";

    private static final String CAN_NOT_FIND_PERSON_BY_ID_MSG = "Can't find person by id = 0";
    private static final String CAN_NOT_FIND_HEART_RATE_BY_ID_MSG = "Can't find heart rate by id = %s";

    private static final String TEST_USER_NAME = "test";
    private static final String USER_AUTHORITY = "USER";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private IPersonDao personDao;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private HeartRateRepository heartRateRepository;

    @Test
    void shouldCreateHeartRateAndPersonWhenPersonWasNotFound() throws Exception {
        when(personDao.getPersonByName(PERSON_1_FIRST_NAME_PARAM_VALUE,
                PERSON_1_LAST_NAME_PARAM_VALUE)
        ).thenReturn(emptyList());

        MvcResult result = performAddHeartRateRequest(PERSON_1_FIRST_NAME_PARAM_VALUE, PERSON_1_LAST_NAME_PARAM_VALUE)
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        HeartRate heartRate = getCreatedHeartRate(jsonResponse);

        verify(personDao).savePerson(any(Person.class));
        assertThat(heartRate.getId()).isNotNull();
        assertThat(heartRate.getUpperPressure())
                .isEqualTo(UPPER_PRESSURE_PARAM_VALUE);
        assertThat(heartRate.getLowerPressure())
                .isEqualTo(LOWER_PRESSURE_PARAM_VALUE);
        assertThat(heartRate.getBeatsPerMinute())
                .isEqualTo(BPM_PARAM_VALUE);
        assertThat(heartRate.getPerson().getFirstName())
                .isEqualTo(PERSON_1_FIRST_NAME_PARAM_VALUE);
        assertThat(heartRate.getPerson().getSecondName())
                .isEqualTo(PERSON_1_LAST_NAME_PARAM_VALUE);
    }

    @Test
    void shouldCreateHeartRateWithPersonWhenPersonWasFound() throws Exception {
        Person person = Person.builder()
                .firstName(PERSON_2_FIRST_NAME_PARAM_VALUE)
                .secondName(PERSON_2_LAST_NAME_PARAM_VALUE)
                .build();
        when(personDao.getPersonByName(PERSON_2_FIRST_NAME_PARAM_VALUE,
                PERSON_2_LAST_NAME_PARAM_VALUE)
        ).thenReturn(singletonList(person));

        MvcResult result = performAddHeartRateRequest(PERSON_2_FIRST_NAME_PARAM_VALUE, PERSON_2_LAST_NAME_PARAM_VALUE)
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        HeartRate heartRate = getCreatedHeartRate(jsonResponse);

        assertThat(heartRate.getId()).isNotNull();
        assertThat(heartRate.getUpperPressure())
                .isEqualTo(UPPER_PRESSURE_PARAM_VALUE);
        assertThat(heartRate.getLowerPressure())
                .isEqualTo(LOWER_PRESSURE_PARAM_VALUE);
        assertThat(heartRate.getBeatsPerMinute())
                .isEqualTo(BPM_PARAM_VALUE);
        assertThat(heartRate.getPerson().getFirstName())
                .isEqualTo(PERSON_2_FIRST_NAME_PARAM_VALUE);
        assertThat(heartRate.getPerson().getSecondName())
                .isEqualTo(PERSON_2_LAST_NAME_PARAM_VALUE);
    }

    @Test
    void shouldThrowPersonNotFoundExceptionWhenPersonIdParamWasNotPass() throws Exception {
        HeartRateRequest request = createDefaultHeartRateRequestBuilder().build();
        performSaveHeartRate(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_PAGE))
                .andExpect(model().attribute(ERROR_MESSAGE_ATTRIBUTE_NAME,
                        CAN_NOT_FIND_PERSON_BY_ID_MSG));
    }

    @Test
    void shouldThrowHeartRateNotFoundExceptionWhenHeartRateWasNotFound() throws Exception {
        Person person = personRepository.save(new Person());

        HeartRateRequest request = createDefaultHeartRateRequestBuilder()
                .id(HEART_RATE_VALUE)
                .personId(person.getId())
                .build();
        performSaveHeartRate(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_PAGE))
                .andExpect(model().attribute(ERROR_MESSAGE_ATTRIBUTE_NAME,
                        String.format(CAN_NOT_FIND_HEART_RATE_BY_ID_MSG, HEART_RATE_VALUE)));
    }

    @Test
    void shouldUpdateExistingHeartRateWhenUserPostsFormWithPressureData() throws Exception {
        Person person = createDefaultPerson();
        personRepository.save(person);

        HeartRate heartRate = createDefaultHeartRate(person);
        heartRateRepository.save(heartRate);

        HeartRateRequest request = createDefaultHeartRateRequestBuilder()
                .id(heartRate.getId())
                .personId(person.getId())
                .upperPressure(UPDATED_UPPER_PRESSURE_PARAM_VALUE)
                .lowerPressure(UPDATED_LOWER_PRESSURE_PARAM_VALUE)
                .bpm(UPDATED_BPM_PARAM_VALUE)
                .build();
        performSaveHeartRate(request)
                .andDo(print())
                .andExpect(status().isOk());

        HeartRate updated = heartRateRepository
                .findById(heartRate.getId())
                .orElseThrow();

        Instant dtInstant = getZonedDateTimeInUTC(DATE, TimeOfDay.DAY)
                .toInstant();

        assertThat(updated.getId())
                .isEqualTo(heartRate.getId());
        assertThat(updated.getUpperPressure())
                .isEqualTo(UPDATED_UPPER_PRESSURE_PARAM_VALUE);
        assertThat(updated.getLowerPressure())
                .isEqualTo(UPDATED_LOWER_PRESSURE_PARAM_VALUE);
        assertThat(updated.getBeatsPerMinute())
                .isEqualTo(UPDATED_BPM_PARAM_VALUE);
        assertThat(updated.getDate())
                .isEqualTo(Date.from(dtInstant));
        assertThat(updated.getPerson().getFirstName())
                .isEqualTo(PERSON_1_FIRST_NAME_PARAM_VALUE);
        assertThat(updated.getPerson().getSecondName())
                .isEqualTo(PERSON_1_LAST_NAME_PARAM_VALUE);
    }

    @Test
    void shouldCreateNewHeartRateWhenUserPostsFormWithPressureData() throws Exception {
        Person person = createDefaultPerson();
        personRepository.save(person);

        HeartRateRequest request = createDefaultHeartRateRequestBuilder()
                .personId(person.getId())
                .timeOfDay(TimeOfDay.MORNING)
                .build();

        performSaveHeartRate(request)
                .andDo(print())
                .andExpect(status().isOk());

        HeartRate created = heartRateRepository
                .findByPersonId(person.getId())
                .orElseThrow();

        Instant dtInstant = getZonedDateTimeInUTC(DATE, TimeOfDay.MORNING)
                .toInstant();

        assertThat(created.getId()).isNotNull();
        assertThat(created.getUpperPressure())
                .isEqualTo(UPPER_PRESSURE_PARAM_VALUE);
        assertThat(created.getLowerPressure())
                .isEqualTo(LOWER_PRESSURE_PARAM_VALUE);
        assertThat(created.getBeatsPerMinute())
                .isEqualTo(BPM_PARAM_VALUE);
        assertThat(created.getDate())
                .isEqualTo(Date.from(dtInstant));
        assertThat(created.getPerson().getFirstName())
                .isEqualTo(PERSON_1_FIRST_NAME_PARAM_VALUE);
        assertThat(created.getPerson().getSecondName())
                .isEqualTo(PERSON_1_LAST_NAME_PARAM_VALUE);
    }

    private HeartRate getCreatedHeartRate(String jsonResponse) throws Exception {
        List<HeartRate> heartRates =
                objectMapper.readValue(jsonResponse, new TypeReference<List<HeartRate>>() {});
        return heartRates.stream().findFirst().orElseThrow();
    }

    private ResultActions performAddHeartRateRequest(String firstName, String lastName) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get(ADD_HEART_RATE_URL)
                .with(user(TEST_USER_NAME)
                        .authorities(new SimpleGrantedAuthority(USER_AUTHORITY))
                )
                .queryParam(UPPER_PRESSURE_PARAM_NAME,
                        UPPER_PRESSURE_PARAM_VALUE.toString())
                .queryParam(LOWER_PRESSURE_PARAM_NAME,
                        LOWER_PRESSURE_PARAM_VALUE.toString())
                .queryParam(BPM_PARAM_NAME,
                        BPM_PARAM_VALUE.toString())
                .queryParam(FIRST_NAME_PARAM_NAME, firstName)
                .queryParam(LAST_NAME_PARAM_NAME, lastName)
        );
    }

    private ResultActions performSaveHeartRate(HeartRateRequest request) throws Exception {
        var mockMvcRequestBuilders = MockMvcRequestBuilders
                .post(SAVE_HEART_RATE_URL)
                .with(user(TEST_USER_NAME)
                        .authorities(new SimpleGrantedAuthority(USER_AUTHORITY))
                )
                .param(UPPER_PRESSURE_PARAM_NAME,
                        request.getUpperPressure().toString())
                .param(LOWER_PRESSURE_PARAM_NAME,
                        request.getLowerPressure().toString())
                .param(BPM_PARAM_NAME,
                        request.getBpm().toString())
                .param(DATE_NAME, DATE_VALUE)
                .param(TIME_OF_DAY_PARAM_NAME,
                        request.getTimeOfDay().name());
        if (nonNull(request.getId())) {
            mockMvcRequestBuilders.param(HEART_RATE_ID, request.getId().toString());
        }
        if (nonNull(request.getPersonId())) {
            mockMvcRequestBuilders.param(PERSON_ID_NAME, request.getPersonId().toString());
        }
        return mockMvc.perform(mockMvcRequestBuilders);
    }

    private static Person createDefaultPerson() {
        return Person.builder()
                .firstName(PERSON_1_FIRST_NAME_PARAM_VALUE)
                .secondName(PERSON_1_LAST_NAME_PARAM_VALUE)
                .build();
    }

    private static HeartRate createDefaultHeartRate(Person person) {
        return HeartRate.builder()
                .person(person)
                .upperPressure(UPPER_PRESSURE_PARAM_VALUE)
                .lowerPressure(LOWER_PRESSURE_PARAM_VALUE)
                .beatsPerMinute(BPM_PARAM_VALUE)
                .build();
    }

    private static HeartRateRequest.HeartRateRequestBuilder createDefaultHeartRateRequestBuilder() {
        return HeartRateRequest.builder()
                .upperPressure(UPPER_PRESSURE_PARAM_VALUE)
                .lowerPressure(LOWER_PRESSURE_PARAM_VALUE)
                .bpm(BPM_PARAM_VALUE)
                .timeOfDay(TimeOfDay.DAY);
    }

    private static ZonedDateTime getZonedDateTimeInUTC(LocalDateTime ldt, TimeOfDay timeOfDay) {
        return ldt
                .with(timeOfDay.getLocalTime())
                .atZone(ZoneId.of("UTC"));
    }
}
