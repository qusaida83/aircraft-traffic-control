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
	 * Schedules the landing times for a sequence of aircrafts landings,
	 * starting from the begin of the landing sequence.
	 * 
	 * @param aircraftLandingSequence aircraft landing sequence to be scheduled.
	 *//*
	public void scheduleFromBegin2(Aircraft[] aircraftLandingSequence) {
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
	}*/
	
	/**
	 * This method try to approximate an aircraft landing time to its target time as possible.
	 * @param aircraftLandingSequence
	 */
	public void interpolateTimeToTargetTime(Aircraft[] aircraftLandingSequence) {
		for (int i = 1; i < aircraftLandingSequence.length; i++) {
			Aircraft currentAircraft = aircraftLandingSequence[i];
			
			Aircraft currentAircraftCopy = currentAircraft.clone();
			currentAircraftCopy.setBestLandingTime();
			
			if (i == 0) {
				Aircraft nextAircraft = aircraftLandingSequence[i + 1];
				if (nextAircraft.respectsGapTimeBetween(currentAircraftCopy)) {
					currentAircraft.setLandingTime(currentAircraftCopy.getLandingTime());
				} else {
					currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime());
					// a is the time space that the aircraft landed before the target time
					int a = currentAircraftCopy.getTargetLandingTime() - currentAircraftCopy.getLandingTime();
					
					if (a >= 0) {
						currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime() + 1);
						while(nextAircraft.respectsGapTimeBetween(currentAircraftCopy)) {
							currentAircraft.setLandingTime(currentAircraftCopy.getLandingTime());
							currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime() + 1);
						}
					}
				}
			}
			else if (i == aircraftLandingSequence.length - 1) {
				Aircraft previousAircraft = aircraftLandingSequence[i - 1];
				if (currentAircraftCopy.respectsGapTimeBetween(previousAircraft)) {
					currentAircraft.setLandingTime(currentAircraftCopy.getLandingTime());
				} else {
					currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime());
					// a is the time space that the aircraft landed before the target time
					int a = currentAircraftCopy.getTargetLandingTime() - currentAircraftCopy.getLandingTime();
					
					if (a < 0) {
						currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime() - 1);
						while(currentAircraftCopy.respectsGapTimeBetween(previousAircraft)) {
							currentAircraft.setLandingTime(currentAircraftCopy.getLandingTime());
							currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime() - 1);
						}
					}
				}
			}
			else {
				Aircraft previousAircraft = aircraftLandingSequence[i - 1];
				Aircraft nextAircraft = aircraftLandingSequence[i + 1];
				if (nextAircraft.respectsGapTimeBetween(currentAircraftCopy) && currentAircraftCopy.respectsGapTimeBetween(previousAircraft)) {
					currentAircraft.setLandingTime(currentAircraftCopy.getLandingTime());
				} else {
					currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime());
					// a is the time space that the aircraft landed before the target time
					int a = currentAircraftCopy.getTargetLandingTime() - currentAircraftCopy.getLandingTime();
					
					if (a >= 0) {
						currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime() + 1);
						while(nextAircraft.respectsGapTimeBetween(currentAircraftCopy)) {
							currentAircraft.setLandingTime(currentAircraftCopy.getLandingTime());
							currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime() + 1);
						}
					} else {
						currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime() - 1);
						while(currentAircraftCopy.respectsGapTimeBetween(previousAircraft)) {
							currentAircraft.setLandingTime(currentAircraftCopy.getLandingTime());
							currentAircraftCopy.setLandingTime(currentAircraft.getLandingTime() - 1);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Schedules the landing times for a sequence of aircrafts landings,
	 * starting from the begin of the landing sequence.
	 * 
	 * @param aircraftLandingSequence aircraft landing sequence to be scheduled.
	 */
	public void scheduleFromBeginBKP(Aircraft[] aircraftLandingSequence) {
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
		
		interpolateTimeToTargetTime(aircraftLandingSequence);
	}
	
	public void scheduleFromBegin(Aircraft[] aircraftLandingSequence) {
		for (int i = 1; i < aircraftLandingSequence.length; i++) {
			// The first aircraft landing time can be it's target time once it don't have to wait for any other aircraft to landing first.
			// So, for any other aircraft landing after the first one, we need to ensure that the time of landing is, at least,
			// the time of the previous landing plus the gap time needed to landing.
			
			Aircraft secondAircraftToLand = aircraftLandingSequence[i - 1];
			Aircraft thirdAircraftToLand = aircraftLandingSequence[i];
			if (i > 1) {
				Aircraft firstAircraftToLand = aircraftLandingSequence[i - 2];
				rearrangeLandingTimes(firstAircraftToLand, secondAircraftToLand, thirdAircraftToLand);
			} else if (!thirdAircraftToLand.respectsGapTimeBetween(secondAircraftToLand)) {
				// If the current aircraft landing time does not respects the restriction, then, this current landing time will be
				// the previous landing time plus the gap time needed to landing.
				thirdAircraftToLand.setMinLandingTimeAfterLandingOf(secondAircraftToLand);
			}
		}
		
		interpolateTimeToTargetTime(aircraftLandingSequence);
	}
	

	/**
	 * Finds the best time scheduling for a sequence of 3 aircraft landings.
	 * @param first
	 * @param second
	 * @param third
	 */
	private void rearrangeLandingTimes(Aircraft first, Aircraft second, Aircraft third) {
		if (!third.respectsGapTimeBetween(second)) {
			// If the current aircraft landing time does not respects the restriction, then, this current landing time will be
			// the previous landing time plus the gap time needed to landing.
			third.setMinLandingTimeAfterLandingOf(second);
		}
		float firstCost = first.getLandingCost() + second.getLandingCost() + third.getLandingCost();
		
		
		Aircraft secondCopy = second.clone();
		Aircraft thirdCopy = third.clone();
		secondCopy.setRandomLandingTimeLessThenTargetTime();
		thirdCopy.setRandomLandingTimeLessThenTargetTime();
		float secondCost = first.getLandingCost() + secondCopy.getLandingCost() + thirdCopy.getLandingCost();
		
		if (secondCopy.respectsGapTimeBetween(first) &&
			thirdCopy.respectsGapTimeBetween(secondCopy) &&
			secondCost < firstCost) {

			second.setLandingTime(secondCopy.getLandingTime());
			third.setLandingTime(thirdCopy.getLandingTime());
			
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
	
}
