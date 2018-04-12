package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.*;
import java.util.List;

public class AnalyzeWorkout {
	//class for analysis-methods for a single workout
	
	//PULSE_ZONES: percentages for calculating HR-zones. We'll use <75% for low, 75-90% for moderate and 90%-max for high.
	private final double low = 0.75;
	private final double moderate = 0.90;
	
	//how many seconds is each pulse-step:
	private final int timeSteps = CSVReader.timeStep;
	
	
	
	
	public List<Integer> getTimeInHRZones(Workout workout) {
		
		
		//list where the the first element is time in low-zone, second is time in moderate-zone, and third is time in high-zone
		List<Integer> timeInZones = new ArrayList<Integer>();
				 
		//get athletes zones
		List<Integer> zones = this.getHRZones(workout.getAthlete());
	
		//get pulseList	
		List<String> pulseList = workout.getPulsList();
		
		//init time-variables
		int SecondsInLow = 0;
		int SecondsInModerate = 0;
		int SecondsInHigh = 0;
		
		//iterates through pulse-list
		for (String pulse : pulseList) {

		   if (Integer.parseInt(pulse) < zones.get(0) ) {
			   SecondsInLow = SecondsInLow + this.timeSteps ;
			   
		   } else if (Integer.parseInt(pulse) < zones.get(1) ) {
			   SecondsInModerate = SecondsInModerate+ this.timeSteps;
			   
		   } else if (Integer.parseInt(pulse) < zones.get(2)) {
			   SecondsInHigh = SecondsInHigh + this.timeSteps ;
		   }
	
		}
		timeInZones.add(0, ((int) Math.round( SecondsInLow/60 )) );
		timeInZones.add(1, ((int) Math.round( SecondsInModerate/60 ))    );
		timeInZones.add(2, ((int) Math.round( SecondsInHigh/60 ))    );
		return timeInZones;
	}
	
	
	public List<Integer> getHRZones(Athlete athlete) {
		//list where the the first element is limit for low-zone, second is limit for moderate-zone, and third is limit for high-zone (maxHR)

		List<Integer> zones = new ArrayList<Integer>();
				 
		//get athletes maxHR
		int maxHR = athlete.getMaxHR();
		
		//if maxHR not set:
		if (maxHR == 0) {
			System.out.println("maxHr not set for particular athlete");
		} else {
			
		//calculate zones and add to list
		zones.add(0, ((int) Math.round( this.low*maxHR ))  );
		zones.add(1, ((int) Math.round( this.moderate*maxHR ))  );
		zones.add(2, maxHR );
		
		return zones;
		}

		return null;
	}
	
	
	public List<Integer> getHRPercentage(List<Integer> timeInHRZones) {
		//takes in timeInHRZone list with format (timeInLow, timeInModerate, timeInHigh)
	
		//list to contain %-s
		List<Integer> percentages = new ArrayList<Integer>();
		
		//the total time in different zones
		int totalTime = timeInHRZones.get(0) + timeInHRZones.get(1) + timeInHRZones.get(2);
		
		int lowPercentage = timeInHRZones.get(0)*100 / totalTime;
		int moderatePercentage = timeInHRZones.get(1)*100 /totalTime;
		int highPercentage = timeInHRZones.get(2)*100 / totalTime;
		
		//fix so the %-s sums to 100
		int diff = 100 - (lowPercentage + moderatePercentage + highPercentage);
		
		//adds to list
		percentages.add(0, lowPercentage+diff);
		percentages.add(1, moderatePercentage);
		percentages.add(2, highPercentage);


		return percentages;
		
	}
	
	public List<Integer> getAnalyzedHRZonesMeanValueForAthlete(List<Workout> workouts) {
		int totLow = 0;
		int totModerate = 0;
		int totHigh = 0;
		for (Workout workout : workouts) {
			totLow += getHRPercentage(getTimeInHRZones(workout)).get(0);
			totModerate += getHRPercentage(getTimeInHRZones(workout)).get(1);
			totHigh += getHRPercentage(getTimeInHRZones(workout)).get(2);
		}
		totLow = totLow/workouts.size();
		totModerate = totModerate/workouts.size();
		totHigh = totHigh/workouts.size();
		System.out.println("Low: " + totLow);
		System.out.println("Moderate: " + totModerate);
		System.out.println("High: " + totHigh);
		
		List<Integer> meanValueHRZones = Arrays.asList(totLow,totModerate,totHigh);
		return meanValueHRZones;
		
	}
	
	public List<Integer> getAnalyzedHRZonesMeanValueForAll(List<Athlete> athletes) {
		int totLow = 0;
		int totModerate = 0;
		int totHigh = 0;
		int counter = 0;
		for (Athlete athlete : athletes) {
			for (Workout workout : athlete.getAllWorkouts()) {
				counter += 1;
				totLow += getHRPercentage(getTimeInHRZones(workout)).get(0);
				totModerate += getHRPercentage(getTimeInHRZones(workout)).get(1);
				totHigh += getHRPercentage(getTimeInHRZones(workout)).get(2);
			}
		}
		totLow = totLow/counter;
		totModerate = totModerate/counter;
		totHigh = totHigh/counter;
		System.out.println("Low: " + totLow);
		System.out.println("Moderate: " + totModerate);
		System.out.println("High: " + totHigh);
		
		List<Integer> meanValueHRZones = Arrays.asList(totLow,totModerate,totHigh);
		return meanValueHRZones;
		
	}
	
	public int getAnalyzedDurationMeanValueForAthlete(List<Workout> workouts) {
		int duration = 0;
		for (Workout workout : workouts) {
			duration += workout.getDuration();
		}
		duration = duration/workouts.size();
		System.out.println("Duration: " + duration);
		
		return duration;
	}
	
	public int getAnalyzedDurtionMeanValueForAll(List<Athlete> athletes) {
		int duration = 0;
		int counter = 0;
		for (Athlete athlete : athletes) {
			for (Workout workout : athlete.getAllWorkouts()) {
				counter += 1;
				duration += workout.getDuration();
			}
		}
		duration = duration/counter;
		System.out.println("Duration: " + duration);
		
		return duration;
	}
	
	public int getAnalyzedAmountForAthlete(List<Workout> workouts) {
		return workouts.size();
	}
	
	public int getAnalyzedAmountMeanValueForAll(List<Athlete> athletes) {
		int amount = 0;
		for (Athlete athlete : athletes) {
			amount += athlete.getNumbWorkouts();
		}
		amount = amount/athletes.size();
		return amount;
	}
	
	
}
