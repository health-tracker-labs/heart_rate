package com.sergtm.service.impl;

import com.sergtm.dao.IHeartRateDao;
import com.sergtm.dao.IHelpDao;
import com.sergtm.dao.IPersonDao;
import com.sergtm.entities.*;
import com.sergtm.service.IHeartRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HeartRateServiceImpl implements IHeartRateService {
    @Autowired
    IHeartRateDao heartRateDao;
    @Autowired
    IPersonDao personDao;
    @Autowired
    IHelpDao helpDao;

    @Override
    public Collection<HeartRate> findAll() {
        Collection<HeartRate> heartRates = heartRateDao.getAllHeartRates();
        return heartRates;
    }

    @Override
    @Transactional
    public Collection<? extends IEntity> createHeartRate(int upperPressure, int lowerPressure, Date datetime, String firstName, String secondName) {
        List<Person> people = personDao.getPersonByName(firstName, secondName);

        if (people.size() == 1) {
            HeartRate hr = createAndSaveHeartRate(upperPressure, lowerPressure, datetime, people.get(0));

            return Arrays.asList(hr);
        } else if (people.size() == 0) {
            Person person = Person.createPerson(firstName, secondName);
            personDao.savePerson(person);

            HeartRate hr = createAndSaveHeartRate(upperPressure, lowerPressure, datetime, person);
            return Arrays.asList(hr);
        } else {
            return people;
        }
    }

    @Override
    @Transactional
    public void addHeartRateById(Long id, int upperPressure, int lowerPressure, Date datetime) {
        Person person = personDao.getPersonById(id);
        HeartRate heartRate = HeartRate.createHeartRate(upperPressure, lowerPressure, datetime, person);
        heartRateDao.addHeartRate(heartRate);
    }

    @Override
    @Transactional
    public Collection<? extends IEntity> getHelp(String query, String topicName) {
        Collection<Help> helps = helpDao.getByTopic(query, topicName);
        return helps;
    }

    private HeartRate createAndSaveHeartRate(int upperPressure, int lowerPressure, Date datetime, Person person) {
        HeartRate hr = HeartRate.createHeartRate(upperPressure, lowerPressure, datetime, person);
        heartRateDao.addHeartRate(hr);
        return hr;
    }
}
