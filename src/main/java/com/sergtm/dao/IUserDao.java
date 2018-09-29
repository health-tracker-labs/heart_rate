package com.sergtm.dao;

import com.sergtm.entities.User;

import java.util.Collection;
import java.util.Optional;

public interface IUserDao {
    User findUserByUsername(String username);
    Collection<User> getAll();
    Optional<User> getUserById(long id);
    void deleteUser(User user);
    void update(User user);
    void save(User user);
}
