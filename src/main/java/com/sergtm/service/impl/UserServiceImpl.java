package com.sergtm.service.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.dto.UserDTO;
import com.sergtm.entities.User;
import com.sergtm.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserDao userDao;

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public Collection<UserDTO> getAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<UserDTO> userDTOList = new ArrayList<>();
        Collection<User> users = userDao.getAll();
        for (User user: users) {
            userDTOList.add(modelMapper.map(user, UserDTO.class));
        }
        return userDTOList;
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
