package com.sergtm.service;

import com.sergtm.entities.ChartType;

public interface IChartTypeService {
	Iterable<ChartType> getChartTypes();
}