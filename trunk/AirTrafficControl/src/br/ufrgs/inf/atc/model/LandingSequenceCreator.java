package br.ufrgs.inf.atc.model;

import java.util.Arrays;
import java.util.Comparator;

import br.ufrgs.inf.ga.utils.ShuffleHelper;

/**
 * This class provides methods to create landing sequences.
 * 
 * @author diego
 *
 */
public class LandingSequenceCreator {
	
	/**
	 * The aircrafts static data for a specific ATC problem instance loaded from the file.
	 */
	private final AircraftStaticData[] aircraftsStaticData;
	
	/**
	 * Resolves class dependencies.
	 * @param aircraftsStaticData aircrafts static data loaded from the input file.
	 */
	public LandingSequenceCreator(AircraftStaticData[] aircraftsStaticData) {
		this.aircraftsStaticData = aircraftsStaticData;
	}
	
	/**
	 * Creates an aircraft landing sequence where for each aircraft, it's landing time is
	 * close as possible to it's target time.
	 *
	 * @return an aircraft landing sequence.
	 */
	public Aircraft[] createLandingSequenceSortedByTargetTimes() {
		// Creates a landing sequence where for each aircraft, it's landing time is close as possible to it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithDefaultLandingTime();
		// sort the list of aircraft by it's landing time to generate the landing sequence.
		Arrays.sort(aircraftLandingSequence);

		return aircraftLandingSequence;
	}
	
	/**
	 * Creates an aircraft landing sequence sorted by aircrafts appearance times.
	 *
	 * @return an aircraft landing sequence.
	 */
	public Aircraft[] createLandingSequenceWithEarliestLandingTimes() {
		// Creates a landing sequence where for each aircraft, it's landing time is close as possible to it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithLandindTimeCloseToEarliestLandingTime();
		// sort the list of aircraft by it's appearance time to generate the landing sequence.
		Arrays.sort(aircraftLandingSequence);

		return aircraftLandingSequence;
	}
	
	/**
	 * Creates an aircraft landing sequence sorted by aircrafts latest times.
	 *
	 * @return an aircraft landing sequence.
	 */
	public Aircraft[] createLandingSequenceWithLatestLandingTimes() {
		// Creates a landing sequence where for each aircraft, it's landing time is close as possible to it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithLatestLandingTime();
		// sort the list of aircraft by it's appearance time to generate the landing sequence.
		Arrays.sort(aircraftLandingSequence);

		return aircraftLandingSequence;
	}
	
	/**
	 * Creates a landing sequence ordered by an aircraft penalty cost for landing after the target time.
	 * 
	 * @return landing sequence ordered by penalty costs.
	 */
	public Aircraft[] createLandingSequenceSortedByPenaltyCost() {
		// Creates a landing sequence where for each aircraft, it's landing time is close as possible to it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithDefaultLandingTime();
		ShuffleHelper.shuffle(aircraftLandingSequence);
		
		// sort the list of aircraft by it's penalty cost for landing after target time.
		Arrays.sort(aircraftLandingSequence, new Comparator<Aircraft>() {

			@Override
			public int compare(Aircraft aircraft1, Aircraft aircraft2) {
				if (aircraft1.getLandingAfterTargetTimePenaltyCost() < aircraft2.getLandingAfterTargetTimePenaltyCost()) {
					return 1;
				} else if (aircraft1.getLandingAfterTargetTimePenaltyCost() > aircraft2.getLandingAfterTargetTimePenaltyCost()) {
					return -1;
				} else {
					return 0;
				}
			}
			
		});

		return aircraftLandingSequence;
	}
	
	/**
	 * Creates a landing sequence ordered by an aircraft landing time (generated randomly).
	 * 
	 * @return landing sequence ordered by penalty costs.
	 */
	public Aircraft[] createLandingSequenceSortedByRandomTimes() {
		// Creates a landing sequence where for each aircraft, it's landing time is close as possible to it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithDefaultLandingTime();
		
		for(Aircraft aircraft : aircraftLandingSequence) {
			aircraft.setRandomLandingTimeLessThenTargetTime();
		}
		
		Arrays.sort(aircraftLandingSequence);
		
		return aircraftLandingSequence;
	}
	
	/**
	 * Creates a random landing sequence where for each aircraft, it landing time is its target time.
	 * 
	 * @return a random landing sequence.
	 */
	public Aircraft[] createRandomLandingSequence() {
		// Creates a list of aircrafts where each aircraft landing time is it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithDefaultLandingTime();
		ShuffleHelper.shuffle(aircraftLandingSequence);
		
		return aircraftLandingSequence;
	}

	/**
	 * Creates a vector of aircrafts, where each aircraft landing time is, by default, equals to it's target time.
	 * 
	 * @return Aircraft landing sequence.
	 */
	public Aircraft[] createAircraftsWithDefaultLandingTime() {
		Aircraft[] aircrafts = new Aircraft[aircraftsStaticData.length];
		
		for (int i = 0; i < aircraftsStaticData.length; i++) {
			Aircraft newAircraft = new Aircraft(aircraftsStaticData[i]);
			aircrafts[i] = newAircraft;
		}
		
		return aircrafts;
	}
	
	public Aircraft[] createAircraftsWithLandindTimeCloseToEarliestLandingTime() {
		Aircraft[] aircrafts = new Aircraft[aircraftsStaticData.length];
		
		for (int i = 0; i < aircraftsStaticData.length; i++) {
			Aircraft newAircraft = new Aircraft(aircraftsStaticData[i]);
			newAircraft.setRandomLandingTimeLessThenTargetTime();
			aircrafts[i] = newAircraft;
		}
		
		return aircrafts;
	}
	
	public Aircraft[] createAircraftsWithLatestLandingTime() {
		Aircraft[] aircrafts = new Aircraft[aircraftsStaticData.length];
		
		for (int i = 0; i < aircraftsStaticData.length; i++) {
			Aircraft newAircraft = new Aircraft(aircraftsStaticData[i]);
			newAircraft.setLatestLandingTime();
			aircrafts[i] = newAircraft;
		}
		
		return aircrafts;
	}
}
