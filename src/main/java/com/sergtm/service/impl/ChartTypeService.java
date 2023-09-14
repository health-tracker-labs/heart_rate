package com.sergtm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sergtm.entities.ChartType;
import com.sergtm.repository.ChartTypeRepository;
import com.sergtm.service.IChartTypeService;

@Service
public class ChartTypeService implements IChartTypeService {
	@Resource
	private ChartTypeRepository chartTypeRepository;

	@Override
	public Iterable<ChartType> getChartTypes() {
		return chartTypeRepository.findAll();
	}

}
