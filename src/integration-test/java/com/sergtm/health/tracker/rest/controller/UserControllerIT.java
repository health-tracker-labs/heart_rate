package com.sergtm.health.tracker.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sergtm.entities.Role;
import com.sergtm.health.tracker.AbstractIntegrationTest;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.persistence.repository.RoleRepository;
import com.sergtm.health.tracker.rest.request.UserCreationRequest;
import com.sergtm.health.tracker.rest.request.UserUpdateRequest;
import com.sergtm.health.tracker.rest.response.UserResponse;
import lombok.SneakyThrows;
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
import static com.sergtm.health.tracker.testsupport.request.UserRequestFixture.createUserUpdateRequestBuilder;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Long.MAX_VALUE;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerIT extends AbstractIntegrationTest {
    private static final String USERS_URL = "/users";
    private static final String UPDATE_USER_STATE_URL = "/users/{userId}";

    private static final String NEW_USER_NAME = "newUser";
    private static final String NEW_USER_PASSWORD = "newPassword";

    private static final String EXIST_USER_NAME = "existUser";
    private static final String EXIST_USER_PASSWORD = "existPassword";

    private static final String STATE_PARAM_NAME = "state";

    @Autowired
    private RoleRepository roleRepository;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {SPACE})
    @SneakyThrows
    void post_shouldReturnBadRequest_whenUsernameIsInvalid(String userName) {
        Role regularUserRole = roleRepository.save(createUserRoleBuilder().build());

        UserCreationRequest request = UserCreationRequest.builder()
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
    @SneakyThrows
    void post_shouldReturnBadRequest_whenPasswordIsInvalid(String password) {
        Role regularUserRole = roleRepository.save(createUserRoleBuilder().build());

        UserCreationRequest request = UserCreationRequest.builder()
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
    @SneakyThrows
    void post_shouldReturnBadRequest_whenRoleIdsAreInvalid(Set<Long> roleIds) {
        UserCreationRequest request = UserCreationRequest.builder()
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
    @SneakyThrows
    void post_shouldReturnNotFound_whenRoleIdsContainsNonExistingRoles(
            @Value("classpath:user/responses/postUserWithNonExistingRoles404.json")
            Resource response) {
        Role regularUserRole = roleRepository.save(createUserRoleBuilder().build());
        Role adminUserRole = roleRepository.save(createAdminRoleBuilder().build());

        Long missingRoleId = ThreadLocalRandom.current().nextLong(0, MAX_VALUE);

        UserCreationRequest request = UserCreationRequest.builder()
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
    @SneakyThrows
    void post_shouldCreateUser(
            @Value("classpath:user/responses/postUser.json")
            Resource response) {
        Role regularUserRole = roleRepository.save(createUserRoleBuilder().build());
        Role adminUserRole = roleRepository.save(createAdminRoleBuilder().build());

        UserCreationRequest request = UserCreationRequest.builder()
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

    @Test
    @SneakyThrows
    void put_shouldUpdateExistUserStateToFalse_WhenUserIsActive() {
        User user = userRepository.findOneByUsername(USER_NAME).orElseThrow();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(UPDATE_USER_STATE_URL, user.getId())
                        .param(STATE_PARAM_NAME, String.valueOf(FALSE)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(user.getState());
    }

    @Test
    @SneakyThrows
    void put_shouldUpdateExistUser() {
        User user = userRepository.findOneByUsername(USER_NAME).orElseThrow();

        UserUpdateRequest request = createUserUpdateRequestBuilder()
                .id(user.getId())
                .username(EXIST_USER_NAME)
                .password(EXIST_USER_PASSWORD)
                .state(FALSE)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(USERS_URL)
                        .content(writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThat(user.getUsername()).isEqualTo(EXIST_USER_NAME);
        assertThat(user.getPassword()).isEqualTo(EXIST_USER_PASSWORD);
        assertThat(user.getState()).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {SPACE})
    @SneakyThrows
    void put_shouldReturnBadRequest_whenUsernameIsInvalid(String userName) {
        User user = userRepository.findOneByUsername(USER_NAME).orElseThrow();

        UserUpdateRequest request = UserUpdateRequest.builder()
                .id(user.getId())
                .username(userName)
                .password(EXIST_USER_PASSWORD)
                .state(true)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(USERS_URL)
                        .content(writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {SPACE})
    @SneakyThrows
    void put_shouldReturnBadRequest_whenPasswordIsInvalid(String password) {
        User user = userRepository.findOneByUsername(USER_NAME).orElseThrow();

        UserUpdateRequest request = UserUpdateRequest.builder()
                .id(user.getId())
                .username(EXIST_USER_NAME)
                .password(password)
                .state(true)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(USERS_URL)
                        .content(writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void put_shouldReturnBadRequest_whenUserIdIsNull() {
        UserUpdateRequest request = UserUpdateRequest.builder()
                .username(EXIST_USER_NAME)
                .password(EXIST_USER_PASSWORD)
                .state(true)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(USERS_URL)
                        .content(writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
