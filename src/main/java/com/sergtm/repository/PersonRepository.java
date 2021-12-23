package com.sergtm.repository;

import org.springframework.data.repository.CrudRepository;

import com.sergtm.entities.Person;

public interface PersonRepository  extends CrudRepository<Person, Long> {

}
