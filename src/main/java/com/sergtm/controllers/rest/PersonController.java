package com.sergtm.controllers.rest;

import com.sergtm.controllers.rest.request.PersonRequest;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.controller.mapper.PersonMapper;
import com.sergtm.health.tracker.rest.client.PersonApiClient;
import com.sergtm.health.tracker.rest.response.PersonResponse;
import com.sergtm.service.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/persons", produces = "application/json")
@RequiredArgsConstructor
public class PersonController {
    private final IPersonService personService;
    private final PersonMapper personMapper;
    private final PersonApiClient personApiClient;

    @GetMapping
    public Collection<PersonResponse> getPersons() {
        return personApiClient.getPersons();
    }

    @GetMapping(path = "/xml", produces = "application/xml")
    public Collection<PersonResponse> getPersonsXml(){
        return getPersons();
    }

    @GetMapping(path = "{userName}")
    public Collection<Person> getPersonsByUserName(@PathVariable String userName){
        return personService.getByUser(userName);
    }

    @PutMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse createPerson(@Valid PersonRequest request) {
        Person person = personService.addPerson(
                request.getFirstName(),
                request.getSecondName());
        return personMapper.toResponse(person);
    }

    @DeleteMapping("/delete/{personId}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable Long personId) {
        boolean isDeleted = personService.deletePerson(personId);
        return ok(isDeleted);
    }
}
