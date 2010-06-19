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
	 * Schedules the landing times for a sequence of aircrafts landings.
	 * 
	 * @param aircraftLandingSequence aircraft landing sequence to be scheduled.
	 */
	public void schedule(Aircraft[] aircraftLandingSequence) {
		for (int i = 0; i < aircraftLandingSequence.length; i++) {
			// The first aircraft landing time can be it's target time once it don't have to wait for any other aircraft to landing first.
			// So, for any other aircraft landing after the first one, we need to ensure that the time of landing is, at least,
			// the time of the previous landing plus the gap time needed to landing.
			if (i != 0) {
				Aircraft previousAircraft = aircraftLandingSequence[i - 1];
				Aircraft currentAircraft = aircraftLandingSequence[i];
				if (!currentAircraft.respectsGapTimeBetween(previousAircraft)) {
					// If the current aircraft landing time does not respects the restriction, then, this current landing time will be
					// the previous landing time plus the gap time needed to landing.
					currentAircraft.setMinLandingTimeAfterLandingOf(previousAircraft);
				}
			}	
		}
	}
}
