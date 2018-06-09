package com.sergtm.dao.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

@Repository
@Transactional
public class UserDaoImpl implements IUserDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findUserByUsername(String username) {
        String sql = "FROM User u WHERE u.username = :username";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("username", username);
        return (User) query.getResultList().get(0);
    }
}
