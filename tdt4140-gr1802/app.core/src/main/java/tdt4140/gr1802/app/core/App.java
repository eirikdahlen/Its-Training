package tdt4140.gr1802.app.core;

public class App {
	
	private User user;
	private Database db;
	
	public App() {
		
		db = new Database();
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}
	
	

}
