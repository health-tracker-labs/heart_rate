package com.sergtm.service.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.entities.Role;
import com.sergtm.health.tracker.exception.RoleNotFoundException;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.persistence.repository.RoleRepository;
import com.sergtm.health.tracker.rest.request.UserUpdateRequest;
import com.sergtm.health.tracker.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.collections4.CollectionUtils.COMMA;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.size;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private static final String SOME_ROLES_WERE_NOT_FOUND_MSG = "The following roles were not found: [%s]";
    private static final String NO_CONFIGURED_ROLES_WERE_FOUND = "No configured roles were found";

    private final IUserDao userDao;
    private final RoleRepository roleRepository;

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public Collection<User> getUsers() {
        return userDao.getAll();
    }

    @Override
    public void createUser(User user, Set<Long> roleIds) {
        requireNonNull(roleIds);

        List<Role> roles = roleRepository.findAllById(roleIds);
        if (isEmpty(roles)) {
            throw new RoleNotFoundException(NO_CONFIGURED_ROLES_WERE_FOUND);
        } else if (size(roleIds) != size(roles)) {
            String missingRoles = roleIds.stream()
                    .filter(id -> roles.stream().noneMatch(role -> role.getId().equals(id)))
                    .map(String::valueOf)
                    .collect(Collectors.joining(COMMA));
            throw new RoleNotFoundException(String.format(SOME_ROLES_WERE_NOT_FOUND_MSG, missingRoles));
        }

        user.setRoles(Set.copyOf(roles));
        userDao.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userDao.getUserById(id)
                .ifPresent(userDao::deleteUser);
    }

    @Override
    public void updateUserState(Long id, boolean state) {
        Optional<User> userOpt = userDao.getUserById(id);
        userOpt.ifPresent(user -> {
            user.setState(state);
            userDao.save(user);
        });
    }

    @Override
    public void updateUser(Long id, UserUpdateRequest request) {
        Optional<User> userOpt = userDao.getUserById(id);
        userOpt.ifPresent(user -> {
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setState(request.isState());

            userDao.save(user);
        });
    }
}
