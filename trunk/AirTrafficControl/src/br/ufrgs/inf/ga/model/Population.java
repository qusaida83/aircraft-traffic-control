package br.ufrgs.inf.ga.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * Implements a Genetic Algorithm Population.
 * 
 * <p>
 * Encapsulates all population data used in the genetic algorithm.
 * </p>
 * 
 * @author diego
 */
public class Population implements Iterable<Individual> {

	/**
	 * Max number of individuals in the population.
	 */
	public static final int MAX_INDIVIDUALS = 400;
	
	/**
	 * Reproduction rate for this population.
	 */
	public static final float REPRODUCTION_RATE = 0.4f;
	
	/**
	 * Mutation rate for this population.
	 */
	public static final float MUTATION_RATE = 0.5f;
	
	/**
	 * Set of individuals of the population.
	 */
	private final List<Individual> individuals;
	
	/**
	 * Flag that tell us if the population is sorted by individual fitness value.
	 * This is good to avoid unnecessary population sorting.
	 */
	private boolean sorted = false;
	
	/**
	 * Resolves the class dependencies.
	 * 
	 * @param individuals Set of individuals in the population.
	 */
	public Population(List<Individual> individuals) {
		this.individuals = individuals;
	}
	
	/**
	 * Adds an individual in the population.
	 * @param individual to be added.
	 */
	public void add(Individual individual) {
		this.individuals.add(individual);
		this.sorted = false;
	}
	
	/**
	 * Return the most adapted individual in the population.
	 * In this implementation, a individual with lower fitness value is more adapted than other one with a higher fitness value. 
	 * @return
	 */
	public Individual getMostAdaptedIndividual() {
		this.sortByFitness();
		// the first individual in the sorted list have the lower fitness value,
		// and this is the best one, once we trying to minimize the cost of sequence landings.
		return individuals.get(0);
	}
	
	/**
	 * Retrieve the worse individual in population.
	 * @return the less adapted individual in population.
	 */
	public Individual getLessAdaptedIndividual() {
		this.sortByFitness();
		return individuals.get(individuals.size() - 1);
	}
	
	/**
	 * Removes an individual from the population.
	 * @param individual to be removed.
	 */
	public void remove(Individual individual) {
		this.individuals.remove(individual);
	}

	@Override
	public Iterator<Individual> iterator() {
		return individuals.iterator();
	}
	
	/**
	 * sorts the population by individual fitness value.
	 */
	public void sortByFitness() {
		if (!this.sorted) {
			Collections.sort(this.individuals);
		}
	}
	
	/**
	 * Gets an individual at a position in a population list.
	 * @param individualIndex individual position in the population.
	 * @return an individual at position <code>individualIndex</code>
	 */
	public Individual get(int individualIndex) {
		return individuals.get(individualIndex);
	}
	
	/**
	 * Replace this an individual by one with the worse fitness value in population.
	 * The replacement is only done if the worse individual in population is either less
	 * adapted than the individual to be added in population.
	 * 
	 * @param individual individual to be added in population.
	 */
	public void replaceWithLessAdaptedIndividual(Individual individual) {
		Individual worsePopulationIndividual = getLessAdaptedIndividual();
		if (individual.isMoreAdaptedThan(worsePopulationIndividual)) {
			this.remove(worsePopulationIndividual);
			this.add(individual);
		}
	}
	
	/**
	 * Replaces an individual by other one in population.
	 * 
	 * @param newIndividual the individual to be added in population.
	 * @param individualToBeRemoved the individual to be removed from population.
	 */
	public void replace(Individual newIndividual, Individual individualToBeRemoved) {
		this.remove(individualToBeRemoved);
		this.add(newIndividual);
	}
}
