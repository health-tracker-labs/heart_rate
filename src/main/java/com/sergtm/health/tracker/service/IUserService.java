package com.sergtm.health.tracker.service;

import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.rest.request.UserUpdateRequest;

import java.util.Collection;
import java.util.Set;

public interface IUserService {
    User findUserByUsername(String username);
    Collection<User> getUsers();
    void deleteUser(long id);

    void updateUserState(Long id, boolean state);
    void updateUser(Long id, UserUpdateRequest request);

    void createUser(User user, Set<Long> roleIds);
}
