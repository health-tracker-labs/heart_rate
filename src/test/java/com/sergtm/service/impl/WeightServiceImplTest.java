package com.sergtm.service.impl;

import com.sergtm.controllers.rest.request.WeightRequest;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.entities.Weight;
import com.sergtm.health.tracker.persistence.repository.WeightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeightServiceImplTest {
	private static final Long PERSON_ID = 1L;

	private static final LocalDate WEIGHT_LOCAL_DATE = LocalDate.of(2021, 12, 23);
	private static final Date WEIGHT_DATE = Date.from(WEIGHT_LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant());

	private static final BigDecimal WEIGHT = BigDecimal.valueOf(123.12);

	@Mock
	private WeightRepository weightRepository;

	@Captor
	private ArgumentCaptor<Weight> weightCaptor;

	@InjectMocks
	private WeightServiceImpl testedInstance;

	@Test
	void shouldThrowExceptionWhenWeightIsNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			testedInstance.addWeight(createPerson(), null);
		});
	}

	@Test
	void shouldPopulateAndSaveWeight() {
		Person person = createPerson();
		testedInstance.addWeight(person, createWeightDto());

		verify(weightRepository).save(weightCaptor.capture());
		Weight weight = weightCaptor.getValue();

		assertEquals(person, weight.getPerson());
		assertEquals(WEIGHT_DATE, weight.getDate());
		assertEquals(WEIGHT, weight.getWeight());
	}

	private static WeightRequest createWeightDto() {
		WeightRequest weightDto = new WeightRequest();

		weightDto.setDate(WEIGHT_LOCAL_DATE);
		weightDto.setWeight(WEIGHT);

		return weightDto;
	}

	private static Person createPerson() {
		Person person = new Person();
		person.setId(PERSON_ID);

		return person;
	}
}
