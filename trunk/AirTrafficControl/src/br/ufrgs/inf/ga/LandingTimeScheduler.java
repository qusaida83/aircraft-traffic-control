package br.ufrgs.inf.ga;

import br.ufrgs.inf.atc.model.Aircraft;

/**
 * This class implements a set of algorithms for landing sequence times scheduling.
 * 
 * @author diego
 *
 */
public class LandingTimeScheduler {

	/**
	 * Sets the best landing time (target time) for each aircraft and then,
	 * schedules the landing sequence with valid lading times starting from the begin of the sequence.
	 * 
	 * @param aircraftLandingSequence
	 */
	public void scheduleTargetTimesFromBegin(Aircraft[] aircraftLandingSequence) {
		for (Aircraft aircraft : aircraftLandingSequence) {
			aircraft.setBestLandingTime();
		}
		scheduleFromBegin(aircraftLandingSequence);
	}
	
	/**
	 * Sets the best landing time (target time) for each aircraft and then,
	 * schedules the landing sequence with valid lading times starting from the end of the sequence.
	 * 
	 * @param aircraftLandingSequence
	 */
	public void scheduleTargetTimesFromEnd(Aircraft[] aircraftLandingSequence) {
		for (Aircraft aircraft : aircraftLandingSequence) {
			aircraft.setBestLandingTime();
		}
		scheduleFromEnd(aircraftLandingSequence);
	}
	
	/**
	 * Schedules the landing times for a sequence of aircrafts landings,
	 * starting from the begin of the landing sequence.
	 * 
	 * @param aircraftLandingSequence aircraft landing sequence to be scheduled.
	 */
	public void scheduleFromBegin(Aircraft[] aircraftLandingSequence) {
		for (int i = 1; i < aircraftLandingSequence.length; i++) {
			// The first aircraft landing time can be it's target time once it don't have to wait for any other aircraft to landing first.
			// So, for any other aircraft landing after the first one, we need to ensure that the time of landing is, at least,
			// the time of the previous landing plus the gap time needed to landing.
			Aircraft previousAircraft = aircraftLandingSequence[i - 1];
			Aircraft currentAircraft = aircraftLandingSequence[i];
			if (!currentAircraft.respectsGapTimeBetween(previousAircraft)) {
				// If the current aircraft landing time does not respects the restriction, then, this current landing time will be
				// the previous landing time plus the gap time needed to landing.
				currentAircraft.setMinLandingTimeAfterLandingOf(previousAircraft);
			}
		}
	}
	
	/**
	 * Schedules the landing times for a sequence of aircrafts landings,
	 * starting from the end of the landing sequence.
	 * 
	 * @param aircraftLandingSequence aircraft landing sequence to be scheduled.
	 */
	public void scheduleFromEnd(Aircraft[] aircraftLandingSequence) {
		for (int i = aircraftLandingSequence.length - 2; i >= 0; i--) {
			Aircraft nextAircraft = aircraftLandingSequence[i + 1];
			Aircraft currentAircraft = aircraftLandingSequence[i];

			if (!nextAircraft.respectsGapTimeBetween(currentAircraft)) {
				// If the gap between the next aircraft to land and the current one is not respected,
				// then, is set the max valid landing time value for the current aircraft.
				currentAircraft.setMaxLandingTimeBeforeLandingOf(nextAircraft);
			}
		}
	}
	
	/**
	 * This schedule generates for each aircraft random times between aircraft landing time window values, and then
	 * schedule these times starting from the begin of the landing sequence.
	 * 
	 * @param aircraftLandingSequence
	 */
	public void scheduleRandomTimesFromBegin(Aircraft[] aircraftLandingSequence) {
		for(Aircraft aircraft : aircraftLandingSequence) {
			aircraft.setRandomLandingTime();
		}
		
		scheduleFromBegin(aircraftLandingSequence);
	}
	
	/**
	 * This schedule generates for each aircraft random times between aircraft landing time window values, and then
	 * schedule these times starting from the end of the landing sequence.
	 * 
	 * @param aircraftLandingSequence
	 */
	public void scheduleRandomTimesFromEnd(Aircraft[] aircraftLandingSequence) {
		for(Aircraft aircraft : aircraftLandingSequence) {
			aircraft.setRandomLandingTime();
		}
		
		scheduleFromEnd(aircraftLandingSequence);
	}
}
