package com.sergtm.health.tracker.testsupport.response;

import com.sergtm.health.tracker.rest.response.PersonResponse;

import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_COUNTRY_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_FIRST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_LAST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_MIDDLE_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_COUNTRY_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_FIRST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_LAST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_MIDDLE_NAME_VALUE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public final class PersonResponseFixture {
    private static final String PERSON_NAME_FORMAT = "%s%s, %s";

    private PersonResponseFixture() {
    }

    public static PersonResponse.PersonResponseBuilder createFirstPersonResponseBuilder() {
        return PersonResponse.builder()
                .name(getFullName(
                        PERSON_1_FIRST_NAME_VALUE,
                        PERSON_1_MIDDLE_NAME_VALUE,
                        PERSON_1_LAST_NAME_VALUE))
                .country(PERSON_1_COUNTRY_NAME_VALUE);
    }

    public static String getFirstPersonFullName() {
        return getFullName(PERSON_1_FIRST_NAME_VALUE,
                PERSON_1_MIDDLE_NAME_VALUE,
                PERSON_1_LAST_NAME_VALUE);
    }

    public static String getFirstPersonShortName() {
        return getFullName(PERSON_1_FIRST_NAME_VALUE,
                EMPTY,
                PERSON_1_LAST_NAME_VALUE);
    }

    public static PersonResponse.PersonResponseBuilder createThirdPersonResponseBuilder() {
        return PersonResponse.builder()
                .name(getFullName(
                        PERSON_3_FIRST_NAME_VALUE,
                        PERSON_3_MIDDLE_NAME_VALUE,
                        PERSON_3_LAST_NAME_VALUE))
                .country(PERSON_3_COUNTRY_NAME_VALUE);
    }

    private static String getFullName(
            String firstName,
            String middleName,
            String lastName
    ) {
        return String.format(PERSON_NAME_FORMAT,
                lastName,
                isEmpty(middleName) ? EMPTY : SPACE + middleName,
                firstName);
    }
}
