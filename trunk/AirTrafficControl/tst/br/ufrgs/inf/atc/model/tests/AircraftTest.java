package br.ufrgs.inf.atc.model.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ufrgs.inf.atc.model.Aircraft;

import static org.mockito.Mockito.*;

public class AircraftTest {

	private Aircraft previousAircraft;
	private Aircraft aircraft2;
	
	@Before
	public void setUp() {
		previousAircraft = mock(Aircraft.class);
		aircraft2 = mock(Aircraft.class);
		
		// Adds some behavior for some mathods call of the aircrafts.
		stub(aircraft2.getGapTimeBetween(any(Aircraft.class))).toReturn(15);
		stub(previousAircraft.getLandingTime()).toReturn(100);
		
		stub(aircraft2.getLandingTime()).toReturn(115);
	}
	
	@Test
	public void respectsTheGapTimeBetweenTest() {
		Assert.assertTrue(aircraft2.respectsGapTimeBetween(previousAircraft));
	}
}
