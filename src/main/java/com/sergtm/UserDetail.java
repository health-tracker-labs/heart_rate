package com.sergtm;


import com.sergtm.entities.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class UserDetail extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserDetail(User user){
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public Role getRole() {
        return user.getRole();
    }
}
