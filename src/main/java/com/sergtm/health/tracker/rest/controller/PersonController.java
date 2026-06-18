package com.sergtm.health.tracker.rest.controller;

import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.rest.controller.mapper.PersonMapper;
import com.sergtm.health.tracker.rest.request.PersonRequest;
import com.sergtm.health.tracker.rest.response.PersonResponse;
import com.sergtm.health.tracker.service.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/persons", produces = "application/json")
@RequiredArgsConstructor
public class PersonController {
    private final PersonMapper personMapper;
    private final IPersonService personService;

    @GetMapping
    public Collection<PersonResponse> getPersons() {
        Collection<Person> persons = personService.findAll();
        return personMapper.toResponses(persons);
    }

    @GetMapping(path = "/xml", produces = "application/xml")
    public Collection<PersonResponse> getPersonsXml(){
        return getPersons();
    }

    @GetMapping(path = "{userName}")
    public Collection<PersonResponse> getPersonsByUserName(@PathVariable String userName){
        Collection<Person> persons = personService.getByUser(userName);
        return personMapper.toResponses(persons);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse createPerson(@Valid PersonRequest request) {
        Person person = personService.addPerson(
                request.getFirstName(),
                request.getSecondName());
        return personMapper.toResponse(person);
    }

    @DeleteMapping("/delete/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
    }
}
