package com.sergtm.service.impl;

import com.sergtm.entities.Role;
import com.sergtm.entities.User;
import com.sergtm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        if (!(username.equals("unauthorized"))) {
            user = userService.findUserByUsername(username);
        }
        if (user != null && user.isState()){
            return buildUserFromUserEntity(user);
        }else{
            throw new UsernameNotFoundException("User wasn't found");
        }
    }

    private org.springframework.security.core.userdetails.User buildUserFromUserEntity(User userEntity) {

        String username = userEntity.getUsername();
        String password = userEntity.getPassword();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : userEntity.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(username, password, authorities);
    }
}