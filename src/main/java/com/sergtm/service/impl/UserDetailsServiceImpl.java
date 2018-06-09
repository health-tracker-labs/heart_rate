package com.sergtm.service.impl;

import com.sergtm.UserDetail;
import com.sergtm.entities.User;
import com.sergtm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if (user != null){
            return new UserDetail(user);
        }else{
            throw new UsernameNotFoundException("User wasn't found");
        }
    }
}