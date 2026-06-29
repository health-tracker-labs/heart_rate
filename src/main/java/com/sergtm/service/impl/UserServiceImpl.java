package com.sergtm.service.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserDao userDao;

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public Collection<User> getUsers() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public void createUser(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        Optional<User> userOptional = userDao.getUserById(id);
        userOptional.ifPresent(userDao::deleteUser);
    }

    @Override
    @Transactional
    public void update(Long id, boolean state) {
        Optional<User> userOpt = userDao.getUserById(id);
        userOpt.ifPresent(user -> {
            user.setState(state);
            userDao.save(user);
        });
    }
}
