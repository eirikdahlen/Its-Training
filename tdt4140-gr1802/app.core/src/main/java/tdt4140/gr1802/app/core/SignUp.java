package tdt4140.gr1802.app.core;

public class SignUp {
	private String username; 
	private String name;
	private String password; 
	private String repeatPassword; 
	private boolean isAthlete; //No need for isCoach if isAthlete is false, then the object will be Coach. 
	private static User newUser; // private static so we can exchange the user between other classes. 
	private static boolean validLogin = false; 
	private Database db;
	
	
	public SignUp(String username, String name, String password, String repeatPassword, boolean isAthlete) {
			this.username = username;
			this.name = name;
			this.password = password;
			this.repeatPassword = repeatPassword; 
			this.isAthlete = isAthlete;
			this.db = new Database();	
		}

	public boolean checkNameOnlyLetters() {
		if (this.name.matches("[a-zA-Z ]+")) {
			return true; 
		} else {
			return false; 
		}
	}
	
	public boolean checkUserNameValidAndLenght() {
		if (this.username.matches("[a-zA-Z0-9]+") && this.username.length() > 0) {
			return true; 
		} else {
			return false; 
		}
	}
	
	public boolean checkUserNameNotExitsInDB() {
		if (db.coachUsernameExists(this.username) || (db.athleteUsernameExists(this.username))) {
			System.out.println("Username already in db.");
			return false; 
		} else {
			return true;
		}
	}
	
	public boolean checkPasswordLength() {
		if (this.password.length() <= 4) {
			System.out.println("Password shorter than 4 chars.");
			return false;
		} else {
			return true; 
		}	
	}
	
	public boolean checkEqualPasswords() {
		if (this.password.equals(this.repeatPassword)) {
			return true;
		} else {
			return false; 	
		}
	}
	
	public void addNewUserToDB() {
		if (isAthlete) {
			db.createAthlete((Athlete) newUser);
		} else {
			db.createCoach((Coach) newUser);
		}
	}
	
	public boolean validSignUp() {
		if (checkUserNameNotExitsInDB() &&
				checkNameOnlyLetters() &&
				checkPasswordLength() &&
				checkEqualPasswords()) {
			validLogin = true;
			
			if (isAthlete) {
				newUser = new Athlete(this.username, this.password, this.name, null, null);
			} else {
				newUser = new Coach(this.username, this.password, this.name, null, null);
			}
			
			addNewUserToDB();
			
			return true;
		} else {
			return false;
		}
	}

	public User getUser() { return newUser; }
	
	public boolean isValidLogin() { return validLogin; }
	
	
	

}
