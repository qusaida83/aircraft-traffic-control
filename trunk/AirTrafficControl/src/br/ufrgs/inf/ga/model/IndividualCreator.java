package br.ufrgs.inf.ga.model;

import br.ufrgs.inf.atc.model.Aircraft;
import br.ufrgs.inf.atc.model.LandingSequenceCreator;
import br.ufrgs.inf.ga.FitnessEvaluator;
import br.ufrgs.inf.ga.LandingTimeScheduler;

/**
 * Creates different individuals using the {@link LandingSequenceCreator}.
 * 
 * @author diego
 *
 */
public class IndividualCreator {

	/**
	 * Responsible for schedule a time for each aircraft in a landing sequence.
	 */
	private final LandingTimeScheduler scheduler;
	
	/**
	 * Provides a set of landing sequence creator methods.
	 */
	private final LandingSequenceCreator landingSequenceCreator;
	
	/**
	 * Fitness calculator to evaluate the adeptness of a generated individual of the population.
	 */
	private final FitnessEvaluator fitnessEvaluator;
	
	public IndividualCreator(LandingSequenceCreator landingSequenceCreator, FitnessEvaluator fitnessEvaluator, LandingTimeScheduler scheduler) {
		this.landingSequenceCreator = landingSequenceCreator;
		this.fitnessEvaluator = fitnessEvaluator;
		this.scheduler = scheduler;
	}
	
	/**
	 * Creates an individual with a random landing sequence and times scheduled as close as possible to the target time.
	 * 
	 * @return an individual.
	 */
	public Individual createRandomIndividual() {
		Aircraft[] landingSequence = landingSequenceCreator.createRandomLandingSequence();
		return createIndividualForLandingSequence(landingSequence);
	}
	
	/**
	 * Creates an individual with a landing sequence that was ordered by the landing times of the aircrafts (generated randomly).
	 * @return
	 */
	public Individual createIndividualSortedByRandomLandingSequence() {
		Aircraft[] landingSequence = landingSequenceCreator.createLandingSequenceSortedByRandomTimes();
		return createIndividualForLandingSequence(landingSequence);
	}
	
	/**
	 * Creates an individual with a landing sequence that was ordered by an aircraft penalty cost for landing after the target time.
	 * 
	 * @return an individual
	 */
	public Individual createIndividualSortedByPenaltyCost() {
		Aircraft[] landingSequence = landingSequenceCreator.createLandingSequenceSortedByPenaltyCost();
		return createIndividualForLandingSequence(landingSequence);
	}

	/**
	 * Creates an individual where the landing sequence was sorted by the aircrafts target times.
	 * 
	 * @return an individual.
	 */
	public Individual createIndividualSortedByTargetTimes() {
		Aircraft[] landingSequence = landingSequenceCreator.createLandingSequenceSortedByTargetTimes();
		return createIndividualForLandingSequence(landingSequence);
	}
	
	/**
	 * Creates an individual where the landing sequence was sorted by the aircrafts earliest times.
	 * 
	 * @return an individual.
	 */
	public Individual createIndividualSortedByEarliestTimes() {
		Aircraft[] landingSequence = landingSequenceCreator.createLandingSequenceWithEarliestLandingTimes();
		return createIndividualForLandingSequence(landingSequence);
	}
	
	/**
	 * Creates an individual where the landing sequence was sorted by the aircrafts latest times.
	 * 
	 * @return an individual.
	 */
	public Individual createIndividualSortedByLatestTimes() {
		Aircraft[] landingSequence = landingSequenceCreator.createLandingSequenceWithLatestLandingTimes();
		return createIndividualForLandingSequence(landingSequence);
	}
	
	/**
	 * Calculates the landing time for each aircraft in the landing sequence.
	 * 
	 * @param aircraftLandingSequence a landing sequence.
	 * @return a population individual (landing sequence and it fitness value).
	 */
	public Individual createIndividualForLandingSequence(final Aircraft[] aircraftLandingSequence) {		
		// Schedules the landing times for each aircraft in the landing sequence.
		scheduler.scheduleFromBegin(aircraftLandingSequence);
		
		// At this point, the aircreftLandingSequence vector still ordered by the aircrafts landing time parameter. So, no sort is needed here!
		int fitnessValue = fitnessEvaluator.evaluate(aircraftLandingSequence);
		return new Individual(aircraftLandingSequence, fitnessValue);
	}
}
