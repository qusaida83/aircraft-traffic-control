package br.ufrgs.inf.ga.operators;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.ufrgs.inf.ga.FitnessEvaluator;
import br.ufrgs.inf.ga.LandingTimeScheduler;
import br.ufrgs.inf.ga.model.Individual;
import br.ufrgs.inf.ga.model.Parents;

/**
 * Implements a genetic crossover operation.
 * 
 * @author diego
 *
 */
public class CrossoverOperator {
	
	/**
	 * Number of random copies produced, to choose one among these copies (the best one), to be a candidate for the spot of generated son.
	 */
	private static final int MAX_RANDOM_COPIES = 100;
	
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
	public Individual execute(final Parents parents) {
		
		Individual individual1 = createIndividualRondomlyCheduledFromBegin(parents);
		Individual individual2 = createIndividualCheduledByTargetTimes(parents);
		
		// return the most adapted copy as result of the crossover.
		return individual1.isMoreAdaptedThan(individual2) ? individual1 : individual2;
	}
	
	/**
	 * Creates a new individual where its landing times are scheduled randomly around the target time.
	 * @param parents parents used to generate a new solution.
	 * @return new individual
	 */
	private Individual createIndividualRondomlyCheduledFromBegin(final Parents parents) {
		// list with parents copies for manipulation without mess up with parents data.
		List<Individual> parentsCopies = new LinkedList<Individual>();
		
		for (int i = 0; i < MAX_RANDOM_COPIES/2; i++) {
			Individual parent1Copy1 = parents.getParent1().clone();
			Individual parent2Copy1 = parents.getParent2().clone();
			
			parentsCopies.add(parent1Copy1);
			parentsCopies.add(parent2Copy1);
			
			scheduler.scheduleRandomTimesFromBegin(parent1Copy1.getAircraftLandingSequence());
			scheduler.scheduleRandomTimesFromBegin(parent2Copy1.getAircraftLandingSequence());
			
			setNewFitnessValueTo(parent1Copy1);
			setNewFitnessValueTo(parent2Copy1);
		}
		// put the most adapted copy in front of the list.
		Collections.sort(parentsCopies);

		return parentsCopies.get(0);
	}
	
	/**
	 * Creates an individual where the landing sequence times are scheduled by target times.
	 * @param parents parents used to generate a new solution.
	 * @return new individual
	 */
	private Individual createIndividualCheduledByTargetTimes(final Parents parents) {
		// list with parents copies for manipulation without mess up with parents data.
		List<Individual> parentsCopies = new LinkedList<Individual>();
		
		for (int i = 0; i < MAX_RANDOM_COPIES/2; i++) {
			Individual parent1Copy1 = parents.getParent1().clone();
			Individual parent2Copy1 = parents.getParent2().clone();
			
			parentsCopies.add(parent1Copy1);
			parentsCopies.add(parent2Copy1);
			
			scheduler.scheduleTargetTimesFromBegin(parent1Copy1.getAircraftLandingSequence());
			scheduler.scheduleTargetTimesFromBegin(parent2Copy1.getAircraftLandingSequence());
			
			setNewFitnessValueTo(parent1Copy1);
			setNewFitnessValueTo(parent2Copy1);
		}
		// put the most adapted copy in front of the list.
		Collections.sort(parentsCopies);

		return parentsCopies.get(0);
	}
	
	/**
	 * Updates an individual fitness value.
	 * @param individual
	 */
	private void setNewFitnessValueTo(Individual individual) {
		int parent1CopyFitnessValue = fitnessEvaluator.evaluate(individual.getAircraftLandingSequence());
		individual.setFitnessValue(parent1CopyFitnessValue);
	}
}
