package com.sergtm.service.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.entities.Role;
import com.sergtm.health.tracker.persistence.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final Long FIRST_USER_ID = 1L;
    private static final String FIRST_USER_NAME = "user1";
    private static final String FIRST_USER_PASSWORD = "user1Pwd";

    private static final Long SECOND_USER_ID = 2L;
    private static final String SECOND_USER_NAME = "user2";
    private static final String SECOND_USER_PASSWORD = "user2Pwd";

    private static final Long USER_ROLE_ID = 1L;
    private static final String USER_ROLE_NAME = "USER";

    private static final Long ADMIN_ROLE_ID = 2L;
    private static final String ADMIN_ROLE_NAME = "ADMIN";

    @Mock
    private IUserDao userDao;

    @InjectMocks
    private UserServiceImpl testedInstance;

    @Test
    void getUsers_shouldReturnOneUser_whenOneUserExists() {
        doReturn(singleton(createRegularUser()))
                .when(userDao)
                .getAll();

        Collection<User> users = testedInstance.getUsers();
        assertRegularUser(users.stream()
                .findFirst()
                .orElseThrow());
    }

    @Test
    void getUsers_shouldReturnSeveralUsers_whenSeveralUsersExist() {
        doReturn(List.of(createRegularUser(), createAdminUser()))
                .when(userDao)
                .getAll();

        Collection<User> users = testedInstance.getUsers();

        List<User> sortedUsers = users.stream()
                .sorted(Comparator.comparing(User::getId))
                .toList();

        User regularUser = sortedUsers.get(0);
        assertRegularUser(regularUser);

        User adminUser = sortedUsers.get(1);
        assertAdminUser(adminUser);
    }

    private static void assertRegularUser(User user) {
        assertEquals(FIRST_USER_ID, user.getId());
        assertEquals(FIRST_USER_NAME, user.getUsername());
        assertEquals(FIRST_USER_PASSWORD, user.getPassword());
        assertFalse(user.getState());

        Role role = user.getRoles().stream()
                .findFirst()
                .orElseThrow();
        assertEquals(USER_ROLE_ID, role.getId());
        assertEquals(USER_ROLE_NAME, role.getName());
    }

    private static void assertAdminUser(User user) {
        assertEquals(SECOND_USER_ID, user.getId());
        assertEquals(SECOND_USER_NAME, user.getUsername());
        assertEquals(SECOND_USER_PASSWORD, user.getPassword());
        assertTrue(user.getState());

        Role role = user.getRoles().stream()
                .findFirst()
                .orElseThrow();
        assertEquals(ADMIN_ROLE_ID, role.getId());
        assertEquals(ADMIN_ROLE_NAME, role.getName());
    }

    private static User createRegularUser() {
        return createUser(
                FIRST_USER_ID,
                FIRST_USER_NAME,
                FIRST_USER_PASSWORD,
                Boolean.FALSE,
                Set.of(createRole(USER_ROLE_ID, USER_ROLE_NAME)));
    }

    private User createAdminUser() {
        return createUser(
                SECOND_USER_ID,
                SECOND_USER_NAME,
                SECOND_USER_PASSWORD,
                Boolean.TRUE,
                Set.of(createRole(ADMIN_ROLE_ID, ADMIN_ROLE_NAME)));
    }

    private static User createUser(
            Long id,
            String name,
            String password,
            Boolean isActive,
            Set<Role> roles
    ) {
        return User.builder()
                .id(id)
                .username(name)
                .password(password)
                .state(isActive)
                .roles(roles)
                .build();
    }

    private static Role createRole(Long id, String name) {
        return new Role(id, name);
    }
}
