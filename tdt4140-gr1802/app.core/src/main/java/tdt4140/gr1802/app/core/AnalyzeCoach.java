package tdt4140.gr1802.app.core;

import java.util.List;

public class AnalyzeCoach {
	//class for analysis-methods for a coach. 
	//takes in coach and returns aggregated data for all the coach's athletes
	
	Database db = App.getDb();
	AnalyzeWorkouts analyzeWorkouts = new AnalyzeWorkouts();
	
	
	public int getTotalTrainingTime(Coach coach) {
		int totalTime = 0;
		
// hei på deg ga,mle sjokolade
		List<String> athletesUsername = coach.getAthletes();

		System.out.println(athletesUsername);
		for (String username : athletesUsername) {
			
			int athleteDuration = analyzeWorkouts.getTotalDuration(db.getAllWorkouts(db.getAthlete(username)));
			System.out.println(athleteDuration);
			totalTime = totalTime + athleteDuration;
			
			
			
		}
		
		
		
		
		return totalTime;
		
	}
	
	

}
