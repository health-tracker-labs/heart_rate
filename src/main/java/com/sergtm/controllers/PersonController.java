package com.sergtm.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sergtm.entities.Person;
import com.sergtm.service.IPersonService;

/**
 * Created by Sergey on 14.07.2017.
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private IPersonService personService;

    @RequestMapping(method = RequestMethod.GET, path = "add.json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestParam String firstName, @RequestParam String secondName, String username, Authentication authentication) {
        if (username == null) {
            username = authentication.getName();
        }
        return personService.addPerson(firstName, secondName, username);
    }
    @RequestMapping(method = RequestMethod.GET, path = "getAll.xml", produces = "application/xml")
    public Collection<Person> getAll(){
        return personService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "getAll.json", produces = "application/json")
    public Collection<Person> getAllJSon(){
        return personService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "getByUser.json", produces = "application/json")
    public Collection<Person> get(Authentication authentication){
        return personService.getByUser(authentication.getName());
    }

    @RequestMapping(path = "delete.do")
    public boolean deletePerson(@RequestParam Long id){
       return personService.deletePerson(id);
    }
}

