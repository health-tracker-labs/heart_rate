package com.sergtm.service.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.dto.UserDTO;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.service.IUserService;
import com.sergtm.service.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserDao userDao;
    private final UserMapper userMapper;

    public UserServiceImpl(
            IUserDao userDao,
            UserMapper userMapper
    ) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }
    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public Collection<UserDTO> getAll() {
        Collection<User> users = userDao.getAll();
        return userMapper.toUserDtos(users);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        Optional<User> userOptional = userDao.getUserById(id);
        userOptional.ifPresent(user -> userDao.deleteUser(user));
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) {
        Optional<User> userOptional = userDao.getUserById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(userDTO.getPassword());
            user.setUsername(userDTO.getUsername());
            user.setState(userDTO.isState());
        }
    }

    @Override
    public void save(User user) {
        if (user.getId() != null){
            user.setId(null);
        }
        userDao.save(user);
    }
}
