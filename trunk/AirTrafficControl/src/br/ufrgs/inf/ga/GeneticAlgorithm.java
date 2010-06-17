package br.ufrgs.inf.ga;

import br.ufrgs.inf.ga.exceptions.AlgorithmException;

/**
 * Genetic Algorithm Class.
 * 
 * <p>
 * This class implements a genetic algorithm meta heuristic to solve the
 * Air Traffic Control Problem. Solve means, find the best solution based in
 * an evaluation function in a finite number of iterations.
 * </p>
 * 
 * @author diego
 *
 */
public class GeneticAlgorithm {
	
	/**
	 * Max number of generations that the algorithm will run until a valid solution is find.
	 */
	public static final int MAX_GENERATION = 900;
	
	/**
	 * Responsable for initialize the population object.
	 * This class provide a set of initializers methods.
	 */
	private final PopulationInitializer populationInitializer;
	
	/**
	 * Calculator for the fitness value of an individual in the population.
	 */
	private final FitnessCalculator fitnessCalculator;
	
	/**
	 * Population where each individual represents a solution for the problem that is been solved.
	 */
	private Population population;
	
	/**
	 * The best individual found at the end of the algorithm execution.
	 */
	private Individual bestIndividual;
	
	/**
	 * Resolves the dependencies of this class.
	 * 
	 * @param populationInitializer population initializer instance.
	 * @param fitnessCalculator fitness calculator instance.
	 */
	public GeneticAlgorithm(PopulationInitializer populationInitializer, FitnessCalculator fitnessCalculator) {
		this.populationInitializer = populationInitializer;
		this.fitnessCalculator = fitnessCalculator;
	}
	
	/**
	 * Executes the algorithm.
	 * @throws AlgorithmException thrown if anything bad happens.
	 */
	public void execute() throws AlgorithmException {
		try {
			initializePopulation();
			
			// Initial generation value
			int generation = 1;
			
			// The algorithm stop condition
			while(!solutionFound() && generation < MAX_GENERATION) {				
				findTheBestIndividualInCurrentGeneration();
				selectParentsForReproduction();
				reproduct();
				mutate();
				fitnessCalculation();
				
				// next generation...
				generation++;
			}
		} catch (Exception e) {
			throw new AlgorithmException("An exception has occured when the algorithm was running.", e);
		}
	}
	
	private void findTheBestIndividualInCurrentGeneration() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}

	/**
	 * initializes the population and make the first fitness evaluation. 
	 */
	protected void initializePopulation() {
		this.population = populationInitializer.createPopulation();
	}
	
	protected void mutate() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}

	protected void reproduct() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}

	protected void selectParentsForReproduction() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}

	protected void fitnessCalculation() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}
	
	/**
	 * Verify the stop condition of the algorithm.
	 * @return true if the stop condition is reached, false otherwise.
	 */
	protected boolean solutionFound() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}
}
