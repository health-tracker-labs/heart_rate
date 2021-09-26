package com.sergtm.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergtm.OccasionLevel;
import com.sergtm.entities.Occasion;
import com.sergtm.repository.DiseaseRepository;
import com.sergtm.repository.OccasionRepository;
import com.sergtm.service.IOccasionService;

@Service
public class OccasionServiceImpl implements IOccasionService {
	private static final String DISEASE_NAME = "epilepsy";

	@Autowired
	private DiseaseRepository diseaseRepository;
	@Autowired
	private OccasionRepository occasionRepository;

	@Override
	@Transactional
	public void addOccasion(OccasionLevel occasionLevel, boolean convulsion, LocalDateTime ldt) {
		Occasion occasion = new Occasion();

		occasion.setOccasionLevel(occasionLevel);
		occasion.setConvulsion(convulsion);
		occasion.setDisease(diseaseRepository.findOneByName(DISEASE_NAME));
		occasion.setOccasionDate(ldt);

		occasionRepository.save(occasion);
	}

}
