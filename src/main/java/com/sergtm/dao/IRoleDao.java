package com.sergtm.dao;

import com.sergtm.entities.Role;

import java.util.Collection;

public interface IRoleDao {
    Collection<Role> getAll();
}
