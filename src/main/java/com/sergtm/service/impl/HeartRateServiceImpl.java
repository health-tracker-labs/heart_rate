package com.sergtm.service.impl;

import com.sergtm.dao.IHeartRateDao;
import com.sergtm.dao.IHelpDao;
import com.sergtm.dao.IPersonDao;
import com.sergtm.dto.StatisticOnDay;
import com.sergtm.entities.*;
import com.sergtm.form.AddHeartRateForm;
import com.sergtm.service.IHeartRateService;
import com.sergtm.service.IUserService;
import com.sergtm.util.DateUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class HeartRateServiceImpl implements IHeartRateService {
    private static int RECORD_COUNT_ON_PAGE = 5;
    @Autowired
    private IHeartRateDao heartRateDao;
    @Autowired
    private IPersonDao personDao;
    @Autowired
    private IHelpDao helpDao;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public Collection<? extends IEntity> createHeartRate(int upperPressure, int lowerPressure, int beatsPerMinute, Date datetime, String firstName, String secondName) {
        List<Person> people = personDao.getPersonByName(firstName, secondName);

        if (people.size() == 1) {
            HeartRate hr = createAndSaveHeartRate(upperPressure, lowerPressure, beatsPerMinute, datetime, people.get(0));

            return Arrays.asList(hr);
        } else if (people.size() == 0) {
            Person person = Person.createPerson(firstName, secondName);
            personDao.savePerson(person);

            HeartRate hr = createAndSaveHeartRate(upperPressure, lowerPressure, beatsPerMinute, datetime, person);
            return Arrays.asList(hr);
        } else {
            return people;
        }
    }

    //Rewrite
    @Override
    @Transactional
    public HeartRate createHeartRate(AddHeartRateForm form) {
        Person person = personDao.getPersonById(form.getPersonId());
        return createAndSaveHeartRate(form.getUpperPressure(),
                form.getLowerPressure(), form.getBeatsPerMinute(), form.getDate(), person);
    }

    //Rewrite
    @Override
    @Transactional
    public void addHeartRateById(Long id, int upperPressure, int lowerPressure, int beatsPerMinute, Date datetime) {
        Person person = personDao.getPersonById(id);
        HeartRate heartRate = HeartRate.createHeartRate(upperPressure, lowerPressure, beatsPerMinute, datetime, person);
        heartRateDao.addHeartRate(heartRate);
    }

    @Override
    @Transactional
    public Collection<? extends IEntity> getHelp(String query, String topicName) {
        return helpDao.getByTopic(query, topicName);

    }

    @Override
    @Transactional
    public Collection<? extends IEntity> findByPage(final int page) {
        int pageNumber = page;
        if (page <= 0) {
            pageNumber = 1;
        }
        int firstResult = (pageNumber * RECORD_COUNT_ON_PAGE) - RECORD_COUNT_ON_PAGE;
        return heartRateDao.getByPage(firstResult, RECORD_COUNT_ON_PAGE);
    }

    @Override
    @Transactional
    public boolean deleteHeartRate(Long id) {
        HeartRate heartRate = heartRateDao.getById(id);
        if (heartRate == null) {
            return false;
        }
        try {
            heartRateDao.deleteHeartRate(heartRate);
            return true;
        } catch (HibernateException e) {

        }
        return false;
    }

    private HeartRate createAndSaveHeartRate(int upperPressure, int lowerPressure, int beatsPerMinute, Date datetime, Person person) {
        HeartRate hr = HeartRate.createHeartRate(upperPressure, lowerPressure, beatsPerMinute, datetime, person);
        heartRateDao.addHeartRate(hr);
        return hr;
    }

    @Override
    public Collection<StatisticOnDay> getChartData(Long personId, String from, String to, String userName) {
        User user = userService.findUserByUsername(userName);

        LocalDate now = LocalDate.now();
        LocalDateTime firstDayOfMonth = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth()).atTime(23, 59);

        LocalDateTime fromDate = DateUtils.parseDate(from, firstDayOfMonth);
        LocalDateTime toDate = DateUtils.parseDate(to, lastDayOfMonth);

        Collection<HeartRateWithWeatherPressure> heartRateWithWeatherPressures = heartRateDao.getData(
                Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant()), personId, user);

        return heartRateWithWeatherPressures.stream()
                .map(StatisticOnDay::new)
                .collect(Collectors.toList());
    }
}
