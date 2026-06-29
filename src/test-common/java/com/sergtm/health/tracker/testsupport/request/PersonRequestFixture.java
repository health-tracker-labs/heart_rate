package com.sergtm.health.tracker.testsupport.request;

import com.sergtm.health.tracker.rest.request.PersonRequest;

import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_EMAIL_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_FIRST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_LAST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_1_MIDDLE_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_EMAIL_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_FIRST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_LAST_NAME_VALUE;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.PERSON_3_MIDDLE_NAME_VALUE;

public final class PersonRequestFixture {
    private PersonRequestFixture() {
    }

    public static PersonRequest.PersonRequestBuilder createFirstPersonRequestBuilder() {
        return PersonRequest.builder()
                .firstName(PERSON_1_FIRST_NAME_VALUE)
                .middleName(PERSON_1_MIDDLE_NAME_VALUE)
                .secondName(PERSON_1_LAST_NAME_VALUE)
                .email(PERSON_1_EMAIL_VALUE);
    }

    public static PersonRequest.PersonRequestBuilder createThirdPersonRequestBuilder() {
        return PersonRequest.builder()
                .firstName(PERSON_3_FIRST_NAME_VALUE)
                .middleName(PERSON_3_MIDDLE_NAME_VALUE)
                .secondName(PERSON_3_LAST_NAME_VALUE)
                .email(PERSON_3_EMAIL_VALUE);
    }
}
