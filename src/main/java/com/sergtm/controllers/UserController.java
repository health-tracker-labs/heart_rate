package com.sergtm.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergtm.dto.UserDTO;
import com.sergtm.entities.User;
import com.sergtm.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping(path = "/getAll.json", produces = "application/json")
    public Collection<UserDTO> getUsers() {
        return userService.getAll();
    }

    @PostMapping(path = "deleteUser")
    public void deleteUser(Long id){
        userService.deleteUser(id);
    }

    @PostMapping(path = "update")
    public void update(@RequestBody UserDTO userDTO){
       userService.update(userDTO);
    }

    @PostMapping(path = "save")
    public void save(@RequestBody User user){
        userService.save(user);
    }
}
