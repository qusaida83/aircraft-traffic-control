package br.ufrgs.inf.atc;

import br.ufrgs.inf.atc.model.Aircraft;
import br.ufrgs.inf.atc.model.AircraftStaticData;


/**
 * This class implements the air traffic control system.
 * 
 * @author diego
 * 
 */
public class AirTrafficControl {

	/**
	 * Vector that holds all aircrafts in the radar visible area.
	 */
	private AircraftStaticData[] aircrafts;

	/**
	 * Constructs the Air traffic control core.
	 * 
	 * @param aircrafts vector with all aircraft on the radar area.
	 * @param gapTimeBetweenAnotherAircraftLanding matrix with all landing time gap between the aircrafts.
	 */
	public AirTrafficControl(AircraftStaticData[] aircrafts) {
		this.aircrafts = aircrafts;
	}

	/**
	 * Generate a landing schedule based on the aircrafts data using a genetic
	 * algorithm.
	 */
	public void scheduleAircraftsLandings() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}
}
