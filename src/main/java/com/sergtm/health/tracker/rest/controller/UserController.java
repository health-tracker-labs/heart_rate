package com.sergtm.health.tracker.rest.controller;

import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.rest.controller.mapper.UserMapper;
import com.sergtm.health.tracker.rest.request.UserRequest;
import com.sergtm.health.tracker.rest.response.UserResponse;
import com.sergtm.health.tracker.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final IUserService userService;

    @GetMapping
    public Collection<UserResponse> getUsers() {
        Collection<User> users = userService.getUsers();
        return userMapper.toResponses(users);
    }

    @GetMapping(path = "{userName}")
    public UserResponse getUserByUserName(@PathVariable String userName){
        User user = userService.findUserByUsername(userName);
        return userMapper.toResponse(user);
    }

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid UserRequest request) {
        userService.createUser(userMapper.toDomain(request));
    }

    @PutMapping(path = "/update/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Long userId, boolean state){
        userService.update(userId, state);
    }

    @DeleteMapping(path = "/delete/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
