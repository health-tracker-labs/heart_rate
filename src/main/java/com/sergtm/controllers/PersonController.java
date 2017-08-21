package com.sergtm.controllers;

import com.sergtm.dao.IPersonDao;
import com.sergtm.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Sergey on 14.07.2017.
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private IPersonDao personDao;

    @RequestMapping(method = RequestMethod.GET, path = "add.json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestParam String firstName, @RequestParam String secondName) {
        Person person = Person.createPerson(firstName, secondName);
        personDao.savePerson(person);
        return person;
    }
    @RequestMapping(method = RequestMethod.GET, path = "getAll.xml", produces = "application/xml")
    public Collection<Person> getAll(){
        return personDao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "getAll.json", produces = "application/json")
    public Collection<Person> getAllJSon(){
        return personDao.findAll();
    }
}

