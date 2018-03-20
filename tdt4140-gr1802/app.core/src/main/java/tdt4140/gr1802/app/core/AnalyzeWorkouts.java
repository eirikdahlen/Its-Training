package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeWorkouts {
	//class for analysis-methods for a single workout
	
	private AnalyzeWorkout analyzeWorkout = new AnalyzeWorkout();
	
	
	public List<Integer> getTimeInHRZones(List<Workout> workoutList) {
		//input is a list of workout-objects, return total time in each zone
		
		//list where the the first element is time in low-zone, second is time in moderate-zone, and third is time in high-zone
		List<Integer> timeInZones = new ArrayList<Integer>();
		
		//variables to increment 
		int minutesInLow = 0; 
		int minutesInModerate = 0; 
		int minutesInHigh = 0; 
			
		for (Workout workout : workoutList) {
			List<Integer> timeInZonesForWorkout = analyzeWorkout.getTimeInHRZones(workout);
			minutesInLow = minutesInLow + timeInZonesForWorkout.get(0);
			minutesInModerate = minutesInModerate + timeInZonesForWorkout.get(1);
			minutesInHigh = minutesInHigh + timeInZonesForWorkout.get(2);			
					
			}
		timeInZones.add(0, minutesInLow);
		timeInZones.add(1, minutesInModerate);
		timeInZones.add(2, minutesInHigh);
		return timeInZones;
				
				
		}
	
	public List<Integer> getHRPercentage(List<Integer> timeInHRZones) {
		return analyzeWorkout.getHRPercentage(timeInHRZones);
	}
	
	public int getTotalDuration(List<Workout> workouts) {
		int totalDuration = 0;
		System.out.println("hallo");
		for (Workout workout : workouts ) {
			
			//adds only if workout is visible for coach
			if (workout.getVisibility()) {
				totalDuration = totalDuration + workout.getDuration();
			}
			
		}
		

		return totalDuration;
	}
		
	
	
	
}

	
	
	

