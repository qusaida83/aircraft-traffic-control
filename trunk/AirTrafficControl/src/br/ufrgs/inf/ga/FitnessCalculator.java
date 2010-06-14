package br.ufrgs.inf.ga;

import br.ufrgs.inf.atc.model.Aircraft;

public class FitnessCalculator {

	private final int[][] gapTimeBetweenAnotherAircraftLanding;
	
	public FitnessCalculator(int[][] gapTimeBetweenAnotherAircraftLanding) {
		this.gapTimeBetweenAnotherAircraftLanding = gapTimeBetweenAnotherAircraftLanding;
	}
	
	public int calculate(Aircraft[] aircraftLandingSequence) {
		// TODO
		return 0;
	}
}
