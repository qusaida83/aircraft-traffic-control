package br.ufrgs.inf;

import java.io.IOException;

import br.ufrgs.inf.atc.AirTrafficControl;
import br.ufrgs.inf.atc.AtcLoader;
import br.ufrgs.inf.atc.model.AircraftStaticData;
import br.ufrgs.inf.ga.GeneticAlgorithm;
import br.ufrgs.inf.ga.exceptions.AlgorithmException;
import br.ufrgs.inf.ga.model.PopulationConfig;

/**
 * Creates an instance of ATC problem from a input file and execute the ATC
 * schedule based on a genetic algorithm.
 * 
 * @author diego
 * 
 */
public class MainProgram {

	public static void main(String[] args) throws AlgorithmException {
		if (args == null || args.length == 0 || args.length < 5) {
			System.out.println("Usage: java "+ MainProgram.class.getName() + " <fileName.txt> <population max individuals> <reproduction rate> <mutation rate> <max generations>");
		} else {
			String fileName = args[0];
			int maxIndividuals = Integer.parseInt(args[1]);
			float reproductionRate = Float.parseFloat(args[2]);
			float mutationRate = Float.parseFloat(args[3]);
			int maxGenerations = Integer.parseInt(args[4]);
			
			try {
				AircraftStaticData[] aircrafts = AtcLoader.createATCInstanceFromFile(fileName);
				PopulationConfig populationConfig = new PopulationConfig(maxIndividuals, reproductionRate, mutationRate);
				GeneticAlgorithm geneticAlgorithmScheduler = new GeneticAlgorithm(populationConfig, maxGenerations, aircrafts);

				AirTrafficControl atc = new AirTrafficControl(geneticAlgorithmScheduler);
				
				System.out.println(atc.scheduleAircraftsLandings());
				
			} catch (IOException e) {
				System.out.println("An I/O error occured. The input file may not be in the standard form.");
			}
		}
	}

	
}
