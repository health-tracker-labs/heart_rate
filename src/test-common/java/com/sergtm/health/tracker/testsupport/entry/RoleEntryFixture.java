package com.sergtm.health.tracker.testsupport.entry;

import com.sergtm.entities.Role;

import static com.sergtm.health.tracker.testsupport.FixtureConstants.ADMIN_ROLE_NAME;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.USER_ROLE_NAME;

public class RoleEntryFixture {
    private RoleEntryFixture() {
    }

    public static Role.RoleBuilder createUserRoleBuilder() {
        return Role.builder()
                .name(USER_ROLE_NAME);
    }

    public static Role.RoleBuilder createAdminRoleBuilder() {
        return Role.builder()
                .name(ADMIN_ROLE_NAME);
    }
}
