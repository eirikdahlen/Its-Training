package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeCoach {
	//class for analysis-methods for a coach. 
	//takes in coach and returns aggregated data for all the coach's athletes
	
	Database db = App.getDb();
	AnalyzeWorkouts analyzeWorkouts = new AnalyzeWorkouts();
	
	
	public int getTotalTrainingTime(Coach coach) throws Exception {
		int totalTime = 0;
		
		List<String> athletesUsername = coach.getAthletes();

		System.out.println(athletesUsername);
		for (String username : athletesUsername) {
			
			int athleteDuration = analyzeWorkouts.getTotalDuration(db.getAllWorkouts(db.getAthlete(username)));
			System.out.println(athleteDuration);
			totalTime = totalTime + athleteDuration;
			
		}
		return totalTime;
		
	}
	
	public List<Integer> getAvgNrActivites(Coach coach) throws Exception{
		List<String> athletes = coach.getAthletes();
		
		List<Integer> list = new ArrayList<>();
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		
		int count = athletes.size();
		System.out.println(count);
		for (String ath : athletes) {
			List<Integer> temp = db.getAthleteActivityTypes(ath);
			System.out.println(temp);
			a += temp.get(0);
			b += temp.get(1);
			c += temp.get(2);
			d += temp.get(3);
			e += temp.get(4);
		}
		list.add(a/count);
		list.add(b/count);
		list.add(c/count);
		list.add(d/count);
		list.add(e/count);
		System.out.println(list);
		return list;
	}
	
	

}
