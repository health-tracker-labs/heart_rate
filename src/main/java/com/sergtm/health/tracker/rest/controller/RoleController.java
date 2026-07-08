package com.sergtm.health.tracker.rest.controller;

import com.sergtm.entities.Role;
import com.sergtm.health.tracker.rest.controller.mapper.RoleMapper;
import com.sergtm.health.tracker.rest.response.RoleResponse;
import com.sergtm.health.tracker.service.impl.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping
    public Collection<RoleResponse> getRoles(){
        Collection<Role> roles = roleService.getRoles();
        return roleMapper.toResponses(roles);
    }
}
