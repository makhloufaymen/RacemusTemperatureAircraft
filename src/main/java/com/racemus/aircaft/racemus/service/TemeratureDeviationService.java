package com.racemus.aircaft.racemus.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.racemus.aircaft.racemus.entity.TemperatureValues;
import com.racemus.aircaft.racemus.repository.ITemperatureDeviationRepository;

@Service 
public class TemeratureDeviationService implements ITemperatureDeviationService{
	
	
	@Autowired
	private ITemperatureDeviationRepository iTemperatureDeviationRepository;

	@Override
	public TemperatureValues getTemperatureDeviation(String acModel, Double altitude, String aeroPhase) {
		// TODO Auto-generated method stub
		return iTemperatureDeviationRepository.getTemperatureDeviation(acModel, altitude, aeroPhase);
	}
	
	


	
	 
	

}
