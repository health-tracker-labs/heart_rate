package com.sergtm.health.tracker.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sergtm.entities.Role;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private boolean state;

    private EmployeeResponse employee;

    private Set<Role> roles;
}
