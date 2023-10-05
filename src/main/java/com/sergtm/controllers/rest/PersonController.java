package com.sergtm.controllers.rest;

import com.sergtm.controllers.rest.response.PersonResponse;
import com.sergtm.controllers.rest.request.PersonRequest;
import com.sergtm.entities.Person;
import com.sergtm.service.IPersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController("person-controller-rest")
@RequestMapping(path = "/persons", produces = "application/json")
public class PersonController {
    @Resource
    private IPersonService personService;

    @GetMapping
    public Collection<PersonResponse> getPersons() {
        return personService.findAll().stream()
                .map(PersonResponse::new)
                .collect(Collectors.toCollection(ArrayList::new));
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
    public PersonResponse createPerson(@RequestBody @Valid PersonRequest request) {
        Person person = personService.addPerson(
                request.getFirstName(),
                request.getSecondName(),
                request.getUserName());
        return new PersonResponse(person);
    }

    @DeleteMapping("/delete/{personId}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable Long personId) {
        boolean isDeleted = personService.deletePerson(personId);
        return ok(isDeleted);
    }
}
