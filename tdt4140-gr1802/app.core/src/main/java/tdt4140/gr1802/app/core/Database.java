package tdt4140.gr1802.app.core;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Database {
	
	//kobler til MongoDB 
	private String uri = "mongodb://theodorastrupwiik:starwars123!@pu-shard-00-00-wgffn.mongodb.net:27017,pu-shard-00-01-wgffn.mongodb.net:27017,pu-shard-00-02-wgffn.mongodb.net:27017/admin?replicaSet=PU-shard-0&ssl=true";
	private MongoClientURI clientURI = new MongoClientURI(uri);
	private MongoClient mongoClient = new MongoClient(clientURI);
	
	private MongoDatabase athleteDatabase = mongoClient.getDatabase("Athlete");
	private MongoDatabase coachDatabase = mongoClient.getDatabase("Coach");
	private MongoDatabase sessionsDatabase = mongoClient.getDatabase("Sessions");
	
	private MongoCollection coachCollection = coachDatabase.getCollection("Coach");
	private MongoCollection athleteCollection = athleteDatabase.getCollection("Athlete");
	
	
	
	
	public Database() {
	
	}
	
	
	public void createCoach(Coach coach) {
		
		//Checks if username is available 
		if ( ! this.coachUsernameExists(coach.getUsername()) ) {
		
		//Legger til et coach-dokument i Coach-databasen
		Document document = new Document("Username", coach.getUsername());
		document.append("Name",coach.getName());
		document.append("Athletes", coach.getAthletes());
		document.append("Requests", coach.getQueuedAthletes());
		coachCollection.insertOne(document);
		}
		
		else {
			System.out.println("Username in use. Could not add Coach");
		}
	}
	
	public void createAthlete(Athlete athlete) {
		
		//Checks if username is available 
		if ( ! this.athleteUsernameExists(athlete.getUsername()) ) {
		
		//Legger til et coach-dokument i Coach-databasen
			Document document = new Document("Username", athlete.getUsername());
			document.append("Name",athlete.getName());
			document.append("Coaches", athlete.getCoaches());
			document.append("Requests", athlete.getQueuedCoaches());
		athleteCollection.insertOne(document);
		}
		
		else {
			System.out.println("Username in use. Could not add Athlete");
		}
	}
	
	/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	 *  Session/Workout implementering
	 *  
	 *  
	 *  
	 *  
	 *@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 
	 
	 
	public void createSession(Session session) {
		
		//creates new collection if it does not exists
		MongoCollection userSessionsCollection = sessionsDatabase.getCollection(session.getAthlete().getUsername());
		//check to see if collection exists
		//if not, create collection
		
		if ( ! sessionIDExists(session.getAthlete(), session.getSessionID() )) {
			Document doc = new Document("SessionID", session.getSessionID());

			doc.append("Pulse", session.getPulse());
			userSessionsCollection.insertOne(doc);
			
		} else {
			System.out.println("sessionID in use. Could not create session");
		}
		
	}
	
	
	public boolean sessionIDExists(Athlete athlete, int sessionID) {
		
		
		MongoCollection userSessionsCollection = sessionsDatabase.getCollection(athlete.getUsername());
		Document found = (Document) userSessionsCollection.find(new Document("SessionID", sessionID)).first();
		
		
		
		if (found != null) {
			System.out.println("sessionID in use for particular athlete");
			return true;
		}

		return false;

	}
	*/
	public Coach getCoach(String usernam) {
		
		Document found = (Document) coachCollection.find(new Document("Username", usernam)).first();
		
		if(found == null) {
			System.out.println("no Coach goes by this username");
			return null;
		}
		
		Coach coach = new Coach(found.getString("Username"), found.getString("Name") , (List<String>) found.get("Athletes") , (List<String>) found.get("Requests"));
		
		return coach;
	}
	
	public Athlete getAthlete(String usernam) {
		
		
		Document found = (Document) athleteCollection.find(new Document("Username", usernam)).first();
		
		if (found == null) {
			System.out.println("no athlete with this username");
			return null;
			
		}
		
		Athlete athlete = new Athlete( found.getString("Username"), found.getString("Name"), (List<String>) found.get("Coaches") , (List<String>) found.get("Requests"));
		
		return athlete;
	}
	
	
	
	public boolean coachUsernameExists(String username) {
		
		//burde ha en liste over samtlige usernames? så kan man ha en generisk usernameExists-metode, og ikke to spesifikke
		
		Document found = (Document) coachCollection.find(new Document("Username", username)).first();
		
		if (found != null) {
			System.out.println("username in use");
			return true;
		}

		return false;

	}
	
	public boolean athleteUsernameExists(String username) {
		
		
		//burde ha en liste over samtlige usernames? så kan man ha en generisk usernameExists-metode, og ikke to spesifikke
		
		Document found = (Document) athleteCollection.find(new Document("Username", username)).first();
		
		if (found != null) {
			System.out.println("username in use");
			return true;
		}

		return false;

	}
	  

	public boolean isAthlete(String username) {
		
		//burde ha en liste over samtlige usernames? så kan man ha en generisk usernameExists-metode, og ikke to spesifikke
		
		Document found = (Document) athleteCollection.find(new Document("Username", username)).first();
		
		if (found != null) {
			System.out.println("username is Athlete");
			return true;
		}

		return false;

	}
		
}