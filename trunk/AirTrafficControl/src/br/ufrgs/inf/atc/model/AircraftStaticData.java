package br.ufrgs.inf.atc.model;

/**
 * Aircraft static data loaded from the input file.
 * 
 * @author diego
 *
 */
public class AircraftStaticData {

	/**
	 * Aircraft index on the {@link AircraftStaticData) with the parameters of the ATC problem instance loaded from an input file.
	 */
	private final int aircraftId;
	
	/**
	 * time that the aircraft appears on the radar area.
	 */
	private final int appearanceTime;
	
	/**
	 * the landing time for the aircraft traveling at maximum speed.
	 */
	private final int earliestLandingTime;
	
	/**
	 * the landing time for the aircraft traveling at cruise speed.
	 */
	private final int targetLandingTime;
	
	/**
	 * the landing time for the aircraft traveling at fuel speed economy.
	 */
	private final int latestLandingTime;
	
	/**
	 * the penalty cost for aircraft landing before the target time.
	 */
	private final float landingBeforeTargetTimePenaltyCost;
	
	/**
	 * the penalty cost for aircraft landing after the target time.
	 */
	private final float landingAfterTargetTimePenaltyCost;
	
	/**
	 * Gap time between this aircraft landing and the others landings.
	 */
	private final int[] gapTimeBetweenLandings;
	
	/**
	 * Constructor.
	 * Initializes the aircraft parameters.
	 * 
	 * @param appearanceTime time that the aircraft appears on the radar area.
	 * @param earliestLandingTime the landing time for the aircraft traveling at maximum speed.
	 * @param targetLandingTime the landing time for the aircraft traveling at cruise speed.
	 * @param latestLandingTime the landing time for the aircraft traveling at fuel speed economy.
	 * @param landingBeforeTargetTimePenaltyCost the penalty cost for aircraft landing before the target time.
	 * @param landingAfterTargetTimePenaltyCost the penalty cost for aircraft landing after the target time.
	 * @param gapTimeBetweenLandings Gap time between this aircraft landing and the others landings.
	 */
	public AircraftStaticData(int aircraftId,
			int appearanceTime, int earliestLandingTime,
			int targetLandingTime, int latestLandingTime,
			float landingBeforeTargetTimePenaltyCost,
			float landingAfterTargetTimePenaltyCost,
			int[] gapTimeBetweenLandings)
	{
		this.aircraftId = aircraftId;
		this.appearanceTime = appearanceTime;
		this.earliestLandingTime = earliestLandingTime;
		this.targetLandingTime = targetLandingTime;
		this.latestLandingTime = latestLandingTime;
		this.landingAfterTargetTimePenaltyCost = landingAfterTargetTimePenaltyCost;
		this.landingBeforeTargetTimePenaltyCost = landingBeforeTargetTimePenaltyCost;
		this.gapTimeBetweenLandings = gapTimeBetweenLandings;
	}

	public int getAppearanceTime() {
		return appearanceTime;
	}

	public int getEarliestLandingTime() {
		return earliestLandingTime;
	}

	public int getTargetLandingTime() {
		return targetLandingTime;
	}

	public int getLatestLandingTime() {
		return latestLandingTime;
	}

	public float getLandingBeforeTargetTimePenaltyCost() {
		return landingBeforeTargetTimePenaltyCost;
	}

	public float getLandingAfterTargetTimePenaltyCost() {
		return landingAfterTargetTimePenaltyCost;
	}

	public int[] getGapTimeBetweenLandings() {
		return gapTimeBetweenLandings;
	}

	public int getAircraftId() {
		return aircraftId;
	}
}
