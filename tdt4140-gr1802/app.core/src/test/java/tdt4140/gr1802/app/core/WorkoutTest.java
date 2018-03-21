package tdt4140.gr1802.app.core;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class WorkoutTest {
	
	private static final double EPSILON = 1e-15;
	
	Workout workout;

	Athlete athlete1 = new Athlete("Test123","passord","Test Testesen");

//	String filePath = "src/test/resources/tdt4140/gr1802/app/core/CSV1.csv";
	
	URL path = getClass().getResource("CSV1.csv");
	
	public WorkoutTest() throws IOException {
		this.workout = new Workout(athlete1, path, null);
	}
	
//	__JUnit__ 

	@Test
	public void testGetAthlete() throws IOException {
		assertEquals(workout.getAthlete(),athlete1);
	}
	
	@Test
	public void testGetAverageHR() {
//		setting the AverageHR to 150
		workout.setAverageHR(150);
		
		assertTrue(workout.getAverageHR().equals(150));	
	}
	
	@Test
	public void testGetDate() {
//		Setting up actual date for the actual testingCSV
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		
		String dateInString = "01-01-2016 10:34:37";
		
		try {
			Date date = sdf.parse(dateInString);
			assertEquals(workout.getDate(),date);
		} 
		catch (ParseException pe) {
			pe.printStackTrace();
		}	
	}
	
	@Test
	public void testGetDateString() {
//		Setting up false date
		String date = "01-01-2001 13:33:37";
		
		assertFalse(workout.getDateString().equals(date));
	}
	
	@Test
	public void testGetPulselist() {
		List<String> test = new ArrayList<String>();
		
//		Setting up sudo-pulseList
		test = Arrays.asList("1","2","3");
		
		workout.setPulsList(test);;
		
		assertEquals(workout.getPulsList(),test);
		
	}
	
	@Test
	public void testDuration() {
//		setting the duration to 100
		workout.setDuration(100);
		
		assertEquals(workout.getDuration(),(100));	
	}
	
	@Test
	public void testMaxHR() {
//		setting the MaxHR to 205
		workout.setMaxHR(205);
		
		assertTrue(workout.getMaxHR().equals(205));	
	}

	@Test
	public void testGetKilometres() {
//		setting the MaxHR to 205
		workout.setKilometres(44.2);
		
		
		assertEquals(workout.getKilometres(),44.2,EPSILON);
//		assertThat(workout.getKilometres(), is(not(22.1));
		 
	}

}
