package br.ufrgs.inf.ga.operators;

import br.ufrgs.inf.ga.FitnessEvaluator;
import br.ufrgs.inf.ga.LandingTimeScheduler;
import br.ufrgs.inf.ga.model.Individual;
import br.ufrgs.inf.ga.model.Parents;
import br.ufrgs.inf.ga.utils.ShuffleHelper;

/**
 * Implements a genetic crossover operation.
 * 
 * @author diego
 *
 */
public class CrossoverOperator {
	
	/**
	 * Schedules the landing time for each aircraft at a landing sequence.
	 */
	private final LandingTimeScheduler scheduler;
	
	/**
	 * Calculates the fitness value for the resulted individual from the crossover operation.
	 */
	private final FitnessEvaluator fitnessEvaluator;
	
	/**
	 * Resolves the dependencies.
	 * 
	 * @param scheduler Schedules the landing time for each aircraft at a landing sequence.
	 * @param fitnessEvaluator evaluate the landing sequence cost.
	 */
	public CrossoverOperator(final LandingTimeScheduler scheduler, final FitnessEvaluator fitnessEvaluator) {
		this.scheduler = scheduler;
		this.fitnessEvaluator = fitnessEvaluator;
	}

	/**
	 * Executes the crossover operation on the parents generating a son that is the result of
	 * a combination of his parents.
	 * 
	 * @param parents used to generate a new individual.
	 * @return the generated individual.
	 */
	public Individual execute(Parents parents) {
		Individual parent1Copy = parents.getParent1().clone();
		Individual parent2Copy = parents.getParent2().clone();
		
		ShuffleHelper.shuffle(parent1Copy.getAircraftLandingSequence());
		ShuffleHelper.shuffle(parent2Copy.getAircraftLandingSequence());
		
		// Creates the times schedules for each aircraft in a landing sequence.
		scheduler.schedule(parent1Copy.getAircraftLandingSequence());
		scheduler.schedule(parent2Copy.getAircraftLandingSequence());
		
		// updates the fitness values of the parents copies.
		int parent1CopyFitnessValue = fitnessEvaluator.evaluate(parent1Copy.getAircraftLandingSequence());
		parent1Copy.setFitnessValue(parent1CopyFitnessValue);
		
		int parent2CopyFitnessValue = fitnessEvaluator.evaluate(parent2Copy.getAircraftLandingSequence());
		parent2Copy.setFitnessValue(parent2CopyFitnessValue);
		
		// return the most adapted copy as result of the crossover.
		return parent1Copy.isMoreAdaptedThan(parent2Copy) ? parent1Copy : parent2Copy;
	}
}
