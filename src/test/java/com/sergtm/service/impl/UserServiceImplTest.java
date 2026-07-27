package com.sergtm.service.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.entities.Role;
import com.sergtm.health.tracker.exception.RoleNotFoundException;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.persistence.repository.RoleRepository;
import com.sergtm.health.tracker.rest.request.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.sergtm.health.tracker.testsupport.entry.RoleEntryFixture.createAdminRoleBuilder;
import static com.sergtm.health.tracker.testsupport.entry.RoleEntryFixture.createUserRoleBuilder;
import static com.sergtm.health.tracker.testsupport.entry.UserEntryFixture.createUserBuilder;
import static com.sergtm.health.tracker.testsupport.request.UserRequestFixture.createUserUpdateRequestBuilder;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Long.MAX_VALUE;
import static org.apache.commons.collections4.CollectionUtils.COMMA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String NO_CONFIGURED_ROLES_WERE_FOUND = "No configured roles were found";
    private static final String SOME_ROLES_WERE_NOT_FOUND_MSG = "The following roles were not found: [%s]";

    private static final Long FIRST_USER_ID = 1L;
    private static final String FIRST_USER_NAME = "user1";
    private static final String FIRST_USER_PASSWORD = "user1Pwd";

    private static final Long SECOND_USER_ID = 2L;
    private static final String SECOND_USER_NAME = "user2";
    private static final String SECOND_USER_PASSWORD = "user2Pwd";

    private static final Long USER_ROLE_ID = 3L;
    private static final String USER_ROLE_NAME = "USER";

    private static final Long ADMIN_ROLE_ID = 4L;
    private static final String ADMIN_ROLE_NAME = "ADMIN";

    private static final Role REGULAR_USER_ROLE = createUserRoleBuilder()
            .id(USER_ROLE_ID)
            .build();
    private static final Role ADMIN_USER_ROLE = createAdminRoleBuilder()
            .id(ADMIN_ROLE_ID)
            .build();

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private IUserDao userDao;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private UserServiceImpl testedInstance;

    @Test
    void getUsers_shouldReturnOneUser_whenOneUserExists() {
        doReturn(Set.of(createRegularUser()))
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

    @Test
    void createUser_shouldThrowException_whenRoleIdsAreNull() {
        assertThrows(NullPointerException.class,
                () -> testedInstance.createUser(createRegularUser(), null));
        verifyNoInteractions(userDao);
    }

    @Test
    void createUser_shouldThrowException_whenRoleIdsAreEmpty() {
        doReturn(List.of()).when(roleRepository)
                .findAllById(anySet());

        Throwable exception = assertThrows(RoleNotFoundException.class,
                () -> testedInstance.createUser(createRegularUser(), Set.of()));

        assertEquals(NO_CONFIGURED_ROLES_WERE_FOUND, exception.getMessage());
        verifyNoInteractions(userDao);
    }

    @Test
    void createUser_shouldThrowException_whenRoleIdsContainsNonExistingRoles() {
        List<Long> missingRoles = List.of(
                ThreadLocalRandom.current().nextLong(0, MAX_VALUE),
                ThreadLocalRandom.current().nextLong(0, MAX_VALUE));

        Set<Long> roleIds = new LinkedHashSet<>(missingRoles);
        roleIds.add(USER_ROLE_ID);

        doReturn(List.of(REGULAR_USER_ROLE))
                .when(roleRepository)
                .findAllById(roleIds);

        Throwable exception = assertThrows(RoleNotFoundException.class,
                () -> testedInstance.createUser(createRegularUser(), roleIds));

        assertEquals(String.format(SOME_ROLES_WERE_NOT_FOUND_MSG, missingRoles
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(COMMA))), exception.getMessage());
        verifyNoInteractions(userDao);
    }

    @Test
    void createUser_shouldCreateUser_whenUserIsValid() {
        Set<Long> roleIds = Set.of(USER_ROLE_ID);
        doReturn(List.of(REGULAR_USER_ROLE))
                .when(roleRepository)
                .findAllById(roleIds);

        testedInstance.createUser(createRegularUser(), roleIds);
        verify(userDao).save(userCaptor.capture());

        assertRegularUser(userCaptor.getValue());
    }

    @Test
    void updateUser_shouldNotUpdateUser_whenUserWasNotFound() {
        UserUpdateRequest userUpdateRequest = createUserUpdateRequestBuilder().build();

        doReturn(Optional.empty())
                .when(userDao)
                .getUserById(FIRST_USER_ID);

        testedInstance.updateUser(FIRST_USER_ID, userUpdateRequest);
        verify(userDao, never()).save(any(User.class));
    }

    @Test
    void updateUser_shouldUpdateUser_whenUserWasFound() {
        UserUpdateRequest userUpdateRequest = createUserUpdateRequestBuilder()
                .id(FIRST_USER_ID)
                .username(SECOND_USER_NAME)
                .password(SECOND_USER_PASSWORD)
                .state(TRUE)
                .build();

        doReturn(Optional.of(createRegularUser()))
                .when(userDao)
                .getUserById(FIRST_USER_ID);

        testedInstance.updateUser(FIRST_USER_ID, userUpdateRequest);
        verify(userDao).save(userCaptor.capture());

        User user = userCaptor.getValue();

        assertEquals(FIRST_USER_ID, user.getId());
        assertEquals(SECOND_USER_NAME, user.getUsername());
        assertEquals(SECOND_USER_PASSWORD, user.getPassword());
        assertTrue(user.getState());
    }

    @Test
    void updateUserState_shouldNotUpdateUserState_whenUserWasNotFound() {
        doReturn(Optional.empty())
                .when(userDao)
                .getUserById(FIRST_USER_ID);

        testedInstance.updateUserState(FIRST_USER_ID, true);
        verify(userDao, never()).save(any(User.class));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void updateUserState_shouldUpdateUserState_whenUserWasFound(boolean state) {
        doReturn(Optional.of(createRegularUser()))
                .when(userDao)
                .getUserById(FIRST_USER_ID);

        testedInstance.updateUserState(FIRST_USER_ID, state);

        verify(userDao).save(userCaptor.capture());
        User user = userCaptor.getValue();

        assertEquals(state, user.getState());
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
        return createUserBuilder()
                .id(FIRST_USER_ID)
                .username(FIRST_USER_NAME)
                .password(FIRST_USER_PASSWORD)
                .state(FALSE)
                .roles(Set.of(REGULAR_USER_ROLE))
                .build();
    }

    private User createAdminUser() {
        return createUserBuilder()
                .id(SECOND_USER_ID)
                .username(SECOND_USER_NAME)
                .password(SECOND_USER_PASSWORD)
                .state(TRUE)
                .roles(Set.of(ADMIN_USER_ROLE))
                .build();
    }
}
