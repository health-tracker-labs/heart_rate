package com.sergtm.health.tracker.testsupport.response;

import com.sergtm.health.tracker.rest.response.UserResponse;

import static com.sergtm.health.tracker.testsupport.FixtureConstants.USER_NAME;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.USER_PASSWORD;
import static java.lang.Boolean.TRUE;

public final class UserResponseFixture {
    private UserResponseFixture() {
    }

    public static UserResponse.UserResponseBuilder createUserResponseBuilder() {
        return UserResponse
                .builder()
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .state(TRUE);
    }
}
