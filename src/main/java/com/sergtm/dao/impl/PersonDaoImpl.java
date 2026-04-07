package com.sergtm.dao.impl;

import com.sergtm.dao.IPersonDao;
import com.sergtm.entities.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Created by Sergey on 18.07.2017.
 */
@Repository
public class PersonDaoImpl implements IPersonDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void savePerson(Person person) {
        if (isNull(person.getId())) {
            entityManager.persist(person);
        } else {
            entityManager.merge(person);
        }
    }

    @Override
    public Person getPersonById(Long personId) {
        return entityManager.find(Person.class, personId);
    }

    @Override
    public Collection<Person> findAll() {
        String sql = "FROM Person";
        Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }

    @Override
    public List<Person> getPersonByName(String firstName, String secondName) {
        String sql = "FROM Person p WHERE p.firstName = :firstName AND p.secondName = :secondName";
        Query query = entityManager.createQuery(sql);
        query.setParameter("firstName", firstName);
        query.setParameter("secondName", secondName);
        return query.getResultList();
    }

    @Override
    public Collection<Person> getByUser(String userName) {
        String sql = "select sm.person from StaffMember sm \n" +
                "join sm.person p \n" +
                "join sm.user u \n" +
                "where u.username = :username";
        Query query = entityManager.createQuery(sql);
        query.setParameter("username", userName);
        return query.getResultList();
    }
}
