package br.ufrgs.inf.ga.model;

/**
 * Encapsulates Population Configuration Parameters.
 * @author diego
 *
 */
public class PopulationConfig {

	/**
	 * Max number of individuals in the population.
	 */
	public final int maxIndividuals;
	
	/**
	 * Reproduction rate for this population.
	 */
	public final float reproductionRate;
	
	/**
	 * Mutation rate for this population.
	 */
	public final float mutationRate;

	public PopulationConfig(int maxIndividuals, float reproductionRate, float mutationRate) {
		this.maxIndividuals = maxIndividuals;
		this.reproductionRate = reproductionRate;
		this.mutationRate = mutationRate;
	}

	public int getMaxIndividuals() {
		return maxIndividuals;
	}

	public float getReproductionRate() {
		return reproductionRate;
	}

	public float getMutationRate() {
		return mutationRate;
	}
}
