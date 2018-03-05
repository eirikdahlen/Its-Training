package tdt4140.gr1802.app.core;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
	
	//Connects to MongoDB 
	private String uri = "mongodb://theodorastrupwiik:starwars123!@pu-shard-00-00-wgffn.mongodb.net:27017,pu-shard-00-01-wgffn.mongodb.net:27017,pu-shard-00-02-wgffn.mongodb.net:27017/admin?replicaSet=PU-shard-0&ssl=true";
	private MongoClientURI clientURI = new MongoClientURI(uri);
	private MongoClient mongoClient = new MongoClient(clientURI);
	
	private MongoDatabase athleteDatabase = mongoClient.getDatabase("Athlete");
	private MongoDatabase coachDatabase = mongoClient.getDatabase("Coach");
	private MongoDatabase workoutDatabase = mongoClient.getDatabase("Workout");
	
	private MongoCollection coachCollection = coachDatabase.getCollection("Coach");
	private MongoCollection athleteCollection = athleteDatabase.getCollection("Athlete");
	
	
	
	
	public Database() {
		
		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
	
	}
	
	
	public void createCoach(Coach coach) {
		
		//Checks if username is available 
		if ( ! this.usernameExists(coach.getUsername()) ) {
		
		//Adds a coach-document to Coach-collection
		Document document = new Document("Username", coach.getUsername());
		document.append("Password", coach.getPassword());
		document.append("Name",coach.getName());
		document.append("Athletes", coach.getAthletes());
		document.append("Requests", coach.getQueuedAthletes());
		coachCollection.insertOne(document);
		System.out.println("coach added to database");
		}
		
		else {
			System.out.println("Username in use. Could not add Coach");
		}
	}

	
	public void createAthlete(Athlete athlete) {
		
		//Checks if username is available 
		if ( ! this.usernameExists(athlete.getUsername()) ) {
		
		//Adds an athlete-document to athlete-collection
			Document document = new Document("Username", athlete.getUsername());
			document.append("Password", athlete.getPassword());
			document.append("Name",athlete.getName());
			document.append("Coaches", athlete.getCoaches());
			document.append("Requests", athlete.getQueuedCoaches());
			athleteCollection.insertOne(document);
			
			System.out.println("athlete added to database");
		}
		
		else {
			System.out.println("Username in use. Could not add Athlete");
		}
	}
	
	
	public void createWorkout(Workout workout) {
		
		
		//checks if datetime is available for particular athlete 
		if ( ! datetimeExists(workout.getAthlete(), workout.getDateString())) {
			
			//finds the athlete of the workout, and accesses his workout-collection
			//automatically creates new collection if it does not exists
			MongoCollection userWorkoutCollection = workoutDatabase.getCollection(workout.getAthlete().getUsername());
		
			//creates document
			Document doc = new Document("date", workout.getDateString());
			
			//adds workout-attributes
			doc.append("type", workout.getType());
			doc.append("duration", workout.getDuration());
			doc.append("kilometres", workout.getKilometres());

			doc.append("pulse", workout.getPulsList());

			//pushes to database
			userWorkoutCollection.insertOne(doc);
			System.out.println("workout added to database");
			
		} else {
			System.out.println("datetime already in use for particular athlete.");
		}

	}
	
	
	public Coach getCoach(String username) {
		
		Document found = (Document) coachCollection.find(new Document("Username", username)).first();
		
		if(found == null) {
			System.out.println("no Coach goes by this username");
			return null;
		}
		
		Coach coach = new Coach(found.getString("Username"), found.getString("Password") ,found.getString("Name") , (List<String>) found.get("Athletes") , (List<String>) found.get("Requests"));
		
		return coach;
	}
	
	public String getPassword(String username) {
		
		if (! this.usernameExists(username) ) {
			System.out.println("username does not exist");
			return null;
		}
		
		if (athleteUsernameExists(username) ) {
			Document found = (Document) athleteCollection.find(new Document("Username", username)).first();
			String password = found.getString("Password");
			return password;
		}
		
		else {
			Document found = (Document) coachCollection.find(new Document("Username", username)).first();
			String password = found.getString("Password");
			return password;
			
		}
		
		 
		
	}
	
	
	public Athlete getAthlete(String username) {
		
		
		Document found = (Document) athleteCollection.find(new Document("Username", username)).first();
		
		if (found == null) {
			System.out.println("no athlete with this username");
			return null;
			
		}
		
		Athlete athlete = new Athlete( found.getString("Username"), found.getString("Password"), found.getString("Name"), (List<String>) found.get("Coaches") , (List<String>) found.get("Requests"));
		
		return athlete;
	}
	
	
	public Workout getWorkout(Athlete athlete, String date) {
		
		
		//finds the athlete of the workout, and accesses his workout-collection
		//automatically creates new collection if it does not exists
		MongoCollection userWorkoutCollection = workoutDatabase.getCollection(athlete.getUsername());

		
		Document found = (Document) userWorkoutCollection.find(new Document("date", date)).first();
		
		if (found == null) {
			System.out.println("no workout with this datetime");
			return null;
			
		}
			
		//creates workout-object
		Workout workout = new Workout( athlete, found.getString("date"),found.getString("type")  , found.getInteger("duration" )  , 
				found.getDouble("kilometres") , (List<String>) found.get("pulse") );
		
		
		return workout;
	}
	
	
	
	public List<Workout> getAllWorkouts(Athlete athlete) {
		
		List<Workout> workouts = new ArrayList<Workout>();
		
		//finds the athlete of the workout, and accesses his workout-collection
		//automatically creates new collection if it does not exists
		MongoCollection userWorkoutCollection = workoutDatabase.getCollection(athlete.getUsername());
		
		
		try (MongoCursor<Document> cursor = userWorkoutCollection.find().iterator()) {
		
		    while (cursor.hasNext()) {
		    		Document doc = cursor.next();
		    		System.out.println(doc.get("date"));
		    		System.out.println(doc.get("pulse"));
		    		
		        
		        Workout workout = new Workout( athlete, doc.getString("date"),doc.getString("type")  , doc.getInteger("duration" )  , 
						doc.getDouble("kilometres") , (List<String>) doc.get("pulse") );
		        
		        System.out.println("test2");
		        workouts.add(workout);
		    } 
		} catch(Exception e) {
			System.out.println("oi her var det litt smaafeil gitt");
			e.printStackTrace();
		}
		
			
		return workouts;
	}
	
	public void addCoachToAthlete(Athlete athlete, String coachUsername) {
		//method for adding coach to athlete's coach-list
		
		
		Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
		
		if (found == null) {
			System.out.println("no athlete with this username");
		} else {
			List<String> coaches = (ArrayList<String>) found.get("Coaches");
			
	
			boolean alreadyPresent = false;
			for (String element : coaches) {
		    
			    if ( element.equals(coachUsername) ) {
			    	System.out.println("coach already in athlete-list");
			    	alreadyPresent = true;
			    	break;
			    }
		}
			
			if (! alreadyPresent ) {
				//updates coach-list
				coaches.add(coachUsername);
				

				//updates document with updated coach-array
				
				Document found2 = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();

				Bson updatedvalue = new Document("Coaches", coaches);
				Bson updateoperation = new Document("$set", updatedvalue);
				athleteCollection.updateOne(found2, updateoperation);
				System.out.println("adding " + coachUsername + " to "+athlete.getUsername() + "'s coach-list.");
	
		}
			
	}
}
	
	public List<String> getCoachesForAthlete(Athlete athlete) {
		Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
		
		if (found == null) {
			System.out.println("no athlete with this username");
			return null;
		} else {
			List<String> coaches = (ArrayList<String>) found.get("Coaches");
			return coaches;
	
			
		}
	}
	

	
	
	
	//TODO add "addCoach"-metode
	//TODO add "addAthlete"-metode
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean datetimeExists(Athlete athlete, String date) {
	
	
	MongoCollection userWorkoutCollection = workoutDatabase.getCollection(athlete.getUsername());
	Document found = (Document) userWorkoutCollection.find(new Document("date", date)).first();
	
	
	
	if (found != null) {
		System.out.println("datetime in use for particular athlete");
		return true;
	}

	return false;

	}
	
	
	public boolean usernameExists(String username) {
		//returns true if username exists in database
	
		if (coachUsernameExists(username)) {
			System.out.println("Username in use by Coach");
			return true;
		}
		
		if (athleteUsernameExists(username)) {
			System.out.println("Username in use by Athlete");
			return true;
		}

		return false;
	}
	
	
	public boolean coachUsernameExists(String username) {
	
		
		Document found = (Document) coachCollection.find(new Document("Username", username)).first();
		
		if (found != null) {

			return true;
		}
		return false;
	}
	
	
	public boolean athleteUsernameExists(String username) {
	
		
		Document found = (Document) athleteCollection.find(new Document("Username", username)).first();
		
		if (found != null) {
			return true;
		}

		
		return false;

	}

	
	public boolean isAthlete(String username) {

		
		Document found = (Document) athleteCollection.find(new Document("Username", username)).first();
		
		if (found != null) {
			System.out.println("username is Athlete");
			return true;
		}

		return false;

	}
	
	
		
}