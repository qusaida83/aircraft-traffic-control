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
		// list with parents copies for manipulation without mess up with parents data.
		List<Individual> parentsCopies = new LinkedList<Individual>();
		
		Individual parent1Copy1 = parents.getParent1().clone();
		Individual parent2Copy1 = parents.getParent2().clone();
		
		Individual parent1Copy2 = parents.getParent1().clone();
		Individual parent2Copy2 = parents.getParent2().clone();
		
		Individual parent1Copy3 = parents.getParent1().clone();
		Individual parent2Copy3 = parents.getParent2().clone();
		
		parentsCopies.add(parent1Copy1);
		parentsCopies.add(parent2Copy1);
		parentsCopies.add(parent1Copy2);
		parentsCopies.add(parent2Copy2);
		parentsCopies.add(parent1Copy3);
		parentsCopies.add(parent2Copy3);
		
		/*
		 * At this point we creates some differents schedulings for the copies in attempt to create better solutions.
		 * It is used a scheduling by target times (this is good for better cases solutions), and also,
		 * is used a random scheduling to add disturbance at the landing times of some copies.
		 */
		scheduler.scheduleTargetTimesFromBegin(parent1Copy1.getAircraftLandingSequence());
		scheduler.scheduleTargetTimesFromBegin(parent2Copy1.getAircraftLandingSequence());
		
		scheduler.scheduleRandomTimesFromBegin(parent1Copy2.getAircraftLandingSequence());
		scheduler.scheduleRandomTimesFromBegin(parent2Copy2.getAircraftLandingSequence());
		scheduler.scheduleRandomTimesFromBegin(parent1Copy3.getAircraftLandingSequence());
		scheduler.scheduleRandomTimesFromBegin(parent2Copy3.getAircraftLandingSequence());
		
		// updates the fitness values of the parents copies.
		int parent1CopyFitnessValue = fitnessEvaluator.evaluate(parent1Copy1.getAircraftLandingSequence());
		parent1Copy1.setFitnessValue(parent1CopyFitnessValue);
		
		int parent2CopyFitnessValue = fitnessEvaluator.evaluate(parent2Copy1.getAircraftLandingSequence());
		parent2Copy1.setFitnessValue(parent2CopyFitnessValue);
		
		int parent1Copy2FitnessValue = fitnessEvaluator.evaluate(parent1Copy2.getAircraftLandingSequence());
		parent1Copy2.setFitnessValue(parent1Copy2FitnessValue);
		
		int parent2Copy2FitnessValue = fitnessEvaluator.evaluate(parent2Copy2.getAircraftLandingSequence());
		parent2Copy2.setFitnessValue(parent2Copy2FitnessValue);
		
		int parent1Copy3FitnessValue = fitnessEvaluator.evaluate(parent1Copy3.getAircraftLandingSequence());
		parent1Copy3.setFitnessValue(parent1Copy3FitnessValue);
		
		int parent2Copy3FitnessValue = fitnessEvaluator.evaluate(parent2Copy3.getAircraftLandingSequence());
		parent2Copy3.setFitnessValue(parent2Copy3FitnessValue);
		
		// put the most adapted copy in front of the list.
		Collections.sort(parentsCopies);
		
		// return the most adapted copy as result of the crossover.
		return parentsCopies.get(0);
	}
}
