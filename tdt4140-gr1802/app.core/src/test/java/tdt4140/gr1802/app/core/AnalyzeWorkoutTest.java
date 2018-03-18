package tdt4140.gr1802.app.core;

import java.util.List;

public class AnalyzeWorkoutTest {
	
	//init
	Database db = new Database();
	AnalyzeWorkout analyzeWorkout = new AnalyzeWorkout();
	
	public static void main(String[] args) {
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
		test.testGetHRPercentage();
		//___________
	}
	
	public void testGetHRZones() {
		//imports athlete and workout from db for testing
		Athlete athlete1 = db.getAthlete("Olaf90");
		Workout workout1 = db.getWorkout(athlete1, "14-12-2015 12:47:12");
		
		
		List<Integer> HRZones = analyzeWorkout.getHRZones(workout1.getAthlete());
		
		System.out.println(HRZones);

		//
		
	}
	
	public void testGetTimeInHRZones() {
		//imports athlete and workout from db for testing
		Athlete athlete1 = db.getAthlete("Olaf90");
		Workout workout1 = db.getWorkout(athlete1, "14-12-2015 12:47:12");
		
		
		List<Integer> TimeInHrZones = analyzeWorkout.getTimeInHRZones(workout1);
		
		System.out.println(TimeInHrZones);
		
		

		//
	
	}
	
	public void testGetHRPercentage() {
		
		//imports athlete and workout from db for testing
		Athlete athlete1 = db.getAthlete("Olaf90");
		Workout workout1 = db.getWorkout(athlete1, "14-12-2015 12:47:12");
		
		
		List<Integer> TimeInHrZones = analyzeWorkout.getTimeInHRZones(workout1);
		
		List<Integer> percentageInHRZones = analyzeWorkout.getHRPercentage(TimeInHrZones);
		
		System.out.println(percentageInHRZones);
		
	}
}
