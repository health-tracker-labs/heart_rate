package com.sergtm.dao;

import com.sergtm.entities.User;

public interface IUserDao {
    User findUserByUsername(String username);
}
