package com.sergtm.service.impl;

import com.sergtm.dao.IRoleDao;
import com.sergtm.entities.Role;
import com.sergtm.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

@Service
public class RoleService implements IRoleService {
    @Resource
    private IRoleDao roleDao;

    @Override
    public Collection<Role> getAll() {
        return roleDao.getAll();
    }
}
