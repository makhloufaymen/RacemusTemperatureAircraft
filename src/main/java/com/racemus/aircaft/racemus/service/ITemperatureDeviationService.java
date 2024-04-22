package com.racemus.aircaft.racemus.service;

import com.racemus.aircaft.racemus.entity.TemperatureValues;

public interface ITemperatureDeviationService {
	
	public TemperatureValues getTemperatureDeviation(String acModel, Double  altitude, String aeroPhase);


}
