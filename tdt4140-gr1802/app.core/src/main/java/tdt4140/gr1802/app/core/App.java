package tdt4140.gr1802.app.core;

public class App {
	
	private static Athlete athlete;
	private static Coach coach; 
	private static Database db;
	
	public App() {
		
		db = new Database();
		
	}
	
	// Athlete - Get and set 
	public static Athlete getAthlete() { return athlete; }
	public static void setAthlete(Athlete newAthlete) { athlete = newAthlete; }
	
	// Coach - Get and set
	public static Coach getCoach() { return coach; }
	public static void setCoach(Coach newCoach) { coach = newCoach; }
	
	// Database - Get and set
	public static Database getDb() { return db; }
	public static void setDb(Database newDb) { db = newDb; }
	
	
	
	public static void updateCoach() { coach = db.getCoach(coach.getUsername()); }
	
	public static void updateAthlete() { athlete = db.getAthlete(athlete.getUsername()); }
	
	

}
