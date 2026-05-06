package com.sergtm.service.impl;

import com.sergtm.TimeOfDay;
import com.sergtm.dao.IHeartRateDao;
import com.sergtm.dao.IHeartRateWithWeatherDao;
import com.sergtm.dao.IHelpDao;
import com.sergtm.dao.IPersonDao;
import com.sergtm.dto.StatisticOnDay;
import com.sergtm.entities.*;
import com.sergtm.exception.HeartRateNotFoundException;
import com.sergtm.exception.PersonNotFoundException;
import com.sergtm.form.AddHeartRateForm;
import com.sergtm.health.tracker.monitoring.event.UserBpApplicationEvent;
import com.sergtm.health.tracker.persistence.repository.HeartRateRepository;
import com.sergtm.health.tracker.persistence.repository.PersonRepository;
import com.sergtm.service.IHeartRateService;
import com.sergtm.service.IUserService;
import com.sergtm.util.DateUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;

@Service
@Transactional(readOnly = true)
public class HeartRateServiceImpl implements IHeartRateService {
	private static final int RECORD_COUNT_ON_PAGE = 5;
	private static final String CAN_NOT_FIND_HEART_RATE_BY_ID_MSG = "Can't find heart rate by id = %s";
	private static final String CAN_NOT_FIND_PERSON_BY_ID_MSG = "Can't find person by id = %s";

	@Autowired
	private IHeartRateDao heartRateDao;
	@Autowired
	private IHeartRateWithWeatherDao heartRateWithWeatherDao;
	@Autowired
	private HeartRateRepository heartRateRepository;
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private IPersonDao personDao;
	@Autowired
	private IHelpDao helpDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	@Transactional
	public Collection<? extends IEntity> createHeartRate(
			int upperPressure,
			int lowerPressure,
			int beatsPerMinute,
			LocalDateTime dt,
			String firstName,
			String secondName
	) {
		List<Person> persons = personDao.getPersonByName(firstName, secondName);
		Person person;
		if (persons.isEmpty()) {
			person = Person.createPerson(firstName, secondName);
			personDao.savePerson(person);
		} else {
			person = persons.get(0);
		}

		Instant dtInstant = dt.with(LocalTime.now()).atZone(ZoneId.of("UTC")).toInstant();
		HeartRate hr = HeartRate.createHeartRate(upperPressure, lowerPressure,
				beatsPerMinute, Date.from(dtInstant), person);

		heartRateRepository.save(hr);
		publishUserBpApplicationEvent(hr);

		return singletonList(hr);
	}

	// Rewrite
	@Override
	@Transactional
	public HeartRate createHeartRate(Long id, AddHeartRateForm form) {
		Long personId = form.getPersonId();
		Person person = personRepository.findById(personId)
				.orElseThrow(() -> new PersonNotFoundException(
						String.format(CAN_NOT_FIND_PERSON_BY_ID_MSG, personId))
				);

		HeartRate hr;
		if (isNull(id)) {
			hr = new HeartRate();
		} else {
			hr = heartRateRepository
					.findById(id)
					.orElseThrow(() -> new HeartRateNotFoundException(
							String.format(CAN_NOT_FIND_HEART_RATE_BY_ID_MSG, id))
					);
		}

		LocalDateTime dt = form.getDate();
		TimeOfDay timeOfDay = form.getTimeOfDay();

		Instant dtInstant = dt.with(timeOfDay.getLocalTime())
				.atZone(ZoneId.of("UTC")).toInstant();

		hr.setUpperPressure(form.getUpperPressure());
		hr.setLowerPressure(form.getLowerPressure());
		hr.setBeatsPerMinute(form.getBeatsPerMinute());
		hr.setDate(Date.from(dtInstant));
		hr.setPerson(person);

		heartRateRepository.save(hr);
		publishUserBpApplicationEvent(hr);

		return hr;
	}

	private void publishUserBpApplicationEvent(HeartRate hr) {
		UserBpApplicationEvent event = UserBpApplicationEvent.builder()
				.systolic(hr.getUpperPressure())
				.diastolic(hr.getLowerPressure())
				.timestamp(LocalDateTime.now())
				.build();
		applicationEventPublisher.publishEvent(event);
	}

	// Rewrite
	@Override
	@Transactional
	public void addHeartRateByPersonId(Long id, int upperPressure, int lowerPressure, int beatsPerMinute, LocalDateTime dt) {
		Instant dtInstant = dt.atZone(ZoneId.systemDefault()).toInstant();

		Person person = personDao.getPersonById(id);
		HeartRate heartRate = HeartRate.createHeartRate(upperPressure, lowerPressure, beatsPerMinute, Date.from(dtInstant), person);
		heartRateDao.addHeartRate(heartRate);
	}

	@Override
	public Collection<? extends IEntity> getHelp(String query, String topicName) {
		return helpDao.getByTopic(query, topicName);
	}

	@Override
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
		HeartRate heartRate = heartRateDao.findById(id);
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

	@Override
	public Collection<? extends IEntity> findHeartRatesByDateRangeAndPerson(Long personId, LocalDateTime from, LocalDateTime to, String userName) {
		Instant fromInstant = from.with(LocalTime.of(0, 0, 0)).atZone(ZoneId.of("UTC")).toInstant();
		Instant toInstant = to.with(LocalTime.of(23, 59, 59)).atZone(ZoneId.of("UTC")).toInstant();

		return heartRateDao.findHeartRatesByDateRangeAndPerson(Date.from(fromInstant), Date.from(toInstant), personId);
	}

	@Override
	public Collection<StatisticOnDay> getChartData(Long personId, String from, String to, String userName) {
		User user = userService.findUserByUsername(userName);

		LocalDate now = LocalDate.now();
		LocalDateTime firstDayOfMonth = now.withDayOfMonth(1).atStartOfDay();
		LocalDateTime lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth()).atTime(23, 59);

		LocalDateTime fromDate = DateUtils.parseDate(from, firstDayOfMonth);
		LocalDateTime toDate = DateUtils.parseDate(to, lastDayOfMonth);

		Collection<HeartRateWithWeatherPressure> heartRateWithWeatherPressures = heartRateWithWeatherDao
				.getData(fromDate,
						toDate,
						personId, user
				);

		return heartRateWithWeatherPressures.stream().map(StatisticOnDay::new)
				.collect(Collectors.toList());
	}

	@Override
	public HeartRate findById(Long id) {
		return heartRateDao.findById(id);
	}

	@Override
	public void deleteByPerson(Person person) {
		heartRateRepository.deleteByPerson(person);
	}
}
