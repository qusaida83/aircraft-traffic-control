package br.ufrgs.inf.atc.model;

import java.util.Arrays;
import java.util.Collections;
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
	public Aircraft[] createLandingSequenceSortedByTargetLandingTimes() {
		// Creates a landing sequence where for each aircraft, it's landing time is close as possible to it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithDefaultLandingTime();
		// sort the list of aircraft by it's landing time to generate the landing sequence.
		Arrays.sort(aircraftLandingSequence);

		return aircraftLandingSequence;
	}
	
	/**
	 * Creates an aircraft landing sequence sorted by aircrafts latest times.
	 *
	 * @return an aircraft landing sequence.
	 */
	public Aircraft[] createLandingSequenceSortedByLatestLandingTimes() {
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
		Aircraft[] aircraftLandingSequence = createAircraftsWithRandomLandingTimeLessThenTargetTime();
		
		Arrays.sort(aircraftLandingSequence);
		
		return aircraftLandingSequence;
	}
	
	/**
	 * Creates a landing sequence ordered by an aircraft landing time (generated randomly).
	 * 
	 * @return landing sequence ordered by penalty costs.
	 */
	public Aircraft[] createRandomLandingSequenceWithRandomLandingTimes() {
		// Creates a landing sequence where for each aircraft, it's landing time is close as possible to it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithDefaultLandingTime();
		
		for(Aircraft aircraft : aircraftLandingSequence) {
			aircraft.setRandomLandingTimeLessThenTargetTime();
		}
		
		return aircraftLandingSequence;
	}
	
	/**
	 * Creates a random landing sequence where for each aircraft, it landing time is its target time.
	 * 
	 * @return a random landing sequence.
	 */
	public Aircraft[] createRandomLandingSequenceWithTargetTimes() {
		// Creates a list of aircrafts where each aircraft landing time is it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithDefaultLandingTime();
		ShuffleHelper.shuffle(aircraftLandingSequence);
		
		return aircraftLandingSequence;
	}
	
	/**
	 * Creates a random landing sequence with random landing time for each aircraft. The landing sequence is sorted by
	 * aircraft landing cost parameter.
	 *  
	 * @return a landing sequence.
	 */
	public Aircraft[] createRandomLandingSequenceSortedByAircraftCost() {
		Aircraft[] landingSequence = createAircraftsWithRandomLandingTimeLessThenTargetTime();
		sortLandingSequenceByAircraftCost(landingSequence);
		
		return landingSequence;
	}

	/**
	 * Creates a vector of aircrafts, where each aircraft landing time is, by default, equals to it's target time.
	 * 
	 * @return Aircraft landing sequence.
	 */
	private Aircraft[] createAircraftsWithDefaultLandingTime() {
		Aircraft[] aircrafts = new Aircraft[aircraftsStaticData.length];
		
		for (int i = 0; i < aircraftsStaticData.length; i++) {
			Aircraft newAircraft = new Aircraft(aircraftsStaticData[i]);
			aircrafts[i] = newAircraft;
		}
		
		return aircrafts;
	}
	
	/**
	 * Creates a vector of aircrafts, where each aircraft landing time is a random time between the earliest landing time and the target time the aircraft.
	 * 
	 * @return Aircraft landing sequence.
	 */
	private Aircraft[] createAircraftsWithRandomLandingTimeLessThenTargetTime() {
		Aircraft[] aircrafts = new Aircraft[aircraftsStaticData.length];
		
		for (int i = 0; i < aircraftsStaticData.length; i++) {
			Aircraft newAircraft = new Aircraft(aircraftsStaticData[i]);
			newAircraft.setRandomLandingTimeLessThenTargetTime();
			aircrafts[i] = newAircraft;
		}
		
		return aircrafts;
	}
	
	public Aircraft[] createLandingSequenceWithEarliestLandingTime() {
		Aircraft[] aircrafts = new Aircraft[aircraftsStaticData.length];
		
		for (int i = 0; i < aircraftsStaticData.length; i++) {
			Aircraft newAircraft = new Aircraft(aircraftsStaticData[i]);
			newAircraft.setEarliestLandingTime();
			aircrafts[i] = newAircraft;
		}
		ShuffleHelper.shuffle(aircrafts);
		return aircrafts;
	}
	
	private Aircraft[] createAircraftsWithLatestLandingTime() {
		Aircraft[] aircrafts = new Aircraft[aircraftsStaticData.length];
		
		for (int i = 0; i < aircraftsStaticData.length; i++) {
			Aircraft newAircraft = new Aircraft(aircraftsStaticData[i]);
			newAircraft.setLatestLandingTime();
			aircrafts[i] = newAircraft;
		}
		
		return aircrafts;
	}
	
	/**
	 * Sorts ascendantly a landing sequence by aircraft cost parameter
	 * . 
	 * @param landingSequence
	 */
	private void sortLandingSequenceByAircraftCost(Aircraft[] landingSequence) {
		Arrays.sort(landingSequence, new Comparator<Aircraft>() {

			@Override
			public int compare(Aircraft a1, Aircraft a2) {
				if (a1.getLandingCost() > a2.getLandingCost()) {
					return 1;
				}
				else if (a1.getLandingCost() < a2.getLandingCost()) {
					return -1;
				}
				return 0;
			}
			
		});
	}
}
