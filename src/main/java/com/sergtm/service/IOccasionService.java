package com.sergtm.service;

import java.util.List;

import com.sergtm.controllers.rest.request.OccasionRequest;

public interface IOccasionService {

	void addOccasion(Long personId, OccasionRequest occasionRequest);

	void removeOccasion(Long occasionId);

	List<OccasionRequest> findOccasions();

}
