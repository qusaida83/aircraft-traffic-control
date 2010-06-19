package br.ufrgs.inf.ga.model;

import java.util.Arrays;

import br.ufrgs.inf.atc.model.Aircraft;
import br.ufrgs.inf.ga.operators.CrossoverOperator;
import br.ufrgs.inf.ga.operators.MutationOperator;

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
	private int fitnessValue;
	
	/**
	 * Initializes the individual data.
	 * 
	 * @param aircraftLandingSequence Sequence of landings that represents a solution (valid or not) for the genetic problem.
	 * @param fitnessValue fitness value that tell us how good this solution is for the ATC problem instance.
	 */
	public Individual(final Aircraft[] aircraftLandingSequence, int fitnessValue) {
		this.aircraftLandingSequence = aircraftLandingSequence;
		this.fitnessValue = fitnessValue;
	}

	public Aircraft[] getAircraftLandingSequence() {
		return aircraftLandingSequence;
	}

	public int getFitnessValue() {
		return fitnessValue;
	}
	
	public void setFitnessValue(int fitnessValue) {
		this.fitnessValue = fitnessValue;
	}
	
	/**
	 * Verify if this individual is more adapted than <code>that</code> one.
	 * An individual is more adapted than another one if it fitness value is lower than the other one fitness value.
	 * 
	 * @param that individual
	 * @return true if this individual is more adapted than that one. false otherwise.
	 */
	public boolean isMoreAdaptedThan(Individual that) {
		if (this.compareTo(that) >= 0) {
			return false;
		}
		
		return true;
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
		return "Fitness value: " + this.fitnessValue + ", Landing sequence: " + Arrays.toString(aircraftLandingSequence) + "\n";
	}

	/**
	 * A Individual is better than other one if it's fitness value is lower than the other one.
	 * This method in a collections sorting creates an ASC ordered list.
	 */
	@Override
	public int compareTo(Individual that) {
		if (this.getFitnessValue() > that.getFitnessValue()) {
			return 1;
		} else if (this.getFitnessValue() < that.getFitnessValue()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public Individual clone() {
		return new Individual(getLandingSequenceCopy(), this.fitnessValue);
	}
	
	/**
	 * Creates a copy vector of the landing sequence.
	 * @return copy vector of the landing sequence.
	 */
	private Aircraft[] getLandingSequenceCopy() {
		Aircraft[] landingSequenceCopy = new Aircraft[aircraftLandingSequence.length];

		for (int i = 0; i < aircraftLandingSequence.length; i ++) {
			landingSequenceCopy[i] = aircraftLandingSequence[i].clone();
		}
		
		return landingSequenceCopy;
	}
	
	/**
	 * Sets for each aircraft in the landing sequence the best landing time (target time).
	 */
	public void setBestLandingTimeForEachAircraft() {
		for (Aircraft aircraft : aircraftLandingSequence) {
			aircraft.setBestLandingTime();
		}
	}
}
