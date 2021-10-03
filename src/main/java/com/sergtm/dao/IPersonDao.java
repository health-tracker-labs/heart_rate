package com.sergtm.dao;

import java.util.Collection;
import java.util.List;

import com.sergtm.entities.Person;
import com.sergtm.entities.User;

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
