package com.sergtm.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sergtm.dao.IUserDao;
import com.sergtm.entities.User;

@Repository
@Transactional(readOnly = true)
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

    @Override
    public Collection<User> getAll() {
        String sql = "FROM User";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return query.getResultList();
    }

    @Override
    public Optional<User> getUserById(long id) {
        String sql = "FROM User u WHERE  u.id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("id", id);
        List<User> users = query.getResultList();
        if (users.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    @Transactional
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }
}
