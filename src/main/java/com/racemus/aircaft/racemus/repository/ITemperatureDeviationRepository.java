package com.racemus.aircaft.racemus.repository;

import com.racemus.aircaft.racemus.entity.TemperatureValues;

public interface ITemperatureDeviationRepository {
	public TemperatureValues getTemperatureDeviation(String acModel, Double altitude, String aeroPhase);
}
