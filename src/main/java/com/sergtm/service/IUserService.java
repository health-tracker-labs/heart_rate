package com.sergtm.service;

import com.sergtm.entities.User;

import java.util.Collection;

public interface IUserService {
    User findUserByUsername(String username);
    Collection<User> getAll();
    void deleteUser(long id);
    void update(User user);
}
