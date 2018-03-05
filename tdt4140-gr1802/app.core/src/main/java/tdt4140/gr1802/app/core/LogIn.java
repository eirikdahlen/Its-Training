package tdt4140.gr1802.app.core;

public class LogIn {
	
	User inloggedUser = null;
	
	Database db;
	
	public LogIn(String username, String password, Database db) {
		this.db = db;
		if ( checkUsernameAthlete(username) ||
				checkUsernameMatchPassword(username, password)) {
				// Athlete-login
				inloggedUser = db.getAthlete(username);
		} else if (
				checkUsernameCoach(username) ||
				checkUsernameMatchPassword(username, password)) {
				// Coach-login
				inloggedUser = db.getCoach(username);
		}
		
	}
	
	
	public boolean validLogIn() {
		return inloggedUser != null; 
	}
	
	public boolean checkUsernameAthlete(String username) {
		return db.athleteUsernameExists(username);
	}
	
	public boolean checkUsernameCoach(String username) {
		return db.coachUsernameExists(username);
	}
	
	public boolean checkUsernameMatchPassword(String username, String password) {
		return db.getPassword(username).equals(password);
	}
	
	

}
