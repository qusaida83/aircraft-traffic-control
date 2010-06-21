package br.ufrgs.inf.ga;

import java.util.LinkedList;
import java.util.List;

import br.ufrgs.inf.ga.model.Individual;
import br.ufrgs.inf.ga.model.IndividualCreator;
import br.ufrgs.inf.ga.model.Population;

/**
 * Provides a set of {@link Population} initializers.
 * 
 * <p>
 * This class, basically, initializes a genetic algorithm population and
 * calculates the initial fitness for each individual in the population. 
 * </p>
 * @author diego
 *
 */
public class PopulationInitializer {
	
	/**
	 * Provides methods to create individual with different characteristics.
	 */
	private final IndividualCreator individualCreator;
	
	/**
	 * Resolve the class dependencies.
	 * 
	 * @param landingSequenceCreator Provides a set of landing sequence creator methods. 
	 * @param aircraftsStaticData The aircrafts static data for a specific ATC problem instance loaded from the file.
	 * @param fitnessEvaluator used to evaluate the adaptedness of an individual
	 */
	public PopulationInitializer(final IndividualCreator individualCreator) {
		this.individualCreator = individualCreator;
	}
	
	/**
	 * initializes the population and make the first fitness evaluation. 
	 */
	public Population createPopulation() {
		List<Individual> individuals = new LinkedList<Individual>();
		
		// Add an individual where it's landing sequence is sorted by the aircrafts target time.
		// If every aircraft lands at it's target time, it would be the best case, where the cost is zero.
		// So, this sequence is a good one to add in the population always!
		individuals.add(individualCreator.createIndividualSortedByTargetTimes());
		
		// Add an individual where it's landing sequence was sorted by the aircrafts penalty cost for landing after target time.
		// This way, we try to assign a optimal landing time for those aircrafts with the higher penalty costs... doing this,
		// we ensure many as possible optimal landing times for aircrafts with higher penalty costs.
		individuals.add(individualCreator.createIndividualSortedByPenaltyCost());
		
		// Generates randomly the rest of the population.
		for (int i = individuals.size(); i < Population.MAX_INDIVIDUALS; i+=4) {
			// Creates individual with a random landing sequence and times scheduled as close as possible to the target time.
			individuals.add(individualCreator.createRandomIndividual());
			// Creates individual where its landing sequence is sorted by its landing time that was randomly generated. 
			individuals.add(individualCreator.createIndividualSortedByRandomLandingSequence());
			// Creates individual with the characteristic that its landing sequence aircrafts have landing times equals to the earliest landing time.
			individuals.add(individualCreator.createIndividualSortedByEarliestTimes());
			// Creates individual with the characteristic that its landing sequence aircrafts have landing times equals to the latest landing time.
			individuals.add(individualCreator.createIndividualSortedByLatestTimes());
		}
		
		return new Population(individuals);
	}

	
}
