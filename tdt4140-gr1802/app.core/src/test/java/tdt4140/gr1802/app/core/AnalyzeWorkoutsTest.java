package tdt4140.gr1802.app.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class AnalyzeWorkoutsTest {
	
	
	Database db = new Database();
	AnalyzeWorkouts analyzeWorkouts = new AnalyzeWorkouts();
	
	public static void main(String[] args) {
		AnalyzeWorkoutsTest test = new AnalyzeWorkoutsTest();
		
		//___________
		System.out.println("time in zones:");
//		test.testGetTimeInHRZones();
		//___________

		//___________
		System.out.println("% in zones:");
		test.testGetHRPercentage();
		//___________
	

	}
	
	public void testGetTimeInHRZones() throws Exception {
		//imports athlete and workout from db for testing
		Athlete athlete1 = db.getAthlete("williamkvaale");
		List<Workout> workoutList = db.getAllWorkouts(athlete1);
		
		
		List<Integer> TimeInHrZones = analyzeWorkouts.getTimeInHRZones(workoutList);
		
		System.out.println(TimeInHrZones);
		
		

		//
	
	}
	
	
	@Test
	public void testGetHRPercentage() {
		
		//imports athlete and workout from db for testing
		List<Integer> TimeInHrZones = Arrays.asList(20,20,0);
		
		List<Integer> percentageInHRZones = analyzeWorkouts.getHRPercentage(TimeInHrZones);
		
		assertEquals(percentageInHRZones,Arrays.asList(50,50,0));
//		System.out.println(percentageInHRZones);
		
	}
	

	
}
