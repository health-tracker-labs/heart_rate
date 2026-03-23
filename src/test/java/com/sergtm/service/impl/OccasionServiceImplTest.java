package com.sergtm.service.impl;

import com.sergtm.OccasionLevel;
import com.sergtm.controllers.rest.request.OccasionRequest;
import com.sergtm.entities.Disease;
import com.sergtm.entities.Occasion;
import com.sergtm.entities.Person;
import com.sergtm.repository.DiseaseRepository;
import com.sergtm.repository.OccasionRepository;
import com.sergtm.service.IOccasionService;
import com.sergtm.service.IPersonService;
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
public class OccasionServiceImplTest {
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
	public void shouldThrowExceptionWhenOccasionIsNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			testedInstance.addOccasion(createPerson(), null);
		});
	}

	@Test
	public void shouldPopulateAndSaveOccasion() {
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

	private OccasionRequest createOccasionDto() {
		OccasionRequest occasionRequest = new OccasionRequest();

		occasionRequest.setOccasionLevel(OCCASION_LEVEL);
		occasionRequest.setConvulsion(IS_CONVULSION);
		occasionRequest.setOccasionDate(OCCASION_LOCAL_DT);

		return occasionRequest;
	}

	private static Person createPerson() {
		Person person = new Person();
		person.setId(PERSON_ID);

		return person;
	}
}
