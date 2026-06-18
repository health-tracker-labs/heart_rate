package com.sergtm.service;

import com.sergtm.health.tracker.persistence.entity.Person;

public interface IDeletableByPersonService {
    void deleteByPerson(Person person);
}
