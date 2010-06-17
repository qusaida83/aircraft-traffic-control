package br.ufrgs.inf.atc.model;

import java.util.Arrays;

/**
 * Encapsulates all aircraft data necessary for a ATC (air traffic control) schedules all landings.
 * 
 * @author diego
 *
 */
public class Aircraft implements Cloneable, Comparable<Aircraft> {
	
	/**
	 * Aircraft landing time.
	 */
	private int landingTime;
	
	/**
	 * Aircraft static data loaded from an ATC instance input file.
	 */
	private final AircraftStaticData staticData;
	
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
	public Aircraft(int aircraftId, int appearanceTime, int earliestLandingTime,
					int targetLandingTime, int latestLandingTime,
					float landingBeforeTargetTimePenaltyCost,
					float landingAfterTargetTimePenaltyCost,
					int[] gapTimeBetweenLandings)
	{
		this(new AircraftStaticData(aircraftId, appearanceTime, earliestLandingTime, targetLandingTime, latestLandingTime, landingBeforeTargetTimePenaltyCost, landingAfterTargetTimePenaltyCost, gapTimeBetweenLandings));
	}

	/**
	 * Initializes the aircraft static data.
	 * @param staticData Object with all static data of the aircraft retrieved from an input file.
	 */
	public Aircraft(AircraftStaticData staticData) {
		this.staticData = staticData;

		// By default, the landing time is set as the target time of the aircraft.
		this.landingTime = staticData.getTargetLandingTime();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Aircraft)) {
			return false;
		}
		
		Aircraft that = (Aircraft) obj;
		if (this.getId() == that.getId() &&
			this.getAppearanceTime() == that.getAppearanceTime() &&
			this.getEarliestLandingTime() == that.getEarliestLandingTime() &&
			this.getLandingAfterTargetTimePenaltyCost() == that.getLandingAfterTargetTimePenaltyCost() &&
			this.getLandingBeforeTargetTimePenaltyCost() == that.getLandingBeforeTargetTimePenaltyCost() &&
			this.getLandingTime() == that.getLandingTime() &&
			this.getLatestLandingTime() == that.getLatestLandingTime() &&
			this.getTargetLandingTime() == that.getTargetLandingTime() &&
			Arrays.equals(this.getGapTimeBetweenLandings(), that.getGapTimeBetweenLandings())) {
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public int compareTo(Aircraft that) {
		if (this.landingTime > that.getLandingTime()) {
			return 1;
		} else if (this.landingTime < that.getLandingTime()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	@Override
	public Aircraft clone() throws CloneNotSupportedException {
		Aircraft aircraft = new Aircraft(staticData);
		aircraft.setLandingTime(landingTime);
		return aircraft;
	}
	
	@Override
	public String toString() {
		return "Aircraft " + this.getId() + " (landing time: " + this.getLandingTime() + ", target time: " + this.getTargetLandingTime() + ", diff: " + (this.getTargetLandingTime() - this.getLandingTime()) + ")";
	}
	
	/**
	 * Checks if this aircraft landing time (let call it xj) respects the restriction xj >= xi + Sij.
	 * Where xi is the landing time of the previous aircraft and
	 * 		 Sij is the minimal gap time that xj must wait after the xi landing.
	 * 
	 * @param previousAircraft aircraft that landed first this one (xi).
	 * @return true if this landing time respects the restriction. false otherwise.
	 */
	public boolean respectsTheGapTimeBetween(Aircraft previousAircraft) {
		return this.getLandingTime() >= previousAircraft.getLandingTime() + this.getGapTimeBetween(previousAircraft);
	}
	
	/**
	 * Return the gap time between this aircraft and other with id equals to the {@code otherAircraftId}.
	 * 
	 * @param otherAircraftId if of other aircraft.
	 * @return The gap time between this aircraft and other with id equals to the {@code otherAircraftId}.
	 */
	public int getGapTimeBetween(Aircraft otherAircraft) {
		return staticData.getGapTimeBetweenLandings()[otherAircraft.getId()];
	}
	
	public int getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(int landingTime) {
		this.landingTime = landingTime;
	}

	public int getAppearanceTime() {
		return staticData.getAppearanceTime();
	}

	public int getEarliestLandingTime() {
		return staticData.getEarliestLandingTime();
	}

	public int getTargetLandingTime() {
		return staticData.getTargetLandingTime();
	}

	public int getLatestLandingTime() {
		return staticData.getLatestLandingTime();
	}

	public float getLandingBeforeTargetTimePenaltyCost() {
		return staticData.getLandingBeforeTargetTimePenaltyCost();
	}

	public float getLandingAfterTargetTimePenaltyCost() {
		return staticData.getLandingAfterTargetTimePenaltyCost();
	}
	
	public int[] getGapTimeBetweenLandings() {
		return staticData.getGapTimeBetweenLandings();
	}

	public int getId() {
		return staticData.getAircraftId();
	}

	/**
	 * Sets this landing time equals to the target landing time.
	 */
	public void setBestLandingTime() {
		this.landingTime = getTargetLandingTime();
	}
}
