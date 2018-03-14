package tdt4140.gr1802.app.core;

import java.util.List;

public class AnalyzeWorkoutsTest {
	
	
	Database db = new Database();
	AnalyzeWorkouts analyzeWorkouts = new AnalyzeWorkouts();
	
	public static void main(String[] args) {
		AnalyzeWorkoutsTest test = new AnalyzeWorkoutsTest();
		
		//___________
		System.out.println("time in zones:");
		test.testGetTimeInHRZones();
		//___________

		//___________
		System.out.println("% in zones:");
		test.testGetHRPercentage();
		//___________
	

	}
	
	public void testGetTimeInHRZones() {
		//imports athlete and workout from db for testing
		Athlete athlete1 = db.getAthlete("Olaf90");
		List<Workout> workoutList = db.getAllWorkouts(athlete1);
		
		
		List<Integer> TimeInHrZones = analyzeWorkouts.getTimeInHRZones(workoutList);
		
		System.out.println(TimeInHrZones);
		
		

		//
	
	}
	
	public void testGetHRPercentage() {
		
		//imports athlete and workout from db for testing
		Athlete athlete1 = db.getAthlete("Olaf90");
		List<Workout> workoutList = db.getAllWorkouts(athlete1);
		
		
		List<Integer> TimeInHrZones = analyzeWorkouts.getTimeInHRZones(workoutList);
		
		List<Integer> percentageInHRZones = analyzeWorkouts.getHRPercentage(TimeInHrZones);
		
		System.out.println(percentageInHRZones);
		
	}
	

	
}
