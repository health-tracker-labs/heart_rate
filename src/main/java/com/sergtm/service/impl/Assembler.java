package com.sergtm.service.impl;

import com.sergtm.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class Assembler {
    public User buildUserFromUserEntity(com.sergtm.entities.User userEntity) {

        String username = userEntity.getUsername();
        String password = userEntity.getPassword();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : userEntity.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(username, password, authorities);
    }
}
