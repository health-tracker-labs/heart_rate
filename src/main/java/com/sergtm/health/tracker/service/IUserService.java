package com.sergtm.health.tracker.service;

import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.rest.request.UserRequest;

import java.util.Collection;

public interface IUserService {
    User findUserByUsername(String username);
    Collection<User> getUsers();
    void deleteUser(long id);
    void update(Long id, boolean state);
    void createUser(UserRequest user);
}
