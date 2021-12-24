package com.sergtm.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sergtm.controllers.rest.dto.OccasionDto;
import com.sergtm.entities.Occasion;
import com.sergtm.entities.Person;
import com.sergtm.repository.DiseaseRepository;
import com.sergtm.repository.OccasionRepository;
import com.sergtm.service.IOccasionService;
import com.sergtm.service.IPersonService;

@Service
public class OccasionServiceImpl implements IOccasionService {
	private static final String OCCASION_MUST_NOT_BE_NULL = "The given occasion must not be null!";

	private static final String DISEASE_NAME = "epilepsy";

	@Resource
	private DiseaseRepository diseaseRepository;
	@Resource
	private OccasionRepository occasionRepository;
	@Resource
	private IPersonService personService;

	@Override
	@Transactional
	public void addOccasion(Long personId, OccasionDto occasionDto) {
		Assert.notNull(occasionDto, OCCASION_MUST_NOT_BE_NULL);

		Person person = personService.findByIdOrThrowException(personId);

		Occasion occasion = new Occasion();

		occasion.setPerson(person);
		occasion.setOccasionLevel(occasionDto.getOccasionLevel());
		occasion.setConvulsion(occasionDto.isConvulsion());
		occasion.setDisease(diseaseRepository.findOneByName(DISEASE_NAME));

		LocalDateTime ldt = occasionDto.getOccasionDate();
		ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());

		occasion.setOccasionDate(Date.from(zdt.toInstant()));

		occasionRepository.save(occasion);
	}

	@Override
	public void removeOccasion(Long occasionId) {
		occasionRepository.deleteById(occasionId);
	}

	@Override
	public List<OccasionDto> findOccasions() {
		return StreamSupport.stream(occasionRepository.findAll().spliterator(), false)
				.map(OccasionDto::new).collect(Collectors.toList());
	}
}
