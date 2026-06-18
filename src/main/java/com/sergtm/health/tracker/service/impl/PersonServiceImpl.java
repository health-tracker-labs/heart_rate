package com.sergtm.health.tracker.service.impl;

import com.sergtm.dao.IPersonDao;
import com.sergtm.health.tracker.exception.PersonNotFoundException;
import com.sergtm.health.tracker.persistence.entity.Employee;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.entity.User;
import com.sergtm.health.tracker.persistence.repository.PersonRepository;
import com.sergtm.health.tracker.persistence.repository.UserRepository;
import com.sergtm.health.tracker.service.IPersonService;
import com.sergtm.service.IHeartRateService;
import com.sergtm.service.IOccasionService;
import com.sergtm.service.IStaffMemberService;
import com.sergtm.service.IWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements IPersonService {
    private static final String CAN_NOT_FIND_PERSON_BY_PERSON_ID_MESSAGE = "Can't find person by person id = %s";

    @Autowired
    private IPersonDao personDao;
    @Resource
    private IStaffMemberService staffMemberService;
    @Resource
    private IWeightService weightService;
    @Resource
    private IHeartRateService heartRateService;
    @Resource
    private IOccasionService occasionService;
    @Resource
    private PersonRepository personRepository;
    @Resource
    private UserRepository userRepository;

    @Override
    public Collection<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Collection<Person> getByUser(String userName) {
        return userRepository.findOneByUsername(userName)
                .map(User::getEmployee)
                .map(Employee::getId)
                .map(employeeId -> personRepository.findPersonsByEmployeeId(employeeId))
                .orElse(emptyList());
    }

    @Override
    @Transactional
    public void deletePerson(Long id) {
        Person person = findByIdOrThrowException(id);
        deletePersonRelatedData(person);
    }

    private void deletePersonRelatedData(Person person) {
        Stream.of(staffMemberService, weightService, heartRateService, occasionService)
                .forEach(service -> service.deleteByPerson(person));
        personRepository.deleteByPerson(person.getId());
    }

    @Override
    @Transactional
    public Person addPerson(String firstName, String secondName) {
        Person person = Person.builder()
                .firstName(firstName)
                .secondName(secondName)
                .build();
        personDao.savePerson(person);

        return person;
    }

    @Override
    //TODO: rewrite
    public Person getByName(String firstName, String secondName) {
        return personDao.getPersonByName(firstName, secondName).get(0);
    }

    @Override
    public Person findByIdOrThrowException(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException(String.format(CAN_NOT_FIND_PERSON_BY_PERSON_ID_MESSAGE, personId)));
    }
}
