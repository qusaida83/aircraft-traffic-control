package br.ufrgs.inf.ga;

import java.util.Iterator;
import java.util.Set;

/**
 * Implements a Genetic Algorithm Population.
 * 
 * <p>
 * Encapsulates all population data used in the genetic algorithm.
 * </p>
 *
 * @param <T> Implementation Type of the individuals in the population.
 * 
 * @author diego
 */
public class Population<T> implements Iterable<T> {

	/**
	 * Max number of individuals in the population.
	 */
	public static final int MAX_INDIVIDUALS = 200;
	
	/**
	 * Set of individuals of the population.
	 */
	private final Set<T> individuals;
	
	/**
	 * Resolves the class dependencies.
	 * 
	 * @param individuals Set of individuals in the population.
	 */
	public Population(Set<T> individuals) {
		this.individuals = individuals;
	}
	
	/**
	 * Adds an individual in the population.
	 * @param individual to be added.
	 */
	public void add(T individual) {
		this.individuals.add(individual);
	}
	
	/**
	 * Removes an individual from the population.
	 * @param individual to be removed.
	 */
	public void remove(T individual) {
		this.individuals.remove(individual);
	}

	@Override
	public Iterator<T> iterator() {
		return individuals.iterator();
	}
}
