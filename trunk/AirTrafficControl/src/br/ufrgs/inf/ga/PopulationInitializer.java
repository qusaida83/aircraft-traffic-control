package br.ufrgs.inf.ga;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import br.ufrgs.inf.atc.model.Aircraft;
import br.ufrgs.inf.atc.model.AircraftStaticData;
import br.ufrgs.inf.ga.utils.ShuffleHelper;

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
	 * The aircrafts static data for a specific ATC problem instance loaded from the file.
	 */
	private final AircraftStaticData[] aircraftsStaticData;
	
	/**
	 * Fitness calculator to evaluate the adeptness of a generated individual of the population.
	 */
	private final FitnessCalculator fitnessCalculator;
	
	/**
	 * Resolve the class dependencies.
	 * 
	 * @param aircraftsStaticData The aircrafts static data for a specific ATC problem instance loaded from the file.
	 */
	public PopulationInitializer(final AircraftStaticData[] aircraftsStaticData, final FitnessCalculator fitnessCalculator) {
		this.aircraftsStaticData = aircraftsStaticData;
		this.fitnessCalculator = fitnessCalculator;
	}
	
	/**
	 * initializes the population and make the first fitness evaluation. 
	 */
	public Population<Individual> createPopulation() {
		Set<Individual> individuals = new HashSet<Individual>();
		
		// Add an individual where it's landing sequence is sorted by the aircrafts target time.
		// If every aircraft lands at it's target time, it would be the best case, where the cost is zero.
		// So, this sequence is a good one to add in the population always!
		individuals.add(createIndividualSortedByTargetTimes());
		
		// Generates randomly the rest of the population.
		for (int i = 1; i < Population.MAX_INDIVIDUALS; i++) {
			individuals.add(createRandomIndividual());
		}
		
		return new Population<Individual>(individuals);
	}
	
	/**
	 * Creates a random population individual.
	 * 
	 * @return an individual.
	 */
	private Individual createRandomIndividual() {
		Aircraft[] landingSequence = createRandomLandingSequence();
		return createIndividualForLandingSequence(landingSequence);
	}
	
	/**
	 * Creates an individual where the landing sequence was sorted by the aircrafts target times.
	 * 
	 * @return an individual.
	 */
	private Individual createIndividualSortedByTargetTimes() {
		Aircraft[] landingSequence = createLandingSequenceSortedByTargetTimes();
		return createIndividualForLandingSequence(landingSequence);
	}
	
	/**
	 * Calculates the landing time for each aircraft in the landing sequence.
	 * 
	 * @param aircraftLandingSequence a landing sequence.
	 * @return a population individual (landing sequence and it fitness value).
	 */
	private Individual createIndividualForLandingSequence(final Aircraft[] aircraftLandingSequence) {		
		for (int i = 0; i < aircraftLandingSequence.length; i++) {
			// The first aircraft landing time can be it's target time once it don't have to wait for any other aircraft to landing first.
			// So, for any other aircraft landing after the first one, we need to ensure that the time of landing is, at least,
			// the time of the previous landing plus the gap time needed to landing.
			if (i != 0) {
				Aircraft previousAircraft = aircraftLandingSequence[i - 1];
				Aircraft currentAircraft = aircraftLandingSequence[i];
				if (!currentAircraft.respectsTheGapTimeBetween(previousAircraft)) {
					// If the current aircraft landing time does not respects the restriction, then, this current landing time will be
					// the previous landing time plus the gap time needed to landing.
					currentAircraft.setLandingTime(previousAircraft.getLandingTime() + currentAircraft.getGapTimeBetween(previousAircraft));
				}
			}	
		}
		
		// At this point, the aircreftLandingSequence vector still ordered by the aircrafts landing time parameter. So, no sort is needed here!
		int fitnessValue = fitnessCalculator.calculate(aircraftLandingSequence);
		return new Individual(aircraftLandingSequence, fitnessValue);
	}
	
	/**
	 * Creates an aircraft landing sequence where for each aircraft, it's landing time is
	 * close as possible to it's target time.
	 *
	 * @return an aircraft landing sequence.
	 */
	private Aircraft[] createLandingSequenceSortedByTargetTimes() {
		// Creates a landing sequence where for each aircraft, it's landing time is close as possible to it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithDefaultLandingTime();
		// sort the list of aircraft by it's landing time to generate the landing sequence.
		Arrays.sort(aircraftLandingSequence);

		return aircraftLandingSequence;
	}
	
	/**
	 * Creates a random landing sequence.
	 * 
	 * @return a random landing sequence.
	 */
	private Aircraft[] createRandomLandingSequence() {
		// Creates a list of aircrafts where each aircraft landing time is it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithDefaultLandingTime();
		ShuffleHelper.shuffle(aircraftLandingSequence);
		
		return aircraftLandingSequence;
	}
	
	/**
	 * Creates a vector of aircrafts, where each aircraft landing time is, by default, equals to it's target time.
	 * 
	 * @return Aircraft landing sequence.
	 */
	private Aircraft[] createAircraftsWithDefaultLandingTime() {
		Aircraft[] aircrafts = new Aircraft[aircraftsStaticData.length];
		
		for (int i = 0; i < aircraftsStaticData.length; i++) {
			Aircraft newAircraft = new Aircraft(aircraftsStaticData[i]);
			aircrafts[i] = newAircraft;
		}
		
		return aircrafts;
	}
}
