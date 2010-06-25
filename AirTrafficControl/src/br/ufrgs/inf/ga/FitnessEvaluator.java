package br.ufrgs.inf.ga;

import br.ufrgs.inf.atc.model.Aircraft;


/**
 * This class implements the individual fitness evaluation in the population.
 * 
 * <p>
 * This implementation is based on the punishment principle. Sub optimal solutions are punished and INVALID ones are hardly punished.
 * The objective of this implementation is find a landing sequence of aircrafts that has the minimal cost. Based on that,
 * our evaluation method implements the objective function that we wants to minimize, and than, punish the
 * solution in case it is not good enough or it is an invalid landing sequence (aircrafts scheduled to landing at the same time for example).
 * </p>
 * 
 * @author diego
 *
 */
public class FitnessEvaluator {
	
	/**
	 * Calculates the fitness of an individual.
	 *
	 * @param individual
	 * @return the cost of this landing (objective function). If it is an invalid landing sequence,
	 * 		   the result is tha maximum integer value.
	 */
	public int evaluate(Aircraft[] aircraftLandingSequence) {
		int fitnessValue = 0;
		
		for(int i = 0; i < aircraftLandingSequence.length; i++) {
			Aircraft aircraft = aircraftLandingSequence[i];
			
			/*
			 * Checkes the restriction xi ∈ [Ei , Li]
			 *	where xi = aircraft landing time
			 * 		  Ei = aircraft earliest landing time
			 *		  Li = aircraft latest landing time   
			 */
			if (!aircraft.landingTimeIsInLandingTimeWindow()) {
				// Invalid solution! The cost is realy high!
				return Integer.MAX_VALUE;
			}
			
			/*
			 * Checks the restriction xj ≥ xi + Sij
			 * where xj = the current aircraft landing time
			 * 		 xi = the last aircraft landing time (landed before xj)
			 * 		 Sij = the time that xj must wait after xi has been landed.
			 */
			if (i != 0) {
				Aircraft previousAircraft = aircraftLandingSequence[i - 1]; 
				if (!aircraft.respectsGapTimeBetween(previousAircraft)) {
					// Invalid solution! The cost is realy high!
					return Integer.MAX_VALUE;
				}
			}
			
			// if the landing sequence is a valid one, the fitness value will be, in the end, the cost of the landing sequence.
			// And we aim on the minimization of this cost!
			fitnessValue += aircraft.getLandingCost();
		}
		
		return fitnessValue;
	}
}
