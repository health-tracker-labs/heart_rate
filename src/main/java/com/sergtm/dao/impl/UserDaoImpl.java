package com.sergtm.dao.impl;

import com.sergtm.dao.IUserDao;
import com.sergtm.health.tracker.persistence.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class UserDaoImpl implements IUserDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUserByUsername(String username) {
        String sql = "FROM User u WHERE u.username = :username";
        Query query = entityManager.createQuery(sql);
        query.setParameter("username", username);
        return (User) query.getResultList().get(0);
    }

    @Override
    public Collection<User> getAll() {
        String sql = "FROM User";
        Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }

    @Override
    public Optional<User> getUserById(long id) {
        String sql = "FROM User u WHERE  u.id = :id";
        Query query = entityManager.createQuery(sql);
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
        entityManager.remove(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }
}
