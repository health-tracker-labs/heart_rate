package com.sergtm.health.tracker.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sergtm.health.tracker.testsupport.entry.UserEntryFixture.createUserBuilder;
import static com.sergtm.health.tracker.testsupport.entry.UserEntryFixture.createUserRoleBuilder;
import static java.nio.charset.StandardCharsets.UTF_8;

@AutoConfigureMockMvc
@Transactional
class AbstractRestControllerIT {
    protected static final String USER_NAME = "testuser";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = createUserBuilder()
                .roles(Set.of(createUserRoleBuilder().build()))
                .build();
        userRepository.save(user);
    }

    protected String loadJson(Resource response) throws IOException {
        return new String(
                response.getInputStream().readAllBytes(),
                UTF_8);
    }

    protected <T> String writeValueAsString(T request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

    protected <T> MultiValueMap convertRequestToMultiValueMap(T request) throws JsonProcessingException {
        Map<String, String> valuesHashMap = objectMapper.readValue(writeValueAsString(request), HashMap.class);
        Map<String, List<String>> multiValueMap = valuesHashMap.entrySet().stream().collect(
                Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(e -> String.valueOf(e.getValue()), Collectors.toList()))
        );
        return new LinkedMultiValueMap<>(multiValueMap);
    }
}
