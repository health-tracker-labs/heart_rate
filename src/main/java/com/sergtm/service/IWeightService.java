package com.sergtm.service;

import java.util.List;

import com.sergtm.controllers.rest.dto.WeightDto;

public interface IWeightService {
	void addWeight(Long personId, WeightDto weightDto);
	void removeWeight(Long weightId);
	List<WeightDto> findWeights();
}
