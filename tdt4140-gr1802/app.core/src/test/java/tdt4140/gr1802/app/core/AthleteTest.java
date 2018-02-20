package tdt4140.gr1802.app.core;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class AthleteTest {
	
	
	Athlete athlete = new Athlete ("frikkers", "Frikk Hald Andersen", null, null) ;
	

	@Test
	public void testName() {
		assertEquals(athlete.name,"Frikk Hald Andersen") ;
	}
	@Test
	public void testUsername () {
		assertTrue(athlete.getUsername().equals("frikkers")) ;
	}

}
