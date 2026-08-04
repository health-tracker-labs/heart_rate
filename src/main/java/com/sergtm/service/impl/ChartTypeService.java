package com.sergtm.service.impl;

import com.sergtm.entities.ChartType;
import com.sergtm.health.tracker.persistence.repository.ChartTypeRepository;
import com.sergtm.service.IChartTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ChartTypeService implements IChartTypeService {
	@Resource
	private ChartTypeRepository chartTypeRepository;

	@Override
	public Iterable<ChartType> getChartTypes() {
		return chartTypeRepository.findAll();
	}
}
