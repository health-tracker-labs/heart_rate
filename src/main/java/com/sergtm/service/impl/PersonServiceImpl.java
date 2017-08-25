package com.sergtm.service.impl;

import com.sergtm.dao.IPersonDao;
import com.sergtm.entities.Person;
import com.sergtm.service.IPersonService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements IPersonService{
    @Autowired
    IPersonDao personDao;

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
}
