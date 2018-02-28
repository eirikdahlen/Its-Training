package tdt4140.gr1802.app.core;

public class SignUp {
	String username; 
	String name;
	String password; 
	String repeatPassword; 
	boolean isAthlete; //No need for isCoach if isAthlete is false, then the object will be Coach. 
	User newUser;
	
	Database db = new Database();
	
	public SignUp(String username, String name, String password, String repeatPassword, boolean isAthlete) {
		if (checkNameOnlyLetters(username) ||
				checkNameOnlyLetters(name) ||
				checkUserNameExitsInDB(username) ||
				checkPasswordLength(password) ||
				checkEqualPasswords(password, repeatPassword)){
			this.username = username;
			this.name = name;
			this.password = password;
			this.repeatPassword = repeatPassword; 
			this.isAthlete = isAthlete;
			
		} else {
			throw new IllegalArgumentException("Not valid input for signup");
		}
		/*
		// TODO: Add arguments to Athlete() and Coach()
		if (this.isAthlete) {
			Athlete newAthlete = Athlete();
			this.newUser = newAthlete;
		} else {
			// isCoach
			Coach newCoach = Coach();
			this.newUser = newCoach;
		}*/
	}
	
	public boolean checkNameOnlyLetters(String inputUsername) {
		if (inputUsername.matches("[a-zA-Z]")) {
			return true; 
		} else {
			return false; 
		}
	}
	
	public boolean checkUserNameExitsInDB(String inputUsername) {
		if (db.coachUsernameExists(inputUsername) || (db.athleteUsernameExists(inputUsername))) {
			System.out.println("Username already in db.");
			return false; 
		} else {
			return true;
		}
	}
	
	public boolean checkPasswordLength(String inputPassword) {
		if (inputPassword.length() <= 4) {
			System.out.println("Password shorter than 4 chars.");
			return false;
		} else {
			return true; 
		}	
	}
	
	public boolean checkEqualPasswords(String inputPassword,String repeatPassword) {
		if (inputPassword.equals(repeatPassword)) {
			return true;
		} else {
			return false; 	
		}
	}
	
	public void addNewUserToDB() {
		if (isAthlete) {
			db.createAthlete((Athlete) this.newUser);
		} else {
			db.createCoach((Coach) this.newUser);
		}
	}
	
	

}
