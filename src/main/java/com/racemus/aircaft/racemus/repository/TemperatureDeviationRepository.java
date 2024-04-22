package com.racemus.aircaft.racemus.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.racemus.aircaft.racemus.aerophase.AeroPhaseInFlight;
import com.racemus.aircaft.racemus.aerophase.AeroPhaseInTold;
import com.racemus.aircaft.racemus.entity.TemperatureAltitudeMinMax;
import com.racemus.aircaft.racemus.entity.TemperatureDeviation;
import com.racemus.aircaft.racemus.entity.TemperatureValues;

@Repository
public class TemperatureDeviationRepository implements ITemperatureDeviationRepository {

	private static final String CSV_FILE_PATH = "temperature_deviation.csv";
	private static final String CSV_SPLIT_BY = ";";

	private Map<String, TemperatureDeviation> temperatureDeviations = new HashMap<String, TemperatureDeviation>();

	// load the data
	public TemperatureDeviationRepository() {
		loadTemperatueDeviations();
	}

	// Method to load temperature deviation data from the CSV file
	private void loadTemperatueDeviations() {
		try {
			ClassPathResource resource = new ClassPathResource(CSV_FILE_PATH);
			InputStream inputStream = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			// Skip the header line if it exists
			updateTemperatureDeviations(temperatureDeviations, CSV_SPLIT_BY, br);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to update the temperature deviations map with data from CSV
	private void updateTemperatureDeviations(Map<String, TemperatureDeviation> temperatureDeviations, String cvsSplitBy,
			BufferedReader br) throws IOException {
		String line;
		br.readLine();
		TemperatureDeviation temperatureDeviation;
		TemperatureAltitudeMinMax temperatureMinMaxFlight;
		TemperatureAltitudeMinMax temperatureMinMaxHold;

		while ((line = br.readLine()) != null) {
			// use comma as separator
			temperatureDeviation = new TemperatureDeviation();
			temperatureMinMaxFlight = new TemperatureAltitudeMinMax();
			temperatureMinMaxHold = new TemperatureAltitudeMinMax();
			String acModel = null;
			String[] data = line.split(cvsSplitBy);
			if (data.length >= 5) {
				acModel = data[0].trim();
				temperatureMinMaxFlight.setTemperatureMaxMap(data[1].trim());
				temperatureMinMaxFlight.setTemperatureMinMap(data[2].trim());
				temperatureMinMaxHold.setTemperatureMaxMap(data[3].trim());
				temperatureMinMaxHold.setTemperatureMinMap(data[4].trim());
				temperatureDeviation.setTemperature_deviation_in_flight(temperatureMinMaxFlight);
				temperatureDeviation.setTemperature_deviation_in_TOLD(temperatureMinMaxHold);
				temperatureDeviations.put(acModel, temperatureDeviation);
			}

		}
	}

	@Override
	public TemperatureValues getTemperatureDeviation(String acModel, Double altitude, String aeroPhase) {
		// Initialize temperature values object
		TemperatureDeviation temperatureDeviation = temperatureDeviations.get(acModel);

		if (temperatureDeviation == null) {
			return new TemperatureValues();
		}
		TemperatureAltitudeMinMax temperatureMinMax = new TemperatureAltitudeMinMax();
		if (enumContains(AeroPhaseInFlight.class, aeroPhase)) {
			temperatureMinMax = temperatureDeviation.getTemperature_deviation_in_flight();
		} else if (enumContains(AeroPhaseInTold.class, aeroPhase)) {
			temperatureMinMax = temperatureDeviation.getTemperature_deviation_in_TOLD();

		} else {
			return null;
		}
		return getTemperatureValues(altitude, temperatureMinMax);
	}

	// Method to check if the enum contains a specific value
	private static <T extends Enum<T>> boolean enumContains(Class<T> enumClass, String aeroPhase) {
		try {
			Enum.valueOf(enumClass, aeroPhase);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	// Method to set temperature values based on altitude
	private TemperatureValues getTemperatureValues(Double altitude, TemperatureAltitudeMinMax temperatureMinMax) {

		Double temperatureMin = getValues(temperatureMinMax.getTemperatureMinMap(), altitude);
		Double temperatureMax = getValues(temperatureMinMax.getTemperatureMaxMap(), altitude);
		TemperatureValues temperatureValues = new TemperatureValues();
		temperatureValues.setTmax(temperatureMax);
		temperatureValues.setTmin(temperatureMin);
		return temperatureValues;

	}

	// Method to get temperature values based on altitude
	private static Double getValues(Map<Double, Double> temperatureMap, Double altitude) {
		// sorted altitudes values
		Set<Double> keys = temperatureMap.keySet();
		TreeSet<Double> myAltitudeSorted = new TreeSet<Double>();
		myAltitudeSorted.addAll(keys);

		// handle the way when my altitude is very higher or lower
		Double highestAltitude = myAltitudeSorted.last();
		Double lowestAltitude = myAltitudeSorted.first();
		if ((altitude < lowestAltitude) || (altitude > highestAltitude)) {
			return null;
		}

		if (keys.contains(altitude)) {
			return temperatureMap.get(altitude);
		} else {
			// calculate the temperature value when is between
			Double floorAltitude = myAltitudeSorted.floor(altitude);
			Double ceilingAltitude = myAltitudeSorted.ceiling(altitude);
			return calculateTemperature(altitude, floorAltitude, ceilingAltitude, temperatureMap.get(floorAltitude),
					temperatureMap.get(ceilingAltitude));
		}

	}

	private static Double calculateTemperature(Double altitude, Double lowerAltitude, Double higherAltitude,
			Double lowerTemperature, Double higherTemperature) {
		return lowerTemperature + (altitude - lowerAltitude) * (higherTemperature - lowerTemperature)
				/ (higherAltitude - lowerAltitude);
	}

}
