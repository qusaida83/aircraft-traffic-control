package br.ufrgs.inf.ga;

import br.ufrgs.inf.atc.model.Aircraft;


/**
 * Helper class used to calculate the fitness of an individual.
 * 
 * @author diego
 *
 */
public class FitnessCalculator {
	
	/**
	 * Calculates the fitness of an individual.
	 * 
	 * <p>
	 * This calculus is based on the principle of punish the sub optimal solutions
	 * and punish harder the invalid solutions (like one where two aircrafts are scheduled to landing at the same time).
	 * </p>
	 * 
	 * @param individual
	 * @return fitness value.
	 */
	public int calculate(Aircraft[] aircraftLandingSequence) {
		// TODO
		return 0;
	}
}
