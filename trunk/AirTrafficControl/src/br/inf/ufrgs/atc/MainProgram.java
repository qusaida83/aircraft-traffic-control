package br.inf.ufrgs.atc;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Creates an instance of ATC problem from a input file and execute the ATC schedule based on a genetic algorithm.
 * 
 * @author diego
 *
 */
public class MainProgram {
	
	public static void main(String[] args) {
		if (args == null || args.length == 1) {
			System.out.println("Error!");
			System.out.println("[Usage] java AirTrafficControl <fileName>");
			System.out.println("\t1) <fileName> is the name of the file with the ATC instance parameters;");
		}
		String fileName = args[1];
		
		try {
			MainProgram main = new MainProgram();
			AirTrafficControl atc = main.createATCInstanceFromFile(fileName);
			
		} catch (IOException e) {
			System.out.println("An I/O error occured. The input file may not be in the standard form.");
		}
	}
	
	/**
	 * Creates the ATC instance based on an input file.
	 * 
	 * @param fileName name of the input file
	 * @param fileEncoding file encoding (default is UTF-8)
	 * @throws IOException thrown if any I/O operation goes wrong.
	 */
	public AirTrafficControl createATCInstanceFromFile(String fileName) throws IOException {
		// String with all parameters for the problem instance. each parameter is separated from others by a space character.
		String textFromFile = getTextFromFile(fileName);
		String[] atcInstanceParameters = textFromFile.split(" ");
		
		int aircraftCount = Integer.valueOf(atcInstanceParameters[0]);

		// allocates a vector to store all aircrafts in the radar area.
		Aircraft[] aircrafts = new Aircraft[aircraftCount];
		
		// initializes the matrix with landing times gap between the aircrafts.
		int[][] gapTimeBetweenAnotherAircraftLanding = new int[aircraftCount][aircraftCount];
		for (int i = 0; i < aircraftCount; i++) {
			// creates the aircraft
			aircrafts[i] = createAircraft(i, atcInstanceParameters, aircraftCount);
			// add the aircraft landing times gap to the matrix of landing times gap between all aircrafts.
			gapTimeBetweenAnotherAircraftLanding[i] = createLandingTimesGapForAircraft(i, atcInstanceParameters, aircraftCount);
		}
		
		return new AirTrafficControl(aircrafts, gapTimeBetweenAnotherAircraftLanding);
	}
	
	/**
	 * Creates an aircraft based on the parameters retrieved from a file.
	 * 
	 * @param atcInstanceParameters vector with all parameters for the ATC problem instance.
	 * @param aircraftCount number of aircrafts
	 * @param aircraftId the aircraft id. This number indicates the position of the aircraft among all the aircrafts in the file.
	 * @return an instance of {@link Aircraft}
	 */
	private Aircraft createAircraft(int aircraftId, String[] atcInstanceParameters, int aircraftCount) {
		// start index for the aircraft parameters.
		int aircraftParametersIndex = getAircraftParametersIndex(aircraftId, aircraftCount);
		int appearanceTime = Integer.valueOf(atcInstanceParameters[aircraftParametersIndex]);
		int earliestLandingTime = Integer.valueOf(atcInstanceParameters[aircraftParametersIndex + 1]);
		int targetLandingTime = Integer.valueOf(atcInstanceParameters[aircraftParametersIndex + 2]);
		int latestLandingTime = Integer.valueOf(atcInstanceParameters[aircraftParametersIndex + 3]);
		long landingBeforeTargetTimePenaltyCost = Long.valueOf(atcInstanceParameters[aircraftParametersIndex + 4]);
		long landingAfterTargetTimePenaltyCost = Long.valueOf(atcInstanceParameters[aircraftParametersIndex + 5]);
		
		Aircraft aircraft = new Aircraft(appearanceTime, earliestLandingTime, targetLandingTime, latestLandingTime, landingBeforeTargetTimePenaltyCost, landingAfterTargetTimePenaltyCost);
		return aircraft;
	}
	
	/**
	 * Create a vector of landing times gap between an aircraft and all others aircrafts in the radar area.
	 * 
	 * @param aircraftId the aircraft id. This number indicates the position of the aircraft among all the aircrafts in the file.
	 * @param atcInstanceParameters vector with all parameters for the ATC problem instance.
	 * @param aircraftCount number of aircrafts
	 */
	private int[] createLandingTimesGapForAircraft(int aircraftId, String[] atcInstanceParameters, int aircraftCount) {
		int[] aircraftLandingTimesGap = new int[aircraftCount];
		int aircraftTimeGapsIndex = getAircraftParametersIndex(aircraftId, aircraftCount) + 6;
		for (int i = 0; i < aircraftCount; i++) {
			aircraftLandingTimesGap[i] = Integer.valueOf(atcInstanceParameters[aircraftTimeGapsIndex + i]);
		}
		return aircraftLandingTimesGap;
	}
	
	/**
	 * Retrieve the start index for an aircraft parameters in a vector of parameters for a ATC problem instance.
	 * 
	 * @param aircraftId the aircraft id. This number indicates the position of the aircraft among all the aircrafts in the file.
	 * @param aircraftCount number of aircrafts
	 * @return the aircraft paramenters start index in the atcInstanceParameters vector.
	 */
	private int getAircraftParametersIndex(int aircraftId, int aircraftCount) {
		return aircraftId * (6 + aircraftCount) + 2;
	}
	
	private String getTextFromFile(String fileName) throws IOException {
		StringBuilder textFromFile = new StringBuilder();
		Scanner scanner = new Scanner(new File(fileName));
		try {
			while (scanner.hasNextLine()) {
				textFromFile.append(scanner.nextLine() + " ");
			}
		} finally {
			scanner.close();
		}
		
		return textFromFile.toString();
	}
}
