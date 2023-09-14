package com.sergtm.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.sergtm.controllers.rest.dto.WeightDto;
import com.sergtm.entities.Person;
import com.sergtm.entities.Weight;
import com.sergtm.repository.WeightRepository;
import com.sergtm.service.IPersonService;
import com.sergtm.service.IWeightService;

@Service
public class WeightServiceImpl implements IWeightService {
	private static final String WEIGHT_MUST_NOT_BE_NULL = "The given weight must not be null!";
	private static final Sort SORT_BY_DATE_ASC = Sort.by(Sort.Direction.ASC, "date");

	@Resource
	private WeightRepository weightRepository;
	@Resource
	private IPersonService personService;

	@Override
	public void addWeight(Long personId, WeightDto weightDto) {
		Assert.notNull(weightDto, WEIGHT_MUST_NOT_BE_NULL);

		Person person = personService.findByIdOrThrowException(personId);

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
	public List<WeightDto> findWeights() {
		return StreamSupport.stream(weightRepository.findAll(SORT_BY_DATE_ASC).spliterator(), false).map(WeightDto::new)
				.collect(Collectors.toList());
	}
}
