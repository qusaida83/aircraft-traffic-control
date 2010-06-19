package br.ufrgs.inf;

import java.io.IOException;

import br.ufrgs.inf.atc.AtcLoader;
import br.ufrgs.inf.atc.AirTrafficControl;
import br.ufrgs.inf.ga.exceptions.AlgorithmException;
import br.ufrgs.inf.ga.model.Population;

/**
 * Creates an instance of ATC problem from a input file and execute the ATC
 * schedule based on a genetic algorithm.
 * 
 * @author diego
 * 
 */
public class MainProgram {

	public static void main(String[] args) throws AlgorithmException {
		if (args == null || args.length == 0) {
			System.out.println("Error!");
			System.out.println("[Usage] java "+ AirTrafficControl.class.getName() + " <fileName>");
			System.out.println("\t1) <fileName> is the name of the file with the ATC instance parameters;");
		} else {
			String fileName = args[0];

			try {
				AirTrafficControl atc = AtcLoader.createATCInstanceFromFile(fileName);
				atc.scheduleAircraftsLandings();
				
			} catch (IOException e) {
				System.out.println("An I/O error occured. The input file may not be in the standard form.");
			}
		}
	}

	
}
