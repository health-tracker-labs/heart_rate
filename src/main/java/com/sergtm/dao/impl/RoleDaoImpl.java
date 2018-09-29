package com.sergtm.dao.impl;

import com.sergtm.dao.IRoleDao;
import com.sergtm.entities.Role;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.util.Collection;

@Repository
@Transactional(readOnly = true)
public class RoleDaoImpl implements IRoleDao {
    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Collection<Role> getAll() {
        String sql = "FROM Role";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return query.getResultList();
    }
}
