package com.sergtm.dao.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    public void deleteUser(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }
}
