package com.sergtm.health.tracker.service.impl;

import com.sergtm.entities.Role;
import com.sergtm.health.tracker.persistence.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Collection<Role> getRoles() {
        return roleRepository.findAll();
    }
}
