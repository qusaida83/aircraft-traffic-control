package br.inf.ufrgs.atc;


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
	private Aircraft[] aircrafts;

	/**
	 * Matrix nxn where n is the aircraft count in the radar visible area. This
	 * matrix holds the gap time required for the aircraft j lands after the
	 * landing of the aircraft i, with i and j in the range [0 .. n - 1].
	 */
	private int[][] gapTimeBetweenAnotherAircraftLanding;

	/**
	 * Constructs the Air traffic control core.
	 * 
	 * @param aircrafts vector with all aircraft on the radar area.
	 * @param gapTimeBetweenAnotherAircraftLanding matrix with all landing time gap between the aircrafts.
	 */
	public AirTrafficControl(Aircraft[] aircrafts, int[][] gapTimeBetweenAnotherAircraftLanding) {
		this.aircrafts = aircrafts;
		this.gapTimeBetweenAnotherAircraftLanding = gapTimeBetweenAnotherAircraftLanding;

	}

	/**
	 * Generate a landing schedule based on the aircrafts data using a genetic
	 * algorithm.
	 */
	public void scheduleAircraftsLandings() {

	}
}
