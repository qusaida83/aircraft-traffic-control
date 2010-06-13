package br.inf.ufrgs.atc;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the air traffic control system.
 * 
 * @author diego
 * 
 */
public class AirTrafficControl {

	public static final Logger logger = Logger.getLogger(AirTrafficControl.class.getSimpleName());;

	/**
	 * Vector that holds all aircrafts in the radar visible area.
	 */
	private Aircraft[] aircrafts;

	/**
	 * Matrix nxn where n is the aircraft count in the radar visible area. This
	 * matrix holds the gap time required for the aircraft j lands after the
	 * landing of the aircraft i, with i and j in the range [0 .. n - 1].
	 */
	private int[][] gapTimeBetweenAnotherAircraftLanding;

	/**
	 * Constructs the Air traffic control core.
	 * 
	 * @param aircrafts
	 * @param gapTimeBetweenAnotherAircraftLanding
	 */
	public AirTrafficControl(Aircraft[] aircrafts, int[][] gapTimeBetweenAnotherAircraftLanding) {
		this.aircrafts = aircrafts;
		this.gapTimeBetweenAnotherAircraftLanding = gapTimeBetweenAnotherAircraftLanding;

	}

	/**
	 * Generate a landing schedule based on the aircrafts data using a genetic
	 * algorithm.
	 */
	public void scheduleAircraftsLandings() {

	}

	public void main(String[] args) {
		
	}

	
	public void createATCInstanceFromFile(String fileName, String fileEncoding) throws IOException {
		// String with all parameters for the problem instance. each parameter is separated from others by a space character.
		String textFromFile = getTextFromFile(fileName, fileEncoding);
		String[] atcInstanceParameters = textFromFile.split(" ");
		
		int aircraftCount = Integer.valueOf(atcInstanceParameters[0]);

		// allocates a vector to store all aircrafts in the radar area.
		this.aircrafts = new Aircraft[aircraftCount];
		
		// initializes the matrix with landing times gap between the aircrafts.
		this.gapTimeBetweenAnotherAircraftLanding = new int[aircraftCount][aircraftCount];
		for (int i = 0; i < aircraftCount; i++) {
			// creates the aircraft
			aircrafts[i] = createAircraft(i, atcInstanceParameters, aircraftCount);
			// creates the aircraft vector of landing times gap between all others aircrafts in the radar area.
			createLandingTimesGapForAircraft(i, atcInstanceParameters, aircraftCount);
		}
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
	private void createLandingTimesGapForAircraft(int aircraftId, String[] atcInstanceParameters, int aircraftCount) {
		int[] aircraftLandingTimesGap = new int[aircraftCount];
		int aircraftTimeGapsIndex = getAircraftParametersIndex(aircraftId, aircraftCount) + 6;
		for (int i = 0; i < aircraftCount; i++) {
			aircraftLandingTimesGap[i] = Integer.valueOf(atcInstanceParameters[aircraftTimeGapsIndex + i]);
		}
		// add the aircraft landing times gap to the matrix of landing times gap between all aircrafts.
		this.gapTimeBetweenAnotherAircraftLanding[aircraftId] = aircraftLandingTimesGap;
	}
	
	/**
	 * Retrieve the start index for an aircraft parameters in a vector of parameters for a ATC problem instance.
	 * 
	 * @param aircraftId the aircraft id. This number indicates the position of the aircraft among all the aircrafts in the file.
	 * @param aircraftCount
	 * @return
	 */
	private int getAircraftParametersIndex(int aircraftId, int aircraftCount) {
		return aircraftId * (6 + aircraftCount) + 2;
	}
	
	private String getTextFromFile(String fileName, String fileEncoding) throws IOException {
		StringBuilder textFromFile = new StringBuilder();
		Scanner scanner = new Scanner(new File(fileName), fileEncoding);
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
