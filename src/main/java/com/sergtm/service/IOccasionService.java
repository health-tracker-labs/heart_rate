package com.sergtm.service;

import com.sergtm.health.tracker.rest.request.OccasionRequest;
import com.sergtm.health.tracker.persistence.entity.Person;

import java.util.List;

public interface IOccasionService extends IDeletableByPersonService {
	void addOccasion(Person person, OccasionRequest occasionRequest);
	void removeOccasion(Long occasionId);
	List<OccasionRequest> findOccasions();
}
