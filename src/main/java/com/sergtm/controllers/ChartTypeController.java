package com.sergtm.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sergtm.entities.ChartType;
import com.sergtm.service.IChartTypeService;

@Controller
@RequestMapping("chartTypes")
public class ChartTypeController {
	@Resource
	private IChartTypeService chartTypeService;

	@GetMapping
	@ResponseBody
	public Iterable<ChartType> getChartTypes() {
		return chartTypeService.getChartTypes();
	}
}
