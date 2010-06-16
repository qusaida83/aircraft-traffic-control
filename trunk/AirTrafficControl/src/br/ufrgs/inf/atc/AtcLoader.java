package br.ufrgs.inf.atc;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import br.ufrgs.inf.atc.model.Aircraft;
import br.ufrgs.inf.atc.model.AircraftStaticData;

/**
 * Load an instance of ATC problem from a text file.
 * 
 * @author diego
 *
 */
public class AtcLoader {

	/**
	 * Creates the ATC instance based on an input file.
	 * 
	 * @param fileName
	 *            name of the input file
	 * @param fileEncoding
	 *            file encoding (default is UTF-8)
	 * @throws IOException
	 *             thrown if any I/O operation goes wrong.
	 */
	public static AirTrafficControl createATCInstanceFromFile(String fileName) throws IOException {
		// String with all parameters for the problem instance. each parameter
		// is separated from others by a space character.
		String textFromFile = getTextFromFile(fileName);
		textFromFile = textFromFile.replace("  ", " ");

		textFromFile = textFromFile.substring(1, textFromFile.length() - 1);
		String[] atcInstanceParameters = textFromFile.split(" ");

		// number of aircrafts in the ATC problem instance.
		int aircraftCount = Integer.valueOf(atcInstanceParameters[0]);

		// allocates a vector to store all aircrafts in the radar area.
		AircraftStaticData[] aircrafts = new AircraftStaticData[aircraftCount];

		for (int i = 0; i < aircraftCount; i++) {
			// creates the aircraft
			aircrafts[i] = createAircraftStaticData(i, atcInstanceParameters, aircraftCount);
		}

		return new AirTrafficControl(aircrafts);
	}

	/**
	 * Creates an {@link AircraftStaticData} based on the parameters retrieved from a file.
	 * 
	 * @param atcInstanceParameters
	 *            vector with all parameters for the ATC problem instance.
	 * @param aircraftCount
	 *            number of aircrafts
	 * @param aircraftId
	 *            the aircraft id. This number indicates the position of the
	 *            aircraft among all the aircrafts in the file.
	 * @return an instance of {@link Aircraft}
	 */
	private static AircraftStaticData createAircraftStaticData(int aircraftId, String[] atcInstanceParameters, int aircraftCount) {
		// start index for the aircraft parameters.
		int aircraftParametersIndex = getAircraftParametersIndex(aircraftId, aircraftCount);
		int appearanceTime = Integer.valueOf(atcInstanceParameters[aircraftParametersIndex]);
		int earliestLandingTime = Integer.valueOf(atcInstanceParameters[aircraftParametersIndex + 1]);
		int targetLandingTime = Integer.valueOf(atcInstanceParameters[aircraftParametersIndex + 2]);
		int latestLandingTime = Integer.valueOf(atcInstanceParameters[aircraftParametersIndex + 3]);
		float landingBeforeTargetTimePenaltyCost = Float.valueOf(atcInstanceParameters[aircraftParametersIndex + 4]);
		float landingAfterTargetTimePenaltyCost = Float.valueOf(atcInstanceParameters[aircraftParametersIndex + 5]);
		int[] gapTimeBetweenLandings = createGapTimesBetweenLandings(aircraftId, atcInstanceParameters, aircraftCount);
		
		return new AircraftStaticData(appearanceTime, earliestLandingTime, targetLandingTime, latestLandingTime, landingBeforeTargetTimePenaltyCost, landingAfterTargetTimePenaltyCost, gapTimeBetweenLandings);
	}

	/**
	 * Create a vector of landing times gap between an aircraft and all others
	 * aircrafts in the radar area.
	 * 
	 * @param aircraftId
	 *            the aircraft id. This number indicates the position of the
	 *            aircraft among all the aircrafts in the file.
	 * @param atcInstanceParameters
	 *            vector with all parameters for the ATC problem instance.
	 * @param aircraftCount
	 *            number of aircrafts
	 */
	private static int[] createGapTimesBetweenLandings(int aircraftId, String[] atcInstanceParameters, int aircraftCount) {
		int[] aircraftLandingTimesGap = new int[aircraftCount];
		int aircraftTimeGapsIndex = getAircraftParametersIndex(aircraftId, aircraftCount) + 6;
		for (int i = 0; i < aircraftCount; i++) {
			aircraftLandingTimesGap[i] = Integer.valueOf(atcInstanceParameters[aircraftTimeGapsIndex + i]);
		}
		return aircraftLandingTimesGap;
	}

	/**
	 * Retrieve the start index for an aircraft parameters in a vector of
	 * parameters for a ATC problem instance.
	 * 
	 * @param aircraftId
	 *            the aircraft id. This number indicates the position of the
	 *            aircraft among all the aircrafts in the file.
	 * @param aircraftCount
	 *            number of aircrafts
	 * @return the aircraft paramenters start index in the atcInstanceParameters
	 *         vector.
	 */
	private static int getAircraftParametersIndex(int aircraftId, int aircraftCount) {
		return aircraftId * (6 + aircraftCount) + 2;
	}

	private static String getTextFromFile(String fileName) throws IOException {
		StringBuilder textFromFile = new StringBuilder();
		Scanner scanner = new Scanner(new File(fileName));
		try {
			while (scanner.hasNextLine()) {
				textFromFile.append(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}

		return textFromFile.toString();
	}
}
