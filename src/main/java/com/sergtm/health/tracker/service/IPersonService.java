package com.sergtm.health.tracker.service;

import com.sergtm.health.tracker.persistence.entity.Person;

import java.util.Collection;

public interface IPersonService {
    void deletePerson(Long id);
    Person addPerson(String firstName, String secondName);
    Collection<Person> findAll();
    Collection<Person> getByUser(String userName);
    Person getByName(String firstName, String secondName);
	Person findByIdOrThrowException(Long personId);
}
