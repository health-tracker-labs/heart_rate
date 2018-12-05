package com.sergtm.dao;

import com.sergtm.entities.Person;
import com.sergtm.entities.User;
import org.springframework.security.access.method.P;

import java.util.Collection;
import java.util.List;

/**
 * Created by Sergey on 18.07.2017.
 */
public interface IPersonDao {
    void savePerson(Person person);
    Person getPersonById(Long personId);
    void deletePerson(Person person);
    Collection<Person> findAll();
    List<Person> getPersonByName(String firstName, String secondName);
    Collection<Person> getByUser(User user);
}
