package com.sergtm.health.tracker.service.impl;

import com.sergtm.entities.Role;
import com.sergtm.health.tracker.persistence.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static com.sergtm.health.tracker.testsupport.entry.RoleEntryFixture.createAdminRoleBuilder;
import static com.sergtm.health.tracker.testsupport.entry.RoleEntryFixture.createUserRoleBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    private static final Long REGULAR_USER_ROLE_ID = 1L;
    private static final Long ADMIN_USER_ROLE_ID = 2L;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService testedInstance;

    @Test
    void getRoles_shouldReturnEmptyCollection_whenNoRolesWereFound() {
        doReturn(List.of())
                .when(roleRepository)
                .findAll();

        Collection<Role> actual = testedInstance.getRoles();
        assertThat(actual).isEmpty();
    }

    @Test
    void getRoles_shouldReturnRoles_whenAnyWereFound() {
        List<Role> expected = List.of(
                createUserRoleBuilder()
                        .id(REGULAR_USER_ROLE_ID)
                        .build(),
                createAdminRoleBuilder()
                        .id(ADMIN_USER_ROLE_ID)
                        .build());

        doReturn(expected)
                .when(roleRepository)
                .findAll();

        Collection<Role> actual = testedInstance.getRoles();
        assertThat(actual)
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(expected);
    }
}
