package br.ufrgs.inf.atc;

import br.ufrgs.inf.atc.model.AircraftStaticData;
import br.ufrgs.inf.ga.GeneticAlgorithm;
import br.ufrgs.inf.ga.exceptions.AlgorithmException;
import br.ufrgs.inf.ga.model.PopulationConfig;
import br.ufrgs.inf.ga.model.Solution;


/**
 * This class implements the air traffic control system.
 * 
 * @author diego
 * 
 */
public class AirTrafficControl {
	
	/**
	 * 
	 */
	private GeneticAlgorithm geneticAlgorithm;

	/**
	 * Constructs the Air traffic control core.
	 * 
	 * @param geneticAlgorithm
	 */
	public AirTrafficControl(GeneticAlgorithm geneticAlgorithm) {
		this.geneticAlgorithm = geneticAlgorithm;
	}

	/**
	 * Generate a landing schedule based on the aircrafts data using a genetic
	 * algorithm.
	 * @throws AlgorithmException 
	 */
	public Solution scheduleAircraftsLandings() throws AlgorithmException {
		
		
		long startTime = System.currentTimeMillis();
		geneticAlgorithm.execute();
		long endTime = System.currentTimeMillis();
		float executionTime = (endTime - startTime) * 0.001f;
		
		System.out.println("Tempo em seg: " + executionTime);
	
		return geneticAlgorithm.getSolution();
	}
}
