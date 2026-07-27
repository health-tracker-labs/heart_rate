package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.entities.Role;
import com.sergtm.health.tracker.rest.response.RoleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Stream;

import static com.sergtm.health.tracker.testsupport.entry.RoleEntryFixture.createAdminRoleBuilder;
import static com.sergtm.health.tracker.testsupport.entry.RoleEntryFixture.createUserRoleBuilder;
import static com.sergtm.health.tracker.testsupport.response.RoleResponseFixture.createAdminRoleResponseBuilder;
import static com.sergtm.health.tracker.testsupport.response.RoleResponseFixture.createUserRoleResponseBuilder;
import static org.assertj.core.api.Assertions.assertThat;

class RoleMapperTest {
    private static final Long REGULAR_USER_ROLE_ID = 1L;
    private static final Long ADMIN_USER_ROLE_ID = 2L;

    private static final Role REGULAR_USER_ROLE = createUserRoleBuilder()
            .id(REGULAR_USER_ROLE_ID)
            .build();
    private static final Role ADMIN_USER_ROLE = createAdminRoleBuilder()
            .id(ADMIN_USER_ROLE_ID)
            .build();

    private static final RoleResponse REGULAR_USER_ROLE_RESPONSE = createUserRoleResponseBuilder()
            .id(REGULAR_USER_ROLE_ID)
            .build();
    private static final RoleResponse ADMIN_USER_ROLE_RESPONSE = createAdminRoleResponseBuilder()
            .id(ADMIN_USER_ROLE_ID)
            .build();

    private final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @ParameterizedTest
    @MethodSource("role2ResponseScenarios")
    void toResponse_shouldMapRoleToRoleResponse(
            Role role,
            RoleResponse roleResponse
    ) {
        RoleResponse response = roleMapper.toResponse(role);
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(roleResponse);
    }

    @Test
    void toResponses_shouldMapRolesToRoleResponses() {
        Set<RoleResponse> responses = roleMapper.toResponses(Set.of(REGULAR_USER_ROLE, ADMIN_USER_ROLE));
        Set<RoleResponse> expectedResponses = Set.of(ADMIN_USER_ROLE_RESPONSE, REGULAR_USER_ROLE_RESPONSE);

        assertThat(responses)
                .containsExactlyInAnyOrderElementsOf(expectedResponses);
    }

    private static Stream<Arguments> role2ResponseScenarios() {
        return Stream.of(
                Arguments.of(REGULAR_USER_ROLE, REGULAR_USER_ROLE_RESPONSE),
                Arguments.of(ADMIN_USER_ROLE, ADMIN_USER_ROLE_RESPONSE)
        );
    }
}
