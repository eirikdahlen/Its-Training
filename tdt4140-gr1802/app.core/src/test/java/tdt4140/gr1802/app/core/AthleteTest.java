package tdt4140.gr1802.app.core;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class AthleteTest {

	Athlete athlete = new Athlete ("frikkers", "passord", "Frikk Hald Andersen") ;
	
	// Testing name
	@Test
	public void testName() {
		assertEquals(athlete.name,"Frikk Hald Andersen") ;
	}
	
	// Testing if username is "frikkers"
	@Test
	public void testUsername () {
		assertTrue(athlete.getUsername().equals("frikkers")) ;
	}
}
