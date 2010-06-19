package br.ufrgs.inf.ga;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import br.ufrgs.inf.atc.model.Aircraft;
import br.ufrgs.inf.atc.model.AircraftStaticData;
import br.ufrgs.inf.ga.model.Individual;
import br.ufrgs.inf.ga.model.Population;
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
	private final FitnessEvaluator fitnessCalculator;
	
	/**
	 * Responsible for schedule a time for each aircraft in a landing sequence.
	 */
	private final LandingTimeScheduler scheduler;
	
	/**
	 * Resolve the class dependencies.
	 * 
	 * @param aircraftsStaticData The aircrafts static data for a specific ATC problem instance loaded from the file.
	 * @param fitnessEvaluator used to evaluate the adaptedness of an individual
	 */
	public PopulationInitializer(final AircraftStaticData[] aircraftsStaticData, final FitnessEvaluator fitnessEvaluator, final LandingTimeScheduler scheduler) {
		this.aircraftsStaticData = aircraftsStaticData;
		this.fitnessCalculator = fitnessEvaluator;
		this.scheduler = scheduler;
	}
	
	/**
	 * initializes the population and make the first fitness evaluation. 
	 */
	public Population createPopulation() {
		List<Individual> individuals = new LinkedList<Individual>();
		
		// Add an individual where it's landing sequence is sorted by the aircrafts target time.
		// If every aircraft lands at it's target time, it would be the best case, where the cost is zero.
		// So, this sequence is a good one to add in the population always!
		individuals.add(createIndividualSortedByTargetTimes());
		
		// Add an individual where it's landing sequence was sorted by the aircrafts penalty cost for landing after target time.
		// This way, we try to assign a optimal landing time for those aircrafts with the higher penalty costs... doing this,
		// we ensure many as possible optimal landing times for aircrafts with higher penalty costs.
		individuals.add(createIndividualSortedByPenaltyCost());
		
		// Generates randomly the rest of the population.
		for (int i = individuals.size(); i < Population.MAX_INDIVIDUALS; i++) {
			individuals.add(createRandomIndividual());
		}
		
		//System.out.println(individuals);
		
		return new Population(individuals);
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
	 * Creates an individual with a landing sequence that was ordered by an aircraft penalty cost for landing after the target time.
	 * 
	 * @return an individual
	 */
	private Individual createIndividualSortedByPenaltyCost() {
		Aircraft[] landingSequence = createLandingSequenceSortedByPenaltyCost();
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
		// Schedules the landing times for each aircraft in the landing sequence.
		scheduler.scheduleRandomTimesFromBegin(aircraftLandingSequence);
		
		// At this point, the aircreftLandingSequence vector still ordered by the aircrafts landing time parameter. So, no sort is needed here!
		int fitnessValue = fitnessCalculator.evaluate(aircraftLandingSequence);
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
	 * Creates a landing sequence ordered by an aircraft penalty cost for landing after the target time.
	 * 
	 * @return landing sequence ordered by penalty costs.
	 */
	private Aircraft[] createLandingSequenceSortedByPenaltyCost() {
		// Creates a landing sequence where for each aircraft, it's landing time is close as possible to it's target time.
		Aircraft[] aircraftLandingSequence = createAircraftsWithDefaultLandingTime();

		// sort the list of aircraft by it's penalty cost for landing after target time.
		Arrays.sort(aircraftLandingSequence, new Comparator<Aircraft>() {

			@Override
			public int compare(Aircraft aircraft1, Aircraft aircraft2) {
				if (aircraft1.getLandingAfterTargetTimePenaltyCost() < aircraft2.getLandingAfterTargetTimePenaltyCost()) {
					return 1;
				} else if (aircraft1.getLandingAfterTargetTimePenaltyCost() > aircraft2.getLandingAfterTargetTimePenaltyCost()) {
					return -1;
				} else {
					return 0;
				}
			}
			
		});

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
