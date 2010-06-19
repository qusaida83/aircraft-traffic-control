package br.ufrgs.inf.atc;

import br.ufrgs.inf.atc.model.AircraftStaticData;
import br.ufrgs.inf.ga.GeneticAlgorithm;
import br.ufrgs.inf.ga.exceptions.AlgorithmException;
import br.ufrgs.inf.ga.model.Solution;


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
	 * @throws AlgorithmException 
	 */
	public Solution scheduleAircraftsLandings() throws AlgorithmException {
		GeneticAlgorithm ga = new GeneticAlgorithm(aircrafts);
		ga.execute();
	
		return ga.getSolution();
	}
}
