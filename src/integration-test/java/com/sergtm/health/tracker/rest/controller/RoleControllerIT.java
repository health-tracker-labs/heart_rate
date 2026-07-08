package com.sergtm.health.tracker.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sergtm.entities.Role;
import com.sergtm.health.tracker.persistence.repository.RoleRepository;
import com.sergtm.health.tracker.rest.response.RoleResponse;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Set;

import static com.sergtm.health.tracker.testsupport.entry.RoleEntryFixture.createAdminRoleBuilder;
import static com.sergtm.health.tracker.testsupport.entry.RoleEntryFixture.createUserRoleBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class RoleControllerIT extends AbstractRestControllerIT {
    private static final String ROLES_URL = "/roles";

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void get_shouldReturnAllRoles(
            @Value("classpath:role/responses/getRoles.json")
            Resource response
    ) throws Exception {
        Role regularUserRole = roleRepository.save(createUserRoleBuilder().build());
        Role adminUserPerson = roleRepository.save(createAdminRoleBuilder().build());

        Set<Role> roles = Set.of(regularUserRole, adminUserPerson);

        String actualJson = mockMvc.perform(MockMvcRequestBuilders
                        .get(ROLES_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<RoleResponse> responses = objectMapper.readValue(actualJson, new TypeReference<>() {});
        List<RoleResponse> actual = responses.stream()
                .filter(resp -> roles.stream()
                        .anyMatch(role -> role.getId().equals(resp.getId())))
                .toList();

        JSONAssert.assertEquals(
                loadJson(response),
                writeValueAsString(actual),
                JSONCompareMode.LENIENT);
    }

}
