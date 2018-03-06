package tdt4140.gr1802.app.core;

public class LogIn {
	
	User inloggedUser = null;
	
	private String username; 
	private String password;
	
	Database db;
	
	public LogIn(String username, String password) {
		this.username = username; 
		this.password = password;
		this.db = new Database();
	}
	
	public boolean checkUserNameExits(String username) { 
		return checkUsernameAthlete(username) || checkUsernameCoach(username);
	}
	
	
	public boolean validLogIn() {
		if (checkUsernameAthlete(username) && checkUsernameMatchPassword(username, password)) {
			return true;
		} else if (checkUsernameCoach(username) && checkUsernameMatchPassword(username, password)) {
			return true;
		}
		return false; 
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
