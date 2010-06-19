package br.ufrgs.inf.ga;

import java.util.List;

import br.ufrgs.inf.atc.model.AircraftStaticData;
import br.ufrgs.inf.ga.exceptions.AlgorithmException;
import br.ufrgs.inf.ga.model.Individual;
import br.ufrgs.inf.ga.model.Parents;
import br.ufrgs.inf.ga.model.Population;
import br.ufrgs.inf.ga.model.Solution;
import br.ufrgs.inf.ga.operators.CrossoverOperator;
import br.ufrgs.inf.ga.operators.MutationOperator;
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
	public static final int MAX_GENERATION = 1000;
	
	/**
	 * Responsable for initialize the population object.
	 * This class provide a set of initializers methods.
	 */
	private final PopulationInitializer populationInitializer;
	
	/**this.selectionOperator = new SelectionOperator();
	 * Calculator for the fitness value of an individual in the population.
	 */
	private final FitnessEvaluator fitnessEvaluator;
	
	/**
	 * Operator used to select parents to a reproduction process.
	 */
	private final SelectionOperator selectionOperator = new SelectionOperator();
	
	/**
	 * Executes the crossover operator in a reproduction process.
	 */
	private final CrossoverOperator crossoverOperator;
	
	/**
	 * Executes a mutation operation.
	 */
	private final MutationOperator mutationOperator;
	
	/**
	 * Population where each individual represents a solution for the problem that is been solved.
	 */
	private Population population;
	
	/**
	 * Parents selected by the {@link SelectionOperator} for reproduction.
	 */
	private List<Parents> selectedParents;
	
	/**
	 * The best individual found at the end of the algorithm execution.
	 */
	private Individual bestIndividual;
	
	/**
	 * Counts how many generations that none best solution is found.
	 */
	private int generationsWithoutImprovement = 0;
	
	/**
	 * Algorithm solution.
	 */
	private Solution solution;
	
	/**
	 * Initializes the dependencies.
	 * @param aircrafts aircrafts data loaded from an input file used to create the population for the algorithm.
	 */
	public GeneticAlgorithm(AircraftStaticData[] aircrafts) {
		LandingTimeScheduler scheduler = new LandingTimeScheduler();
		this.fitnessEvaluator = new FitnessEvaluator();
		this.populationInitializer = new PopulationInitializer(aircrafts, fitnessEvaluator, scheduler);
		this.crossoverOperator = new CrossoverOperator(scheduler, fitnessEvaluator);
		this.mutationOperator = new MutationOperator(scheduler, fitnessEvaluator);
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
				//System.out.println("### generation " + generation);
				
				// swap the global best individual so far for the best individual of the current
				// generation, if this last one is better!
				findTheBestIndividualInCurrentGeneration();
				
				// generating the next generation
				selectParentsForReproduction();
				reproduct();
				mutate();
				
				// next generation...
				generation++;
			}
			
			this.solution = new Solution(population, bestIndividual, MAX_GENERATION, generation, generationsWithoutImprovement);

		} catch (Exception e) {
			throw new AlgorithmException("An exception has occured when the algorithm was running.", e);
		}
	}
	
	/**
	 * Verify if the best individual in the current generation is better than the global one found so far.
	 */
	private void findTheBestIndividualInCurrentGeneration() {
		// The best individual found in this population.
		Individual generationBestIndividual = population.getMostAdaptedIndividual();
		//System.out.println("#### generation best individual: " + generationBestIndividual);
		//System.out.println("#### global best individual: " + bestIndividual);
		
		// if the best individual in this generation is more adapted than the global best individual so far,
		// than, the best individual in this generation becomes the global best individual.
		if (this.bestIndividual == null) {
			this.bestIndividual = generationBestIndividual;
		} else if (!this.bestIndividual.isMoreAdaptedThan(generationBestIndividual)) {
			this.bestIndividual = generationBestIndividual;
			generationsWithoutImprovement = 0;
		} else {
			// counts how many generations none global best solution is found.
			generationsWithoutImprovement++;
		}
		
	}

	/**
	 * initializes the population and make the first fitness evaluation. 
	 */
	protected void initializePopulation() {
		this.population = populationInitializer.createPopulation();
	}
	
	/**
	 * Selects a list of parents to reproduction and generation of new individuals.
	 */
	protected void selectParentsForReproduction() {
		this.selectedParents = selectionOperator.selectParents(population);
	}

	/**
	 * Execute the reproduction operation.
	 * 
	 * This process generates a new individual for each parents reproduction.
	 * This son can join the population just if he is more adapted than one of his parents.
	 * If true, the less adapted parent is replaced by his son.
	 */
	protected void reproduct() {
		for (Parents parents : selectedParents) {
			Individual son = crossoverOperator.execute(parents);
			Individual lessAdaptedParent = parents.getLessAdaptedParent();
			if (son.isMoreAdaptedThan(lessAdaptedParent)) {
				population.replace(son, lessAdaptedParent);
			}
		}
	}
	
	/**
	 * Mutates a percentage of bad individuals in attempt to generate better solutions.
	 * This is good for escaping from minimal local solutions.
	 */
	protected void mutate() {
		int n = (int)(Population.MAX_INDIVIDUALS * Population.MUTATION_RATE);
		int mutationStartIndex = (int)(Population.MAX_INDIVIDUALS * 0.5f);
		population.sortByFitness();
		
		// Executes n randomly mutations in individuals starting from the middle of the sorted population to the end.
		for (int i = 0; i < n; i++) {
			// generates a random index between mutationStartIndex and the size of the population.
			// TODO verify the correctness of this calculus
			int individualToBeMutatedIndex = mutationStartIndex + (int) (Math.random() * (Population.MAX_INDIVIDUALS - mutationStartIndex));
			Individual individualToBeMutated = population.get(individualToBeMutatedIndex);
			mutationOperator.execute(individualToBeMutated);
		}
	}
	
	/**
	 * Verify the stop condition of the algorithm.
	 * 
	 * <p>
	 * If has been a long time since the last solution improvement, probably no improvement at all will occur, so we choose to stop the algorithm. 
	 * </p>
	 * 
	 * @return true if the stop condition is reached, false otherwise.
	 */
	protected boolean solutionFound() {
		if (generationsWithoutImprovement > GeneticAlgorithm.MAX_GENERATION * 2/3) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the solution founded by the algorithm.
	 * @return the solution.
	 */
	public Solution getSolution() {
		return this.solution;
	}
}
