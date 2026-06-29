package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.rest.response.PersonResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_EMAIL_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_FIRST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_LAST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createFirstPersonBuilder;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createThirdPersonBuilder;
import static com.sergtm.health.tracker.testsupport.request.PersonRequestFixture.createFirstPersonRequestBuilder;
import static com.sergtm.health.tracker.testsupport.response.PersonResponseFixture.createFirstPersonResponseBuilder;
import static com.sergtm.health.tracker.testsupport.response.PersonResponseFixture.createThirdPersonResponseBuilder;
import static com.sergtm.health.tracker.testsupport.response.PersonResponseFixture.getFirstPersonFullName;
import static com.sergtm.health.tracker.testsupport.response.PersonResponseFixture.getFirstPersonShortName;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PersonMapperTest {
    private static final Long EMPLOYEE_PERSON_ID = 1L;
    private static final Long PATIENT_PERSON_ID = 2L;
    private static final String PERSON_1_MIDDLE_NAME_VALUE = "person1MN";

    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    @ParameterizedTest
    @MethodSource("namesScenarios")
    void toResponse_shouldGenerateExpectedNameInPersonResponse(
            String middleName,
            String expectedName
    ) {
        Person employee = createFirstPersonBuilder()
                .id(EMPLOYEE_PERSON_ID)
                .middleName(middleName)
                .build();
        PersonResponse response = personMapper.toResponse(employee);

        PersonResponse expectedResponse = createFirstPersonResponseBuilder()
                .id(EMPLOYEE_PERSON_ID)
                .name(expectedName)
                .build();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void toResponse_shouldMapPersonToPersonResponse() {
        Person employee = createThirdPersonBuilder()
                .id(EMPLOYEE_PERSON_ID)
                .build();
        PersonResponse response = personMapper.toResponse(employee);

        PersonResponse expectedResponse = createThirdPersonResponseBuilder()
                .id(EMPLOYEE_PERSON_ID)
                .build();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void toResponses_shouldMapPersonsToPersonResponses() {
        List<PersonResponse> responses = personMapper.toResponses(List.of(
                createThirdPersonBuilder().id(EMPLOYEE_PERSON_ID).build(),
                createFirstPersonBuilder().id(PATIENT_PERSON_ID).build()));
        List<PersonResponse> expectedResponses = List.of(
                createThirdPersonResponseBuilder()
                        .id(EMPLOYEE_PERSON_ID)
                        .build(),
                createFirstPersonResponseBuilder()
                        .id(PATIENT_PERSON_ID)
                        .build());

        assertThat(responses)
                .containsExactlyInAnyOrderElementsOf(expectedResponses);
    }

    @Test
    void toDomain_shouldMapUserRequestToUser() {
        Person person = personMapper.toDomain(createFirstPersonRequestBuilder().build());
        assertThat(person)
                .usingRecursiveComparison()
                .isEqualTo(Person.builder()
                        .firstName(PERSON_1_FIRST_NAME_VALUE)
                        .middleName(PERSON_1_MIDDLE_NAME_VALUE)
                        .secondName(PERSON_1_LAST_NAME_VALUE)
                        .email(PERSON_1_EMAIL_VALUE)
                        .build());
    }

    private static Stream<Arguments> namesScenarios() {
        return Stream.of(
                Arguments.of(PERSON_1_MIDDLE_NAME_VALUE, getFirstPersonFullName()),
                Arguments.of(EMPTY, getFirstPersonShortName())
        );
    }
}
