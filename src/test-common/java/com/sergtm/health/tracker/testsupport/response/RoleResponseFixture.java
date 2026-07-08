package com.sergtm.health.tracker.testsupport.response;

import com.sergtm.health.tracker.rest.response.RoleResponse;

import static com.sergtm.health.tracker.testsupport.FixtureConstants.ADMIN_ROLE_NAME;
import static com.sergtm.health.tracker.testsupport.FixtureConstants.USER_ROLE_NAME;

public class RoleResponseFixture {
    private RoleResponseFixture() {
    }

    public static RoleResponse.RoleResponseBuilder createUserRoleResponseBuilder() {
        return RoleResponse.builder()
                .name(USER_ROLE_NAME);
    }

    public static RoleResponse.RoleResponseBuilder createAdminRoleResponseBuilder() {
        return RoleResponse.builder()
                .name(ADMIN_ROLE_NAME);
    }
}
