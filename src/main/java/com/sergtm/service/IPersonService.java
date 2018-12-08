package com.sergtm.service;

import com.sergtm.entities.Person;

import java.util.Collection;

public interface IPersonService {
    boolean deletePerson(Long id);
    Person addPerson(String firstName, String secondName, String userName);
    Collection<Person> findAll();
    Collection<Person> getByUser(String userName);
}
