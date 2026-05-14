package com.sergtm.dto;

import com.sergtm.entities.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private boolean state;
    Set<Role> roles;
}
