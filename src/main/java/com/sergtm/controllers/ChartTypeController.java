package com.sergtm.controllers;

import com.sergtm.entities.ChartType;
import com.sergtm.service.IChartTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("chartTypes")
@RequiredArgsConstructor
public class ChartTypeController {
    private final IChartTypeService chartTypeService;

    @GetMapping
    @ResponseBody
    public Iterable<ChartType> getChartTypes() {
        return chartTypeService.getChartTypes();
    }
}
