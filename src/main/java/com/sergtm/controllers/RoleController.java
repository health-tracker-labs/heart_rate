package com.sergtm.controllers;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergtm.entities.Role;
import com.sergtm.service.IRoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private IRoleService roleService;

    @GetMapping(path = "/getRoles.json", produces = "application/json")
    public Collection<Role> getRoles(){
        return roleService.getAll();
    }
}
