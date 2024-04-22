package com.racemus.aircaft.racemus.entity;

// class combine the two type of phase
public class TemperatureDeviation {
	
	private TemperatureAltitudeMinMax temperature_deviation_in_flight;
	private TemperatureAltitudeMinMax temperature_deviation_in_TOLD;
	
	
	public TemperatureAltitudeMinMax getTemperature_deviation_in_flight() {
		return temperature_deviation_in_flight;
	}
	public void setTemperature_deviation_in_flight(TemperatureAltitudeMinMax temperature_deviation_in_flight) {
		this.temperature_deviation_in_flight = temperature_deviation_in_flight;
	}
	public TemperatureAltitudeMinMax getTemperature_deviation_in_TOLD() {
		return temperature_deviation_in_TOLD;
	}
	public void setTemperature_deviation_in_TOLD(TemperatureAltitudeMinMax temperature_deviation_in_TOLD) {
		this.temperature_deviation_in_TOLD = temperature_deviation_in_TOLD;
	}
	
	
	
	
	
	
	
	
	
	
	

}
