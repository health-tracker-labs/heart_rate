package com.sergtm.service.impl;

import com.sergtm.health.tracker.rest.request.WeightRequest;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.entities.Weight;
import com.sergtm.health.tracker.persistence.repository.WeightRepository;
import com.sergtm.service.IWeightService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WeightServiceImpl implements IWeightService {
	private static final String WEIGHT_MUST_NOT_BE_NULL = "The given weight must not be null!";
	private static final Sort SORT_BY_DATE_ASC = Sort.by(Sort.Direction.ASC, "date");

	@Resource
	private WeightRepository weightRepository;

	@Override
	public void addWeight(Person person, WeightRequest weightDto) {
		Assert.notNull(weightDto, WEIGHT_MUST_NOT_BE_NULL);
		Weight model = new Weight();

		model.setPerson(person);
		model.setWeight(weightDto.getWeight());

		LocalDate weightDate = weightDto.getDate();
		ZonedDateTime zdt = weightDate.atStartOfDay(ZoneId.systemDefault());
		model.setDate(Date.from(zdt.toInstant()));

		weightRepository.save(model);
	}

	@Override
	public void removeWeight(Long weightId) {
		weightRepository.deleteById(weightId);
	}

	@Override
	public List<WeightRequest> findWeights() {
		return StreamSupport.stream(weightRepository.findAll(SORT_BY_DATE_ASC).spliterator(), false).map(WeightRequest::new)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteByPerson(Person person) {
		weightRepository.deleteByPerson(person);
	}
}
