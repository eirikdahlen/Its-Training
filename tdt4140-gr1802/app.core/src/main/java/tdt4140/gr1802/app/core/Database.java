package tdt4140.gr1802.app.core;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;

public class Database {
	
	//Connects to MongoDB 
	private String uri = "mongodb://theodorastrupwiik:starwars123!@pu-shard-00-00-wgffn.mongodb.net:27017,pu-shard-00-01-wgffn.mongodb.net:27017,pu-shard-00-02-wgffn.mongodb.net:27017/admin?replicaSet=PU-shard-0&ssl=true";
	private MongoClientURI clientURI = new MongoClientURI(uri);
	private MongoClient mongoClient = new MongoClient(clientURI);
	
	private MongoDatabase athleteDatabase;
	private MongoDatabase coachDatabase;
	private MongoDatabase workoutDatabase;
	private MongoDatabase dataDatabase;
	
	private MongoCollection coachCollection;
	private MongoCollection athleteCollection;
	private MongoCollection activityCollection;
	
	public Database() {
		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
		refresh();
	}
	
	// Create Coach in database
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
		} else {
			System.out.println("Username in use. Could not add Coach");
		}
	}
	

	// Create Athlete in database
	public void createAthlete(Athlete athlete) {
		//Checks if username is available 
		if ( ! this.usernameExists(athlete.getUsername()) ) {
			//Adds an athlete-document to athlete-collection
			Document document = new Document("Username", athlete.getUsername());
			document.append("Password", athlete.getPassword());
			document.append("Name",athlete.getName());
			document.append("maxHR", 0);
			document.append("Coaches", athlete.getCoaches());
			document.append("Requests", athlete.getQueuedCoaches());
			athleteCollection.insertOne(document);	
			System.out.println("athlete added to database");
		} else {
			System.out.println("Username in use. Could not add Athlete");
		}
	}
	

	
	// Create Workout in database
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
			System.out.println("Database vis gpx data: "+workout.getGpxData());
			doc.append("gpx", workout.getGpxData());
			
			//adds maxHR to athlete if it does not exist 
			Document found = (Document) athleteCollection.find(new Document("Username", workout.getAthlete().getUsername())).first();
			
			//TODO, fix, all athletes should have maxHR, so should be unnecessary to check  
			try {
			int HR = found.getInteger("maxHR");
			} catch (Exception e) {
				System.out.println("feil på maxHR");
				System.out.println("athlete does not have maxHR, adds from workout");
				this.addMaxHR(workout.getAthlete(), workout.getAthleteMaxHR() );	
			}
			
			doc.append("Visibility", workout.getVisibility());

			//pushes to database
			userWorkoutCollection.insertOne(doc);
			System.out.println("workout added to database");
		} else {
			System.out.println("datetime already in use for particular athlete.");
		}

	}
	

	
	public void addMaxHR(Athlete athlete, int maxHR) {
		Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
		
		Bson updatedvalue = new Document("maxHR", maxHR);
		Bson updateoperation = new Document("$set", updatedvalue);
		athleteCollection.updateOne(found, updateoperation);
		
	
		
	}
	
	// Returns the Coach from the database
	public Coach getCoach(String username) {
		Document found = (Document) coachCollection.find(new Document("Username", username)).first();
		
		if(found == null) {
			System.out.println("no Coach goes by this username");
			return null;
		}
		Coach coach = new Coach(found.getString("Username"), found.getString("Password") ,found.getString("Name") , (List<String>) found.get("Athletes") , (List<String>) found.get("Requests"));
	
		return coach;
	}
	
	// Returning the password to the user
	public String getPassword(String username) {
		if (! this.usernameExists(username) ) {
			System.out.println("username does not exist");
			return null;
		}
		if (athleteUsernameExists(username) ) {
			Document found = (Document) athleteCollection.find(new Document("Username", username)).first();
			String password = found.getString("Password");
			return password;
		} else {
			Document found = (Document) coachCollection.find(new Document("Username", username)).first();
			String password = found.getString("Password");
			return password;
		}
	}
	
	// Initializing variables, should be changed.
	public void refresh() {
		this.athleteDatabase = mongoClient.getDatabase("Athlete");
		this.coachDatabase = mongoClient.getDatabase("Coach");
		this.workoutDatabase = mongoClient.getDatabase("Workout");
		this.dataDatabase = mongoClient.getDatabase("Data");
		
		this.coachCollection = coachDatabase.getCollection("Coach");
		this.athleteCollection = athleteDatabase.getCollection("Athlete");
		this.activityCollection = dataDatabase.getCollection("ActivityTypes");
	}
	
	// Returns the Athlete from the database
	public Athlete getAthlete(String username) {
		
		Document found = (Document) athleteCollection.find(new Document("Username", username)).first();
		
		if (found == null) {
			System.out.println("no athlete with this username");
			return null;
		}
		Athlete athlete = new Athlete( found.getString("Username"), found.getString("Password"), found.getString("Name"), (List<String>) found.get("Coaches") , (List<String>) found.get("Requests"));
	
		//TODO: fikse opp i dette, legge til i konstruktør
		try {
			athlete.setMaxHR( found.getInteger("maxHR") );
		} catch (Exception e) {
			System.out.println("feil på get maxHR");
			//sets maxHR to 0 if it is not present in db
			athlete.setMaxHR(0);
			
		}
		return athlete;
	}
	
	public List<Athlete> getAllAthletes(){
		List<Athlete> athletes = new ArrayList<Athlete>();
		
		try (MongoCursor<Document> cursor = athleteCollection.find().iterator()) {
		    while (cursor.hasNext()) {
		    		Document doc = cursor.next();
		    		
		    		Athlete athlete = new Athlete(doc.getString("Username"), doc.getString("Password"), doc.getString("Name"), (List<String>) doc.get("Coaches") , (List<String>) doc.get("Requests"));
		    		athletes.add(athlete);
		    } 
		} catch(Exception e) {
			System.out.println("oi her var det litt smaafeil gitt");
			e.printStackTrace();
		}
		return athletes;
	}
	
	/*private List<LatLong> stringToLatLong(List<String> stringList){
		List<LatLong> latlonglist = new ArrayList<>();
		for (String s : stringList) {
			String[] temp = s.split(",");
			double l1 = Double.parseDouble(temp[0]);
			double l2 = Double.parseDouble(temp[1]);
			latlonglist.add(new LatLong(l1,l2));
		}
		return latlonglist;
	}*/
	
	// Returns the Workout from the database
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
		Workout workout = null;
		try {
			workout = new Workout( athlete, found.getString("date"),found.getString("type")  , found.getInteger("duration" )  , 
					found.getDouble("kilometres") , (List<String>) found.get("pulse"), found.getBoolean("Visibility"), null);
			workout.setGpxData((List<List<Double>>)found.get("gpx"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return workout;
	}
	
	// Returns a list of all the Workouts for a spesific Athlete
	public List<Workout> getAllWorkouts(Athlete athlete) {
		List<Workout> workouts = new ArrayList<Workout>();
		
		//finds the athlete of the workout, and accesses his workout-collection
		//automatically creates new collection if it does not exists
		MongoCollection userWorkoutCollection = workoutDatabase.getCollection(athlete.getUsername());
		
		try (MongoCursor<Document> cursor = userWorkoutCollection.find().iterator()) {
		
		    while (cursor.hasNext()) {
		    		Document doc = cursor.next();

		 
		        Workout workout = new Workout( athlete, doc.getString("date"),doc.getString("type")  , doc.getInteger("duration" )  , 
						doc.getDouble("kilometres") , (List<String>) doc.get("pulse"), doc.getBoolean("Visibility"), null );
		        workout.setGpxData((List<List<Double>>)doc.get("gpx"));
		        workouts.add(workout);
		    } 
		} catch(Exception e) {
			System.out.println("oi her var det litt smaafeil gitt");
			e.printStackTrace();
		}
		return workouts;
	}
	
	// Add Coach to the Athletes Coach-list
	public void addCoachToAthlete(Athlete athlete, String coachUsername) {
		////method for adding coach to athlete's coach-list
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
	
	// Add Athlete to the Coach Athletes-list
	public void addAthleteToCoach(Coach coach, String athleteUsername) {
		//method for adding athlete to coach's athlete-list
		Document found = (Document) coachCollection.find(new Document("Username", coach.getUsername() )).first();
		
		if (found == null) {
			System.out.println("no coach with this username");
		} else {
			List<String> athletes = (ArrayList<String>) found.get("Athletes");
		
			boolean alreadyPresent = false;
			for (String element : athletes) {
		    
			    if ( element.equals(athleteUsername) ) {
				    	System.out.println("athlete already in coach-list");
				    	alreadyPresent = true;
				    	break;
			    }
			}
			if (! alreadyPresent ) {
				//updates coach-list
				athletes.add(athleteUsername);
		
				//updates document with updated coach-array
				
				Document found2 = (Document) coachCollection.find(new Document("Username", coach.getUsername())).first();

				Bson updatedvalue = new Document("Athletes", athletes);
				Bson updateoperation = new Document("$set", updatedvalue);
				coachCollection.updateOne(found2, updateoperation);
				System.out.println("adding " + athleteUsername + " to "+coach.getUsername() + "'s athlete-list.");
			}
		}
	}
	
	
	// Add Athlete to Coaches RequestAthlete-list
	public void addRequestAthleteToCoach(Coach coach, String athleteUsername) {
		//method for adding athlete to coach's requests
		Document found = (Document) coachCollection.find(new Document("Username", coach.getUsername() )).first();
		
		if (found == null) {
			System.out.println("no coach with this username");
		} else {
			List<String> requestAthletes = (ArrayList<String>) found.get("Requests");
			
			boolean alreadyPresent = false;
			for (String element : requestAthletes) {
		    
			    if ( element.equals(athleteUsername) ) {
				    	System.out.println("athlete already in request-list");
				    	alreadyPresent = true;
				    	break;
			    }
			}
			if (! alreadyPresent ) {
				//updates coach-list
				requestAthletes.add(athleteUsername);
			
				//updates document with updated coach-array
				Document found2 = (Document) coachCollection.find(new Document("Username", coach.getUsername())).first();

				Bson updatedvalue = new Document("Requests", requestAthletes);
				Bson updateoperation = new Document("$set", updatedvalue);
				coachCollection.updateOne(found2, updateoperation);
				System.out.println("adding " + athleteUsername + " to "+coach.getUsername() + "'s request-list.");
			}
		}
	}
	
	// Add Atlete to Coaches RequestAthlete-list
	public void addRequestCoachToAthlete(Athlete athlete, String coachUsername) {
		//method for adding coach to athlete's requests
		Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername() )).first();
		
		if (found == null) {
			System.out.println("no athlete with this username");
		} else {
			List<String> requestCoaches = (ArrayList<String>) found.get("Requests");
		
			boolean alreadyPresent = false;
			for (String element : requestCoaches) {
		    
			    if ( element.equals(coachUsername) ) {
				    	System.out.println("athlete already in request-list");
				    	alreadyPresent = true;
				    	break;
			    }
			}
			if (! alreadyPresent ) {
				//updates coach-list
				requestCoaches.add(coachUsername);
		
				//updates document with updated coach-array
				
				Document found2 = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();

				Bson updatedvalue = new Document("Requests", requestCoaches);
				Bson updateoperation = new Document("$set", updatedvalue);
				athleteCollection.updateOne(found2, updateoperation);
				System.out.println("adding " + coachUsername + " to "+athlete.getUsername() + "'s request-list.");
			}	
		}
	}
	
	// Delete Coach from Atletes Coach-list
	public void deleteCoachForAthlete(Athlete athlete, String coachUsername) {
		//method for deleting coach from athlete's coach-list
		Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
		
		if (found == null) {
			System.out.println("no athlete with this username");
		} else {
			List<String> coaches = (ArrayList<String>) found.get("Coaches");
		
			boolean alreadyPresent = false;
			for (String element : coaches) {
		    
			    if ( element.equals(coachUsername) ) {
				    	alreadyPresent = true;
				    	break;
			    }
			}
			
			if ( alreadyPresent ) {
				//updates coach-list
				coaches.remove(coachUsername);
		
				//updates document with updated coach-array
				Document found2 = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
	
				Bson updatedvalue = new Document("Coaches", coaches);
				Bson updateoperation = new Document("$set", updatedvalue);
				athleteCollection.updateOne(found2, updateoperation);
				System.out.println("deleted " + coachUsername + " from "+athlete.getUsername() + "'s coach-list.");
	
			} else {
				System.out.println("coach not in athlete's coach-list");
			}
		}
	}
	
	// Delete Athlete from Coaches Athletes-list
	public void deleteAthleteForCoach(Coach coach, String athleteUsername) {
		//method for adding athlete to coach's athlete-listt
		System.out.println("inni database");
		System.out.println(coach.getUsername() + " "+athleteUsername);
		
		Document found = (Document) coachCollection.find(new Document("Username", coach.getUsername() )).first();
		
		if (found == null) {
			System.out.println("no coach with this username");
		} else {
			List<String> athletes = (ArrayList<String>) found.get("Athletes");
	
			boolean alreadyPresent = false;
			for (String element : athletes) {
			    if ( element.equals(athleteUsername) ) {
				    	alreadyPresent = true;
				    	break;
			    }
			}
			if (alreadyPresent ) {
				//updates coach-list
				athletes.remove(athleteUsername);

				//updates document with updated coach-array
				Document found2 = (Document) coachCollection.find(new Document("Username", coach.getUsername())).first();

				Bson updatedvalue = new Document("Athletes", athletes);
				Bson updateoperation = new Document("$set", updatedvalue);
				coachCollection.updateOne(found2, updateoperation);
				System.out.println("deleting " + athleteUsername + " from "+coach.getUsername() + "'s athlete-list.");
			} else {
				System.out.println("Athlete not in coach's athelte-list");
			}
		}
	}
	
	// Delete Coach from Athletes RequestCoach-list
	public void deleteCoachRequestForAthlete(Athlete athlete, String coachUsername) {
		//method for deleting coach from athlete's coach-list
		Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
		
		if (found == null) {
			System.out.println("no athlete with this username");
		} else {
			List<String> coaches = (ArrayList<String>) found.get("Requests");
	
			boolean alreadyPresent = false;
			for (String element : coaches) {
			    if ( element.equals(coachUsername) ) {
				    	alreadyPresent = true;
				    	break;
			    }
			}
			if ( alreadyPresent ) {
				//updates coach-list
				coaches.remove(coachUsername);
				//updates document with updated coach-array
				
				Document found2 = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
	
				Bson updatedvalue = new Document("Requests", coaches);
				Bson updateoperation = new Document("$set", updatedvalue);
				athleteCollection.updateOne(found2, updateoperation);
				System.out.println("deleted " + coachUsername + " from "+athlete.getUsername() + "'s request.");
			} else {
				System.out.println("coach not in athlete's request-list");
			}		
		}	
	}
	
	// Delete Athlete from Coaches RequestAthlete-list
	public void deleteAthleteRequestForCoach(Coach coach, String athleteUsername) {
		//method for adding athlete to coach's athlete-list
		
		Document found = (Document) coachCollection.find(new Document("Username", coach.getUsername() )).first();
			
		if (found == null) {
			System.out.println("no coach with this username");
		} else {
			List<String> athletes = (ArrayList<String>) found.get("Requests");
			
			boolean alreadyPresent = false;
			for (String element : athletes) {
			    if ( element.equals(athleteUsername) ) {
				    	alreadyPresent = true;
				    	break;
			    }
			}	
			if (alreadyPresent ) {
				//updates coach-list
				athletes.remove(athleteUsername);
		
				//updates document with updated coach-array
					
				Document found2 = (Document) coachCollection.find(new Document("Username", coach.getUsername())).first();

				Bson updatedvalue = new Document("Requests", athletes);
				Bson updateoperation = new Document("$set", updatedvalue);
				coachCollection.updateOne(found2, updateoperation);
				System.out.println("deleting " + athleteUsername + " from "+coach.getUsername() + "'s request-list.");
			} else {
				System.out.println("Athlete not in coach's request-list");
			}	
		}	
	}
	
	// Returns a list with all the Coaches usernames for the Athlete
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
	
	// Returns a list with all the Athletes usernames for the Coach
	public List<String> getAthleteForCoach(Coach coach) {
		Document found = (Document) coachCollection.find(new Document("Username", coach.getUsername())).first();
		System.out.println("HENTER ATHLETES FOR COACH");
		if (found == null) {
			System.out.println("no coach with this username");
			return null;
		} else {
			List<String> athletes = (ArrayList<String>) found.get("Athletes");
			return athletes;
		}
	}
	
	// Returns a list with all the AthleteRequests usernames for the Coach
	public List<String> getRequestsForCoach(Coach coach) {
		Document found = (Document) coachCollection.find(new Document("Username", coach.getUsername())).first();
		if (found == null) {
			System.out.println("no coach with this username");
			return null;
		} else {
			List<String> requests = (ArrayList<String>) found.get("Requests");
			return requests;
		}
	}
	
	// Returns a list with all the CoachRequests usernames for the Athlete
	public List<String> getRequestsForAthlete(Athlete athlete) {
		Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
		
		if (found == null) {
			System.out.println("no athlete with this username");
			return null;
		} else {
			List<String> coaches = (ArrayList<String>) found.get("Requests");
			return coaches;
		}
	}
	
	// Check if athlete has a Workout at the datetime
	public boolean datetimeExists(Athlete athlete, String date) {
		MongoCollection userWorkoutCollection = workoutDatabase.getCollection(athlete.getUsername());
		Document found = (Document) userWorkoutCollection.find(new Document("date", date)).first();
		
		if (found != null) {
			System.out.println("datetime in use for particular athlete");
			return true;
		}
		return false;
	}
	
	// Check if the username exists
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
	
	// Check if the Coachusername exists
	public boolean coachUsernameExists(String username) {	
		Document found = (Document) coachCollection.find(new Document("Username", username)).first();
		if (found != null) {
			return true;
		}
		return false;
	}
	
	// Check if the Athleteusername exists
	public boolean athleteUsernameExists(String username) {
		Document found = (Document) athleteCollection.find(new Document("Username", username)).first();

		if (found != null) {
			return true;
		}
		return false;
	}
	
	// Check if the user is an Athlete
	public boolean isAthlete(String username) {
		Document found = (Document) athleteCollection.find(new Document("Username", username)).first();
		if (found != null) {
			System.out.println("username is Athlete");
			return true;
		}
		return false;
	}
	
	public void setWorkoutVisibility(boolean bool, Workout workout, Athlete athlete) {
		MongoCollection userWorkoutCollection = workoutDatabase.getCollection(athlete.getUsername());
		Document found = (Document) userWorkoutCollection.find(new Document("date", workout.getDateString())).first();
		
		Bson updatedvalue = new Document("Visibility", bool);
		Bson updateoperation = new Document("$set", updatedvalue);
		userWorkoutCollection.updateOne(found, updateoperation);
	}
	
	// **** ACTIVITIES TAB ****
	public ArrayList<String> getAllActivities(){
		ArrayList<String> activities = new ArrayList<String>();
		
		try (MongoCursor<Document> cursor = activityCollection.find().iterator()) {
		    while (cursor.hasNext()) {
		    		Document doc = cursor.next();
		    		
		    		String a = doc.getString("type");
		    		activities.add(a);
		    } 
		} catch(Exception e) {
			System.out.println("oi feil i getAllActivities");
			e.printStackTrace();
		}
		return activities;
	}
	
	public int getNrOfWorkoutsForAthlete(Athlete athlete, String activity) {
		int count = 0;
		MongoCollection userWorkoutCollection = workoutDatabase.getCollection(athlete.getUsername());
		
		try (MongoCursor<Document> cursor = userWorkoutCollection.find().iterator()) {
		    while (cursor.hasNext()) {
		    		Document doc = cursor.next();
		    		
		    		String act = doc.getString("type");
		    		if (act.equals(activity)) {
		    			count++;
		    		}
		    } 
		} catch(Exception e) {
			System.out.println("oi her var det litt smaafeil gitt");
			e.printStackTrace();
		}
		return count;
	}
	
	public List<Athlete> getAthletesForActivity(String activity){
		List<Athlete> allAthletes = getAllAthletes();
		List<Athlete> activityAthletes = new ArrayList<>();
		
		for (Athlete ath : allAthletes) {
			
			MongoCollection userWorkoutCollection = workoutDatabase.getCollection(ath.getUsername());
			Document found = (Document) userWorkoutCollection.find(new Document("type", activity)).first();
			
			if (found != null) {
				activityAthletes.add(ath);
			}
		}
		return activityAthletes;
	}
	
	public List<Workout> getWorkoutsForActivity(String activity){
		List<Athlete> allAthletes = getAllAthletes();
		List<Workout> activityWorkouts = new ArrayList<>();
		
		for (Athlete ath : allAthletes) {
			MongoCollection userWorkoutCollection = workoutDatabase.getCollection(ath.getUsername());
			
			try (MongoCursor<Document> cursor = userWorkoutCollection.find().iterator()) {
			    while (cursor.hasNext()) {
			    		Document doc = cursor.next();
			    		if (doc.getString("type").equals(activity)) {
			    			Workout workout = new Workout( ath, doc.getString("date"),doc.getString("type")  , doc.getInteger("duration" )  , 
									doc.getDouble("kilometres") , (List<String>) doc.get("pulse"), doc.getBoolean("Visibility") , null);
					        
			    			activityWorkouts.add(workout);
			    		}
			    } 
			} catch(Exception e) {
				System.out.println("oi her var det litt smaafeil gitt");
				e.printStackTrace();
			}
		}
		return activityWorkouts;	
	}
	
	public static void main(String[] args) {
		Database db = new Database();
		System.out.println(db.getAthletesForActivity("RUNNING"));
	}
}