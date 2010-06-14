package br.ufrgs.inf.ga;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.ufrgs.inf.atc.model.Aircraft;

/**
 * Initializes a {@link Population) object
 * @author diego
 *
 */
public class PopulationInitializer {

	private final Aircraft[] aircrafts;
	
	
	public PopulationInitializer(Aircraft[] aircrafts) {
		this.aircrafts = aircrafts;
	}
	
	/**
	 * initializes the population and make the first fitness evaluation. 
	 */
	public void initializePopulation(Population<Individual> population, FitnessCalculator fitnessCalculator) {
		Set<Individual> individuals = new HashSet<Individual>();
		for (int i = 0; i < Population.MAX_INDIVIDUALS; i++) {
			individuals.add(createRandomIndividual(fitnessCalculator));
		}
		
		population = new Population<Individual>(individuals);
	}
	
	private Individual createRandomIndividual(FitnessCalculator fitnessCalculator) {
		List<Aircraft> aircraftLandingSequenceList = new LinkedList<Aircraft>();
		for (Aircraft aircraft : aircrafts) {
			Aircraft newAircraft;
			try {
				newAircraft = aircraft.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
			newAircraft.setLandingTime(getRandomLandingTime(newAircraft.getEarliestLandingTime(), newAircraft.getLatestLandingTime()));
			aircraftLandingSequenceList.add(newAircraft);
		}
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
	private int getRandomLandingTime(int earliestLandingTime, int latestLandingTime) {
		Random rand = new Random(System.currentTimeMillis());
		return earliestLandingTime + rand.nextInt(latestLandingTime - earliestLandingTime);
	}
}
