package br.inf.ufrgs.atc;

/**
 * Encapsulates all aircraft data necessary for a ATC (air traffic control) schedules all landings.
 * 
 * @author diego
 *
 */
public class Aircraft {

	/**
	 * Aircraft landing time.
	 */
	private int landingTime;
	
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
	private final long landingBeforeTargetTimePenaltyCost;
	
	/**
	 * the penalty cost for aircraft landing after the target time.
	 */
	private final long landingAfterTargetTimePenaltyCost;

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
	 */
	public Aircraft(int appearanceTime, int earliestLandingTime,
					int targetLandingTime, int latestLandingTime,
					long landingBeforeTargetTimePenaltyCost,
					long landingAfterTargetTimePenaltyCost)
	{
		this.appearanceTime = appearanceTime;
		this.earliestLandingTime = earliestLandingTime;
		this.targetLandingTime = targetLandingTime;
		this.latestLandingTime = latestLandingTime;
		this.landingAfterTargetTimePenaltyCost = landingAfterTargetTimePenaltyCost;
		this.landingBeforeTargetTimePenaltyCost = landingBeforeTargetTimePenaltyCost;
	}

	public int getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(int landingTime) {
		this.landingTime = landingTime;
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

	public long getLandingBeforeTargetTimePenaltyCost() {
		return landingBeforeTargetTimePenaltyCost;
	}

	public long getLandingAfterTargetTimePenaltyCost() {
		return landingAfterTargetTimePenaltyCost;
	}
}
