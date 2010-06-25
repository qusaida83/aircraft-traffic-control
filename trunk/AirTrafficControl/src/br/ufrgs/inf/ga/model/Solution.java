package br.ufrgs.inf.ga.model;

import java.util.Arrays;

/**
 * Encapsulates a solution data.
 * 
 * @author diego
 *
 */
public class Solution {

	/**
	 * Final population in the algorithm execution.
	 */
	private final Population population;
	
	/**
	 * Best individual found trough the generations.
	 */
	private final Individual bestIndividualFound;
	
	/**
	 * Max number of generations.
	 */
	private final int maxGenerations;
	
	/**
	 * Number of created generations.
	 */
	private final int generationCount;
	
	/**
	 * Number of generations that no solution improvement was detected.
	 */
	private final int generationsWithoutImprovement;
	
	public Solution(Population population, Individual bestIndividualFound,
					int maxGenerations, int generationCount, int generationsWithoutImprovement) {
		
		this.population = population;
		this.bestIndividualFound = bestIndividualFound;
		this.maxGenerations = maxGenerations;
		this.generationCount = generationCount;
		this.generationsWithoutImprovement = generationsWithoutImprovement;
	}

	public Population getPopulation() {
		return population;
	}

	public Individual getBestIndividualFound() {
		return bestIndividualFound;
	}

	public int getMaxGenerations() {
		return maxGenerations;
	}

	public int getGenerationCount() {
		return generationCount;
	}

	public int getGenerationsWithoutImprovement() {
		return generationsWithoutImprovement;
	}
	
	@Override
	public String toString() {
		return "\nSolution cost: " + this.getBestIndividualFound().getFitnessValue() +
			   "\nLanding sequence: " + Arrays.toString(this.getBestIndividualFound().getAircraftLandingSequence());
	}
}
