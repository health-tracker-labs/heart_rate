package com.sergtm.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sergtm.dao.IPersonDao;
import com.sergtm.entities.Person;
import com.sergtm.entities.StaffMember;
import com.sergtm.entities.User;
import com.sergtm.service.IPersonService;
import com.sergtm.service.IStaffMemberService;
import com.sergtm.service.IUserService;

@Service
public class PersonServiceImpl implements IPersonService{
    @Autowired
    private IPersonDao personDao;
    @Autowired
    private IUserService userService;
    @Resource
    private IStaffMemberService staffMemberService;

    @Override
    public boolean deletePerson(Long id) {
        Person person = personDao.getPersonById(id);
        if(person==null){
            return false;
        }
        try {
            personDao.deletePerson(person);
            return true;
        }catch (HibernateException ignored){

        }
        return false;
    }

    @Override
    public Person addPerson(String firstName, String secondName, String userName) {
        Person person = createPerson(firstName, secondName, userService.findUserByUsername(userName));
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

    @Override
    //TODO: rewrite
    public Person getByName(String firstName, String secondName) {

        return personDao.getPersonByName(firstName, secondName).get(0);
    }

    private Person createPerson(String firstName, String secondName, User user){
        Set<StaffMember> staffMembers = new HashSet<>();
        StaffMember staffMember = staffMemberService.getByUser(user);
        if (staffMember != null)
            staffMembers.add(staffMember);

        Person person = new Person();
        person.setFirstName(firstName);
        person.setSecondName(secondName);
        person.setStaffMembers(staffMembers);
        return person;
    }
}
