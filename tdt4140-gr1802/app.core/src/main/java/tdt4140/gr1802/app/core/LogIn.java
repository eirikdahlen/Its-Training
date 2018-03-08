package tdt4140.gr1802.app.core;

public class LogIn {
	
	User inloggedUser = null;
	
	private String username; 
	private String password;
	
	Database db = new Database();
	
	public LogIn(String username, String password) {
		this.username = username; 
		this.password = password;
	}
	
	// Getter for User
	public User getUser() { return this.inloggedUser; }
	
	public boolean checkUserNameExits(String username) { 
		return checkUsernameAthlete(username) || checkUsernameCoach(username);
	}
	
	
	public boolean validLogIn() {
		if (checkUsernameAthlete(username) && checkUsernameMatchPassword(username, password)) {
			inloggedUser = this.db.getAthlete(username);
			return true;
		} else if (checkUsernameCoach(username) && checkUsernameMatchPassword(username, password)) {
			inloggedUser = this.db.getCoach(username);
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
		System.out.println(username);
		System.out.println(password);
		System.out.println(db);
		System.out.println(db.getPassword(username));
		return db.getPassword(username).equals(password);
	}
}
