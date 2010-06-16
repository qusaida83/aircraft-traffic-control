package br.ufrgs.inf.ga;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.ufrgs.inf.atc.model.Aircraft;
import br.ufrgs.inf.atc.model.AircraftStaticData;

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
	 * Resolve the class dependencies.
	 * 
	 * @param aircraftsStaticData The aircrafts static data for a specific ATC problem instance loaded from the file.
	 */
	public PopulationInitializer(AircraftStaticData[] aircraftsStaticData) {
		this.aircraftsStaticData = aircraftsStaticData;
	}
	
	/**
	 * initializes the population and make the first fitness evaluation. 
	 */
	public Population<Individual> createPopulation(Population<Individual> population, FitnessCalculator fitnessCalculator) {
		Set<Individual> individuals = new HashSet<Individual>();
		// creates the initial population randomly
		for (int i = 0; i < Population.MAX_INDIVIDUALS; i++) {
			individuals.add(createRandomIndividual(fitnessCalculator));
		}
		
		return new Population<Individual>(individuals);
	}
	
	/**
	 * Creates a random individual and calculates it fitness value.
	 * 
	 * @param fitnessCalculator fitness value calculator.
	 * @return an individual.
	 */
	private Individual createRandomIndividual(FitnessCalculator fitnessCalculator) {
		List<Aircraft> aircraftLandingSequenceList = new LinkedList<Aircraft>();
		for (AircraftStaticData aircraftStaticData : aircraftsStaticData) {
			Aircraft newAircraft = new Aircraft(aircraftStaticData);
			newAircraft.setLandingTime(calculateRandomLandingTime(newAircraft.getEarliestLandingTime(), newAircraft.getLatestLandingTime()));
			aircraftLandingSequenceList.add(newAircraft);
		}
		
		// sort the list of aircraft by it's landing time
		Collections.sort(aircraftLandingSequenceList);
		
		Aircraft[] aircraftLandingSequence = aircraftLandingSequenceList.toArray(new Aircraft[aircraftLandingSequenceList.size()]);
		int fitnessValue = fitnessCalculator.calculate(aircraftLandingSequence);
		return new Individual(aircraftLandingSequence, fitnessValue);
	}
	
	/**
	 * Generates a random landing time between the earliest landing time and the latest landing time of an aircraft.
	 * @param earliestLandingTime
	 * @param latestLandingTime
	 * @return a valid random landing time.
	 */
	private int calculateRandomLandingTime(int earliestLandingTime, int latestLandingTime) {
		Random rand = new Random(System.currentTimeMillis());
		return earliestLandingTime + rand.nextInt(latestLandingTime - earliestLandingTime);
	}
}
