package br.ufrgs.inf.ga;

import br.ufrgs.inf.ga.exceptions.AlgorithmException;
import br.ufrgs.inf.ga.model.Individual;
import br.ufrgs.inf.ga.model.Population;
import br.ufrgs.inf.ga.operators.SelectionOperator;

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
	private final FitnessEvaluator fitnessCalculator;
	
	/**
	 * Operator used to select parents to a reproduction process.
	 */
	private final SelectionOperator selectionOperator;
	
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
	public GeneticAlgorithm(PopulationInitializer populationInitializer, FitnessEvaluator fitnessCalculator) {
		this.populationInitializer = populationInitializer;
		this.fitnessCalculator = fitnessCalculator;
		this.selectionOperator = new SelectionOperator();
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
	
	/**
	 * Verify if the best individual in the current generation is better than the global one found so far.
	 */
	private void findTheBestIndividualInCurrentGeneration() {
		// The best individual found in this population.
		Individual generationBestIndividual = population.getTheMostAdaptedIndividual();
		
		// if the best individual in this generation is more adapted than the global best individual so far,
		// than, the best individual in this generation becomes the global best individual.
		if (this.bestIndividual == null) {
			this.bestIndividual = generationBestIndividual;
		} else if (!this.bestIndividual.isMoreAdaptedThan(generationBestIndividual)) {
			this.bestIndividual = generationBestIndividual;
		}
		
	}

	/**
	 * initializes the population and make the first fitness evaluation. 
	 */
	protected void initializePopulation() {
		this.population = populationInitializer.createPopulation();
		//System.out.println(population.getTheMostAdaptedIndividual());
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
