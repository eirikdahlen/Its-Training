package tdt4140.gr1802.app.core;

public class App {
	
	public static Athlete athlete;
	public static Coach coach; 
	public static Database db;
	
	public App() {
		
		db = new Database();
		
	}


	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}
	
	public static void updateCoach() {
		App.coach = db.getCoach(coach.getUsername());
	}
	
	public static void updateAthlete() {
		App.athlete = db.getAthlete(athlete.getUsername());
	}
	
	

}
