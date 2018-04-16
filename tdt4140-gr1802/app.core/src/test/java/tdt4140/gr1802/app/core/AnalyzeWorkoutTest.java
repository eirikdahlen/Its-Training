package tdt4140.gr1802.app.core;

import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class AnalyzeWorkoutTest {
	
	//init
	Database db = new Database();
	AnalyzeWorkout analyzeWorkout = new AnalyzeWorkout();
	
	public static void main(String[] args) throws Exception {
		AnalyzeWorkoutTest test = new AnalyzeWorkoutTest();
		
		
		//__________
		//System.out.println("zones:");
		//test.testGetHRZones();
		//___________
		
		//___________
		//System.out.println("time in zones:");
		//test.testGetTimeInHRZones();
		//___________
	
		//___________
		//System.out.println("percentage in zones:");
		//test.testGetHRPercentage();
		//___________
	}
	
	public void testGetHRZones() throws Exception {
		//imports athlete and workout from db for testing
		Athlete athlete1 = db.getAthlete("Olaf90");
		Workout workout1 = db.getWorkout(athlete1, "14-12-2015 12:47:12");
		
		
		List<Integer> HRZones = analyzeWorkout.getHRZones(workout1.getAthlete());
		
		System.out.println(HRZones);

		//
		
	}
	
	public void testGetTimeInHRZones() throws Exception {
		//imports athlete and workout from db for testing
		Athlete athlete1 = db.getAthlete("williamkvaale");
		Workout workout1 = db.getWorkout(athlete1, "25-01-2018 17:31:41");
		
		
		List<Integer> TimeInHrZones = analyzeWorkout.getTimeInHRZones(workout1);
		
		System.out.println(TimeInHrZones);
		
		

		//
	
	}
	
	@Test
	public void getHRPercentageTest() throws Exception {
		
		//imports athlete and workout from db for testing
		Athlete athlete1 = db.getAthlete("williamkvaale");
		Workout workout1 = db.getWorkout(athlete1, "25-01-2018 17:31:41");
		
		
		List<Integer> TimeInHrZones = Arrays.asList(10,20,15);
		
		List<Integer> percentageInHRZones = analyzeWorkout.getHRPercentage(TimeInHrZones);
		
		assertFalse(percentageInHRZones.equals(Arrays.asList(12,12,10)));

		
	}
}
