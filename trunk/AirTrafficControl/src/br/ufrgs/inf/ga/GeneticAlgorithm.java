package br.ufrgs.inf.ga;

import br.ufrgs.inf.ga.exceptions.AlgorithmException;

public class GeneticAlgorithm {
	
	/**
	 * Max number of generations that the algorithm will run until a valid solution is find.
	 */
	public static final int MAX_GENERATION = 900;
	
	/**
	 * Population where each individual represents a solution for the problem that is been solved.
	 */
	private Population<Individual> population;
	
	private PopulationInitializer populationInitializer;
	
	private FitnessCalculator fitnessCalculator;
	
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
				fitnessCalculation();
				selectParentsForReproduction();
				reproduct();
				mutate();
				
				// next generation...
				generation++;
			}
		} catch (Exception e) {
			throw new AlgorithmException("An exception has occured when the algorithm was running.", e);
		}
	}
	
	/**
	 * initializes the population and make the first fitness evaluation. 
	 */
	public void initializePopulation() {
		populationInitializer.initializePopulation(population, fitnessCalculator);
	}
	
	private void mutate() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}

	private void reproduct() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}

	private void selectParentsForReproduction() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}

	private void fitnessCalculation() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}
	
	/**
	 * Verify the stop condition of the algorithm.
	 * @return true if the stop condition is reached, false otherwise.
	 */
	public boolean solutionFound() {
		// TODO implement...
		throw new RuntimeException("Not implemented!");
	}
}
