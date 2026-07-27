package com.sergtm.health.tracker.testsupport.request;

import com.sergtm.health.tracker.rest.request.UserCreationRequest;
import com.sergtm.health.tracker.rest.request.UserUpdateRequest;

import static com.sergtm.health.tracker.testsupport.FixtureConstants.USER_NAME;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.USER_PASSWORD;
import static java.lang.Boolean.TRUE;

public final class UserRequestFixture {
    private UserRequestFixture() {
    }

    public static UserCreationRequest.UserCreationRequestBuilder createUserCreationRequestBuilder() {
        return UserCreationRequest.builder()
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .state(TRUE);
    }

    public static UserUpdateRequest.UserUpdateRequestBuilder createUserUpdateRequestBuilder() {
        return UserUpdateRequest.builder()
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .state(TRUE);
    }
}
