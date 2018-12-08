package com.sergtm.dao.impl;

import com.sergtm.dao.IPersonDao;
import com.sergtm.entities.Person;
import com.sergtm.entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sergey on 18.07.2017.
 */
@Repository
@Transactional
public class PersonDaoImpl implements IPersonDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void savePerson(Person person) {
        sessionFactory.getCurrentSession().saveOrUpdate(person);

    }

    @Override
    public Person getPersonById(Long personId) {
        return sessionFactory.getCurrentSession().get(Person.class, personId);
    }

    @Override
    public void deletePerson(Person person) {
        sessionFactory.getCurrentSession().delete(person);
    }

    @Override
    public Collection<Person> findAll() {
        String sql = "FROM Person";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return query.getResultList();
    }

    @Override
    public List<Person> getPersonByName(String firstName, String secondName) {
        String sql = "FROM Person p WHERE p.firstName = :firstName AND p.secondName = :secondName";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("firstName", firstName);
        query.setParameter("secondName", secondName);
        return query.getResultList();
    }

    @Override
    public Collection<Person> getByUser(User user) {
        String sql = "FROM Person p where (p.user.id = 0 or p.user.id = :user_id)";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("user_id", user.getId());
        return query.getResultList();
    }
}
