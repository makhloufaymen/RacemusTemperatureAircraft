package com.racemus.aircaft.racemus.entity;

import java.util.HashMap;
import java.util.Map;

public class TemperatureAltitudeMinMax {

	Map<Double, Double> temperatureMinMap;
	Map<Double, Double> temperatureMaxMap;

	private static final String SplitByTwoPoint = ":";
	private static final String SplitByRod = "\\|";

	public Map<Double, Double> getTemperatureMinMap() {
		return temperatureMinMap;
	}

	public void setTemperatureMinMap(String temperaturesMin) {
		this.temperatureMinMap = splitTemperature(temperaturesMin);
	}

	public Map<Double, Double> getTemperatureMaxMap() {
		return temperatureMaxMap;
	}

	public void setTemperatureMaxMap(String temperaturesMax) {
		this.temperatureMaxMap = splitTemperature(temperaturesMax);
	}

	
	// parse and split the values of the temperatures
	private static Map<Double, Double> splitTemperature(String temp) {
		String[] dataTemp = temp.split(SplitByRod);
		Map<Double, Double> MapTemp = new HashMap<Double, Double>();

		for (String data : dataTemp) {
			String[] dataValues = data.split(SplitByTwoPoint);
			if (dataValues.length >= 2) {
				MapTemp.put(Double.parseDouble(dataValues[0].trim()), Double.parseDouble(dataValues[1].trim()));
			}
		}
		return MapTemp;

	}

}
