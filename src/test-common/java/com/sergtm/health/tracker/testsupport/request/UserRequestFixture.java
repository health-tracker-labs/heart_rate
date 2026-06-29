package com.sergtm.health.tracker.testsupport.request;

import com.sergtm.health.tracker.rest.request.UserRequest;

import static com.sergtm.health.tracker.testsupport.FixtureConstants.USER_NAME;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.USER_PASSWORD;
import static java.lang.Boolean.TRUE;

public final class UserRequestFixture {
    private UserRequestFixture() {
    }

    public static UserRequest.UserRequestBuilder createUserRequestBuilder() {
        return UserRequest.builder()
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .state(TRUE);
    }

}
