package com.sergtm.service.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.entities.User;
import com.sergtm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUserDao userDao;

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}
