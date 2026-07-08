package com.sergtm.health.tracker.testsupport.entry;

import com.sergtm.health.tracker.persistence.entity.User;

import static com.sergtm.health.tracker.testsupport.FixtureConstants.USER_NAME;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.USER_PASSWORD;
import static java.lang.Boolean.TRUE;

public final class UserEntryFixture {
    private UserEntryFixture() {
    }

    public static User.UserBuilder createUserBuilder() {
        return User.builder()
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .state(TRUE);
    }
}
