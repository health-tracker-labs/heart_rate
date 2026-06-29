package com.sergtm.health.tracker.rest.controller.mapper;

import com.sergtm.entities.Role;
import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.rest.response.EmployeeResponse;
import com.sergtm.health.tracker.rest.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static com.sergtm.health.tracker.testsupport.entry.UserEntryFixture.createAdminRoleBuilder;
import static com.sergtm.health.tracker.testsupport.entry.UserEntryFixture.createUserBuilder;
import static com.sergtm.health.tracker.testsupport.entry.UserEntryFixture.createUserRoleBuilder;
import static com.sergtm.health.tracker.testsupport.request.UserRequestFixture.createUserRequestBuilder;
import static com.sergtm.health.tracker.testsupport.response.UserResponseFixture.createUserResponseBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    private static final Long REGULAR_USER_ROLE_ID = 1L;
    private static final Long ADMIN_USER_ROLE_ID = 2L;

    private static final Long REGULAR_USER_ID = 3L;
    private static final Long ADMIN_USER_ID = 4L;

    private static final Set<Role> REGULAR_USER_ROLES = Set.of(createUserRoleBuilder()
            .id(REGULAR_USER_ROLE_ID)
            .build());
    private static final Set<Role> ADMIN_USER_ROLES = Set.of(createAdminRoleBuilder()
            .id(ADMIN_USER_ROLE_ID)
            .build());

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private UserMapperImpl userMapper;

    @Mock
    private Employee employee;
    @Mock
    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp() {
        // EmployeeMapper is tested separately
        lenient().doReturn(employeeResponse)
                .when(employeeMapper)
                .toResponse(any(Employee.class));
    }

    @Test
    void toResponse_shouldMapUserToUserResponse() {
        User regularUser = createUserBuilder()
                .id(REGULAR_USER_ID)
                .employee(employee)
                .roles(REGULAR_USER_ROLES)
                .build();

        UserResponse response = userMapper.toResponse(regularUser);
        UserResponse expectedResponse = createUserResponseBuilder()
                .id(REGULAR_USER_ID)
                .employee(employeeResponse)
                .roles(REGULAR_USER_ROLES)
                .build();

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void toResponses_shouldMapUsersToUserResponses() {
        List<UserResponse> responses = userMapper.toResponses(List.of(
                createUserBuilder()
                        .id(REGULAR_USER_ID)
                        .employee(employee)
                        .roles(REGULAR_USER_ROLES)
                        .build(),
                createUserBuilder()
                        .id(ADMIN_USER_ID)
                        .employee(employee)
                        .roles(ADMIN_USER_ROLES)
                        .build()));
        List<UserResponse> expectedResponses = List.of(
                createUserResponseBuilder()
                        .id(REGULAR_USER_ID)
                        .employee(employeeResponse)
                        .roles(REGULAR_USER_ROLES)
                        .build(),
                createUserResponseBuilder()
                        .id(ADMIN_USER_ID)
                        .employee(employeeResponse)
                        .roles(ADMIN_USER_ROLES)
                        .build());

        assertThat(responses)
                .containsExactlyInAnyOrderElementsOf(expectedResponses);
    }

    @Test
    void toDomain_shouldMapUserRequestToUser() {
        User user = userMapper.toDomain(createUserRequestBuilder().build());
        assertThat(user)
                .usingRecursiveComparison()
                .isEqualTo(createUserBuilder().build());
    }
}
