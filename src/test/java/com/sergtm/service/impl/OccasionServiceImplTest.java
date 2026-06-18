package com.sergtm.service.impl;

import com.sergtm.OccasionLevel;
import com.sergtm.entities.Disease;
import com.sergtm.entities.Occasion;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.repository.DiseaseRepository;
import com.sergtm.health.tracker.persistence.repository.OccasionRepository;
import com.sergtm.health.tracker.rest.request.OccasionRequest;
import com.sergtm.health.tracker.service.IPersonService;
import com.sergtm.service.IOccasionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OccasionServiceImplTest {
	private static final Long PERSON_ID = 1L;
	private static final String DISEASE_NAME = "epilepsy";
	private static final OccasionLevel OCCASION_LEVEL = OccasionLevel.MIDDLE;
	private static final boolean IS_CONVULSION = true;

	private static final LocalDateTime OCCASION_LOCAL_DT = LocalDateTime.of(2021, 10, 20, 17, 25);
	private static final Date OCCASION_DATE = Date.from(OCCASION_LOCAL_DT.atZone(ZoneId.systemDefault()).toInstant());

	@Mock
	private IPersonService personService;
	@Mock
	private OccasionRepository occasionRepository;
	@Mock
	private DiseaseRepository diseaseRepository;

	@Mock
	private Disease disease;
	@Captor
	private ArgumentCaptor<Occasion> occasionCaptor;

	@InjectMocks
	private IOccasionService testedInstance = new OccasionServiceImpl();

	@Test
	void shouldThrowExceptionWhenOccasionIsNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			testedInstance.addOccasion(createPerson(), null);
		});
	}

	@Test
	void shouldPopulateAndSaveOccasion() {
		when(diseaseRepository.findOneByName(DISEASE_NAME)).thenReturn(disease);

		Person person = createPerson();
		testedInstance.addOccasion(person, createOccasionDto());

		verify(occasionRepository).save(occasionCaptor.capture());
		Occasion occasion = occasionCaptor.getValue();

		assertEquals(person, occasion.getPerson());
		assertEquals(OCCASION_LEVEL, occasion.getOccasionLevel());
		assertEquals(IS_CONVULSION, occasion.isConvulsion());
		assertEquals(disease, occasion.getDisease());
		assertEquals(OCCASION_DATE, occasion.getOccasionDate());
	}

	private static OccasionRequest createOccasionDto() {
		return OccasionRequest.builder()
				.occasionLevel(OCCASION_LEVEL)
				.convulsion(IS_CONVULSION)
				.occasionDate(OCCASION_LOCAL_DT)
				.build();
	}

	private static Person createPerson() {
		return Person.builder()
				.id(PERSON_ID)
				.build();
	}
}
