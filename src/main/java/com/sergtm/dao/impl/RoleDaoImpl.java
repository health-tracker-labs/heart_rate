package com.sergtm.dao.impl;

import com.sergtm.dao.IRoleDao;
import com.sergtm.entities.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
@Transactional(readOnly = true)
public class RoleDaoImpl implements IRoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<Role> getAll() {
        String sql = "FROM Role";
        Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }
}
