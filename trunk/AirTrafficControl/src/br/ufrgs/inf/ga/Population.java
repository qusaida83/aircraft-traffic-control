package br.ufrgs.inf.ga;

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
	public static final int MAX_INDIVIDUALS = 1000;
	
	/**
	 * Set of individuals of the population.
	 */
	private final List<Individual> individuals;
	
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
	}
	
	/**
	 * Return the most adapted individual in the population.
	 * In this implementation, a individual with lower fitness value is more adapted than other one with a higher fitness value. 
	 * @return
	 */
	public Individual getTheMostAdaptedIndividual() {
		Collections.sort(individuals);
		// the first individual in the sorted list have the lower fitness value,
		// and this is the best one, once we trying to minimize the cost of sequence landings.
		return individuals.get(0);
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
}
