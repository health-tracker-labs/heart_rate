package com.sergtm.service;

import com.sergtm.dto.UserDTO;
import com.sergtm.entities.User;

import java.util.Collection;

public interface IUserService {
    User findUserByUsername(String username);
    Collection<UserDTO> getAll();
    void deleteUser(long id);
    void update(UserDTO userDTO);
    void save(User user);
}
