package tdt4140.gr1802.app.core;

public abstract class User {
	
	protected String name ;
		
	protected String username ;
	
	protected String password;
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}
	
	

}
