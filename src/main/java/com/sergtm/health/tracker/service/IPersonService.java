package com.sergtm.health.tracker.service;

import com.sergtm.health.tracker.persistence.entity.Person;

import java.util.Collection;

public interface IPersonService {
    void deletePerson(Long id);
    Person addPerson(Person request);
    Collection<Person> findAll();
    Collection<Person> getPersonsAssignedToUser(String userName);
    Person getByName(String firstName, String secondName);
	Person findByIdOrThrowException(Long personId);
}
