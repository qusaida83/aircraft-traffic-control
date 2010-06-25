package br.ufrgs.inf.ga.operators;

import br.ufrgs.inf.ga.FitnessEvaluator;
import br.ufrgs.inf.ga.LandingTimeScheduler;
import br.ufrgs.inf.ga.model.Individual;
import br.ufrgs.inf.ga.utils.ShuffleHelper;

/**
 * Implements a genetic mutation operation.
 * 
 * @author diego
 *
 */
public class MutationOperator {

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
	public MutationOperator(final LandingTimeScheduler scheduler, final FitnessEvaluator fitnessEvaluator) {
		this.scheduler = scheduler;
		this.fitnessEvaluator = fitnessEvaluator;
	}
	
	/**
	 * Executes the mutation operation on the individual, increasing the variance in the population.
	 * 
	 * @param parents used to generate a new individual.
	 * @return the generated individual.
	 */
	public void execute(final Individual individual) {
	
		// Shuffle the landing sequence of the individual.
		ShuffleHelper.shuffle(individual.getAircraftLandingSequence());
		
		scheduler.scheduleRandomTimesFromBegin(individual.getAircraftLandingSequence());
		int fitnessValue = fitnessEvaluator.evaluate(individual.getAircraftLandingSequence());
		individual.setFitnessValue(fitnessValue);
	}
}
