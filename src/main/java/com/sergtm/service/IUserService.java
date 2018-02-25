package com.sergtm.service;

import com.sergtm.entities.User;

public interface IUserService {
    User findUserByUsername(String username);
}
