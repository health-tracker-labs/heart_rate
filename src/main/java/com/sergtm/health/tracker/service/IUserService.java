package com.sergtm.health.tracker.service;

import com.sergtm.health.tracker.persistence.entity.User;

import java.util.Collection;
import java.util.Set;

public interface IUserService {
    User findUserByUsername(String username);
    Collection<User> getUsers();
    void deleteUser(long id);
    void update(Long id, boolean state);
    void createUser(User user, Set<Long> roleIds);
}
