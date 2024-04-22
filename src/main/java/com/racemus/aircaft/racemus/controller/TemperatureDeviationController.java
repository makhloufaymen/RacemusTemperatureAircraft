package com.racemus.aircaft.racemus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.racemus.aircaft.racemus.entity.TemperatureValues;
import com.racemus.aircaft.racemus.service.ITemperatureDeviationService;

@RestController
@RequestMapping("/aircraft")
public class TemperatureDeviationController {

	@Autowired
	private ITemperatureDeviationService iTemperatureDeviationService;

	@GetMapping("/csv-data")
	@ResponseBody
	public TemperatureValues getTemperatureDeviation(@RequestParam String acModel, @RequestParam Double altitude,
			@RequestParam String aeroPhase) {
		return iTemperatureDeviationService.getTemperatureDeviation(acModel, altitude, aeroPhase);
	}

}
