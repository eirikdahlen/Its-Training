package tdt4140.gr1802.app.core;

public abstract class User {

	protected String name ;
		
	protected String username ;
	
	protected String password;
	
	// getter and setter for password
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	// getter and setter for name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// getter for username
	public String getUsername() {
		return username;
	}
	
	@Override
	public String toString() {
		return this.username;
	}

}
