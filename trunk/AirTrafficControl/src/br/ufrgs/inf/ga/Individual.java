package br.ufrgs.inf.ga;

import br.ufrgs.inf.atc.model.Aircraft;

public class Individual {
	private final Aircraft[] aircraftLandingSequence;
	private final int fitnessValue;
	
	public Individual(Aircraft[] aircraftLandingSequence, int fitnessValue) {
		this.aircraftLandingSequence = aircraftLandingSequence;
		this.fitnessValue = fitnessValue;
	}

	public Aircraft[] getAircraftLandingSequence() {
		return aircraftLandingSequence;
	}

	public int getFitnessValue() {
		return fitnessValue;
	}
	
}
