package com.sergtm.health.tracker.testsupport.entry;

import com.sergtm.health.tracker.persistence.entity.Person;

import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_COUNTRY_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_FIRST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_LAST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_MIDDLE_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_2_COUNTRY_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_2_FIRST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_2_LAST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_2_MIDDLE_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_COUNTRY_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_FIRST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_LAST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_MIDDLE_NAME_VALUE;

public final class PersonEntryFixture {
    private PersonEntryFixture() {
    }

    public static Person createFirstPerson() {
        return createFirstPersonBuilder().build();
    }

    public static Person createSecondPerson() {
        return createSecondPersonBuilder().build();
    }

    public static Person createThirdPerson() {
        return createThirdPersonBuilder().build();
    }

    public static Person.PersonBuilder createFirstPersonBuilder() {
        return Person.builder()
                .firstName(PERSON_1_FIRST_NAME_VALUE)
                .middleName(PERSON_1_MIDDLE_NAME_VALUE)
                .secondName(PERSON_1_LAST_NAME_VALUE)
                .country(PERSON_1_COUNTRY_NAME_VALUE);
    }

    public static Person.PersonBuilder createSecondPersonBuilder() {
        return Person.builder()
                .firstName(PERSON_2_FIRST_NAME_VALUE)
                .middleName(PERSON_2_MIDDLE_NAME_VALUE)
                .secondName(PERSON_2_LAST_NAME_VALUE)
                .country(PERSON_2_COUNTRY_NAME_VALUE);
    }

    public static Person.PersonBuilder createThirdPersonBuilder() {
        return Person.builder()
                .firstName(PERSON_3_FIRST_NAME_VALUE)
                .middleName(PERSON_3_MIDDLE_NAME_VALUE)
                .secondName(PERSON_3_LAST_NAME_VALUE)
                .country(PERSON_3_COUNTRY_NAME_VALUE);
    }
}
