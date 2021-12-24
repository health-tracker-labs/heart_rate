package com.sergtm.service;

import java.util.List;

import com.sergtm.controllers.rest.dto.OccasionDto;

public interface IOccasionService {

	void addOccasion(Long personId, OccasionDto occasionDto);

	void removeOccasion(Long occasionId);

	List<OccasionDto> findOccasions();

}
