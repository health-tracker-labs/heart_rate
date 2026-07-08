package com.sergtm.health.tracker.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sergtm.entities.Role;
import com.sergtm.health.tracker.persistence.repository.RoleRepository;
import com.sergtm.health.tracker.rest.request.UserRequest;
import com.sergtm.health.tracker.rest.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.sergtm.health.tracker.testsupport.entry.RoleEntryFixture.createAdminRoleBuilder;
import static com.sergtm.health.tracker.testsupport.entry.RoleEntryFixture.createUserRoleBuilder;
import static java.lang.Boolean.TRUE;
import static java.lang.Long.MAX_VALUE;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerIT extends AbstractRestControllerIT {
    private static final String USERS_URL = "/users";

    private static final String NEW_USER_NAME = "newUser";
    private static final String NEW_USER_PASSWORD = "newPassword";

    @Autowired
    private RoleRepository roleRepository;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {SPACE})
    void post_shouldReturnBadRequest_whenUsernameIsInvalid(String userName) throws Exception {
        Role regularUserRole = roleRepository.save(createUserRoleBuilder().build());

        UserRequest request = UserRequest.builder()
                .username(userName)
                .password(NEW_USER_PASSWORD)
                .state(true)
                .roleIds(Set.of(
                        regularUserRole.getId()
                ))
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(USERS_URL)
                        .content(writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {SPACE})
    void post_shouldReturnBadRequest_whenPasswordIsInvalid(String password) throws Exception {
        Role regularUserRole = roleRepository.save(createUserRoleBuilder().build());

        UserRequest request = UserRequest.builder()
                .username(NEW_USER_NAME)
                .password(password)
                .state(true)
                .roleIds(Set.of(
                        regularUserRole.getId()
                ))
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(USERS_URL)
                        .content(writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void post_shouldReturnBadRequest_whenRoleIdsAreInvalid(Set<Long> roleIds) throws Exception {
        UserRequest request = UserRequest.builder()
                .username(NEW_USER_NAME)
                .password(NEW_USER_PASSWORD)
                .state(TRUE)
                .roleIds(roleIds)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(USERS_URL)
                        .content(writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_shouldReturnNotFound_whenRoleIdsContainsNonExistingRoles(
            @Value("classpath:user/responses/postUserWithNonExistingRoles404.json")
            Resource response) throws Exception {
        Role regularUserRole = roleRepository.save(createUserRoleBuilder().build());
        Role adminUserRole = roleRepository.save(createAdminRoleBuilder().build());

        Long missingRoleId = ThreadLocalRandom.current().nextLong(0, MAX_VALUE);

        UserRequest request = UserRequest.builder()
                .username(NEW_USER_NAME)
                .password(NEW_USER_PASSWORD)
                .state(TRUE)
                .roleIds(Set.of(
                        regularUserRole.getId(),
                        adminUserRole.getId(),
                        missingRoleId
                ))
                .build();

        String actual = mockMvc.perform(MockMvcRequestBuilders
                        .post(USERS_URL)
                        .content(writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = loadJson(response);
        JSONAssert.assertEquals(
                String.format(expected, missingRoleId),
                actual,
                JSONCompareMode.STRICT);
    }

    @Test
    void post_shouldCreateUser(
            @Value("classpath:user/responses/postUser.json")
            Resource response) throws Exception {
        Role regularUserRole = roleRepository.save(createUserRoleBuilder().build());
        Role adminUserRole = roleRepository.save(createAdminRoleBuilder().build());

        UserRequest request = UserRequest.builder()
                .username(NEW_USER_NAME)
                .password(NEW_USER_PASSWORD)
                .state(TRUE)
                .roleIds(Set.of(
                        regularUserRole.getId(),
                        adminUserRole.getId()
                ))
                .build();

        String responsesJson = mockMvc.perform(MockMvcRequestBuilders
                        .post(USERS_URL)
                        .content(writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserResponse actual = objectMapper.readValue(responsesJson, new TypeReference<>() {});

        JSONAssert.assertEquals(
                loadJson(response),
                writeValueAsString(actual),
                JSONCompareMode.LENIENT);
    }
}
