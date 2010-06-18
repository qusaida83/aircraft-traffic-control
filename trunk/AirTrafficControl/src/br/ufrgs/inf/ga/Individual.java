package br.ufrgs.inf.ga;

import java.util.Arrays;

import br.ufrgs.inf.atc.model.Aircraft;

/**
 * Class that encapsulates all information related to an individual of the population.
 * 
 * <p>
 * An individual represents a solution for the genetic algorithm problem and
 * this solution is represented (in this case) by the {@link Individual#aircraftLandingSequence} vector.
 * For each individual, there is a fitness value that tell us how adapted is this
 * individual in the population, that is, how good is this solution for the genetic problem.
 * </P>
 * 
 * @author diego
 *
 */
public class Individual implements Comparable<Individual> {
	
	/**
	 *  Represents a chromosome for the genetic algorithm. 
	 */
	private final Aircraft[] aircraftLandingSequence;
	
	/**
	 *  fitness value (adaptation factor for this individual in the population).
	 */
	private final int fitnessValue;
	
	/**
	 * Initializes the individual data.
	 * 
	 * @param aircraftLandingSequence Sequence of landings that represents a solution (valid or not) for the genetic problem.
	 * @param fitnessValue fitness value that tell us how good this solution is for the ATC problem instance.
	 */
	public Individual(Aircraft[] aircraftLandingSequence, int fitnessValue) {
		this.aircraftLandingSequence = aircraftLandingSequence;
		this.fitnessValue = fitnessValue;
	}

	public Aircraft[] getAircraftLandingSequence() {
		return aircraftLandingSequence;
	}

	public int getFitnessValue() {
		return fitnessValue;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Individual)) {
			return false;
		}
		
		Individual thatIndividual = (Individual)obj;
		return this.getFitnessValue() == thatIndividual.getFitnessValue() &&
			   Arrays.equals(this.getAircraftLandingSequence(), thatIndividual.getAircraftLandingSequence()); 
	}
	
	@Override
	public String toString() {
		return "Fitness value: " + this.fitnessValue + ", Landing sequence: " + aircraftLandingSequence + "\n";
	}

	/**
	 * A Individual is better than other one if it's fitness value is lower than the other one.
	 * This method in a collections sorting creates an ASC ordered list.
	 */
	@Override
	public int compareTo(Individual o) {
		if (this.getFitnessValue() > o.getFitnessValue()) {
			return 1;
		} else if (this.getFitnessValue() < o.getFitnessValue()) {
			return -1;
		} else {
			return 0;
		}
	}
}
