package com.sergtm.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sergtm.dto.StatisticOnDay;
import com.sergtm.entities.HeartRate;
import com.sergtm.entities.IEntity;
import com.sergtm.form.AddHeartRateForm;
import com.sergtm.service.IHeartRateService;

@RestController
@RequestMapping("/heartRate")
public class HeartRateController {
	@Autowired
	private IHeartRateService heartRateService;

	@RequestMapping(method = RequestMethod.GET, path = "add.json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Collection<? extends IEntity> addHeartRate(
			@RequestParam int upperPressure, @RequestParam int lowerPressure,
			@RequestParam int beatsPerMinute,
			@RequestParam(value = "date", required = false) Date date,
			@RequestParam String firstName,
			@RequestParam String secondName/* , Authentication authentication */) {
		LocalDateTime dt = checkParam(date);
		return heartRateService.createHeartRate(upperPressure, lowerPressure,
				beatsPerMinute, dt, firstName, secondName);
	}

	@RequestMapping(method = RequestMethod.GET, path = "addById")
	@ResponseStatus(HttpStatus.CREATED)
	public void addHeartRateById(@RequestParam Long id,
			@RequestParam int upperPressure, @RequestParam int lowerPressure,
			@RequestParam int beatsPerMinute,
			@RequestParam(value = "date", required = false) Date date) {
		LocalDateTime dt = checkParam(date);
		heartRateService.addHeartRateByPersonId(id, upperPressure,
				lowerPressure, beatsPerMinute, dt);
	}

	@RequestMapping(method = RequestMethod.GET, path = "getByPage.xml", produces = "application/xml")
	public Collection<? extends IEntity> getAll(@RequestParam(defaultValue = "1") int page) {
		return heartRateService.findByPage(page);
	}

	@RequestMapping(method = RequestMethod.GET, path = "getByPage.json", produces = "application/json")
	public Collection<? extends IEntity> getAllJSon(
			@RequestParam(defaultValue = "1") int page) {
		return heartRateService.findByPage(page);
	}

	@RequestMapping(path = "delete.do")
	public boolean delete(@RequestParam Long id) {
		return heartRateService.deleteHeartRate(id);
	}

	@GetMapping(path = "findById.json", produces = "application/json")
	public HeartRate findById(Long id) {
		return heartRateService.findById(id);
	}

	@RequestMapping(method = RequestMethod.GET, path = "getByDateRangeAndPerson.json", produces = "application/json")
	public Collection<? extends IEntity> getByDateRangeAndPerson(Long personId,
			@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss") LocalDateTime from,
			@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss") LocalDateTime to,
			Authentication authentication) {
		return heartRateService.findHeartRatesByDateRangeAndPerson(personId,
				from, to, authentication.getName());
	}

	@RequestMapping(method = RequestMethod.GET, path = "chart.json", produces = "application/json")
	public Collection<StatisticOnDay> getChartData(Long personId, String from,
			String to, Authentication authentication) {
		return heartRateService.getChartData(personId, from, to,
				authentication.getName());
	}

	@RequestMapping(method = RequestMethod.POST, path = "save.do")
	public void save(Long id, @ModelAttribute AddHeartRateForm form) {
		heartRateService.createHeartRate(id, form);
	}

	private LocalDateTime checkParam(Date date) {
		return LocalDateTime.ofInstant(
				Optional.ofNullable(date).orElse(new Date()).toInstant(),
				ZoneId.systemDefault());
	}
}