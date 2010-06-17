package br.ufrgs.inf.ga;

import java.util.Arrays;
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
	public Population<Individual> createPopulation(final FitnessCalculator fitnessCalculator) {
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
	private Individual createRandomIndividual(final FitnessCalculator fitnessCalculator) {
		// the chromosome of the individual.
		Aircraft[] aircraftLandingSequence = new Aircraft[aircraftsStaticData.length];
		
		
		for (int i = 0; i < aircraftsStaticData.length; i++) {
			Aircraft newAircraft = new Aircraft(aircraftsStaticData[i]);
			aircraftLandingSequence[i] = newAircraft;
		}
		// sort the list of aircraft by it's landing time
		Arrays.sort(aircraftLandingSequence);
		
		for (int i = 0; i < aircraftLandingSequence.length; i++) {
			
				if (i > 0) {
					

				} else {

				}
			
		}
		
		
		int fitnessValue = fitnessCalculator.calculate(aircraftLandingSequence);
		return new Individual(aircraftLandingSequence, fitnessValue);
	}
	
	/**
	 * Generates a random landing time between the earliest landing time and the latest landing time of an aircraft.
	 * 
	 * @param earliestLandingTime
	 * @param latestLandingTime
	 * @return a valid random landing time.
	 */
	private int calculateLandingTimeNearToTargetTime(Aircraft aircraft) {
		for (AircraftStaticData staticData : aircraftsStaticData) {
			
		}
		
		return 0;
	}
}
