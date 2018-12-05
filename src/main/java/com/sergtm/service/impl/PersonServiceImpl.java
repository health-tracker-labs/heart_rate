package com.sergtm.service.impl;

import com.sergtm.dao.IPersonDao;
import com.sergtm.entities.Person;
import com.sergtm.entities.User;
import com.sergtm.service.IPersonService;
import com.sergtm.service.IUserService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonServiceImpl implements IPersonService{
    @Autowired
    private IPersonDao personDao;
    @Autowired
    private IUserService userService;

    @Override
    public boolean deletePerson(Long id) {
        Person person = personDao.getPersonById(id);
        if(person==null){
            return false;
        }
        try {
            personDao.deletePerson(person);
            return true;
        }catch (HibernateException e){

        }
        return false;
    }

    @Override
    public Person addPerson(String firstName, String secondName, String userName) {
        Person person = Person.createPerson(firstName, secondName, userService.findUserByUsername(userName));
        personDao.savePerson(person);
        return person;
    }

    @Override
    public Collection<Person> findAll() {
        return personDao.findAll();
    }

    @Override
    public Collection<Person> getByUser(String userName) {
        User user = userService.findUserByUsername(userName);
        return personDao.getByUser(user);
    }
}
