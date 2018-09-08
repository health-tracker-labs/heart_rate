package com.sergtm.service.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.entities.User;
import com.sergtm.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{
    @Resource
    private IUserDao userDao;

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public Collection<User> getAll() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        Optional<User> userOptional = userDao.getUserById(id);
        userOptional.ifPresent(user -> userDao.deleteUser(user));
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
