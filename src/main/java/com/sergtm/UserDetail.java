package com.sergtm;

import com.sergtm.entities.Role;
import com.sergtm.entities.User;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

public class UserDetail extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserDetail(User user){
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public List<Role> getRole() {
        return user.getRoles();
    }
}
