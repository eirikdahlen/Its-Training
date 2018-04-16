
package tdt4140.gr1802.app.core;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

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
	private MongoCollection quotesCollection; 
	
	public Database() {
		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
		refresh();
	}
	
	// Create Coach in database
	public void createCoach(Coach coach) throws Exception {
		
		if ( ! this.usernameExists(coach.getUsername()) ) {
			//Adds a coach-document to Coach-collection
			Document document = new Document("Username", coach.getUsername());
			document.append("Password", coach.getPassword());
			document.append("Name",coach.getName());
			document.append("Athletes", coach.getAthletes());
			document.append("Requests", coach.getQueuedAthletes());
			document.append("Notes", coach.getNotes());
			coachCollection.insertOne(document);
			System.out.println("coach added to database");
		} else {
			System.out.println("Username in use. Could not add Coach");
		}
		
		
//		List<String> list = new ArrayList<String>();
//		list.add(coach.getUsername());
//		list.add(coach.getPassword());
//		list.add(coach.getName());
//		list.add(String.join("_", coach.getAthletes()));
//		list.add(String.join("_", coach.getQueuedAthletes()));
//		
//				
//		HashMap<String, String> myMap = new HashMap<String, String>(); 
//		myMap.put("name", String.join("<", list));
//		BackendConnector.makeRequest(myMap, "createCoach");
	}

	// Create Athlete in database
	public void createAthlete(Athlete athlete) throws Exception {
		//Checks if username is available 
		if ( ! this.usernameExists(athlete.getUsername()) ) {
			//Adds an athlete-document to athlete-collection
			Document document = new Document("Username", athlete.getUsername());
			document.append("Password", athlete.getPassword());
			document.append("Name",athlete.getName());
			document.append("maxHR", 180);
			document.append("Coaches", athlete.getCoaches());
			document.append("Requests", athlete.getQueuedCoaches());
			
			//Adds empty sleep-data list to athlete 
			List<List<String>> sleepdata = new ArrayList <List<String>> () ;
			document.append("Sleepdata", sleepdata);
			
			
			
			athleteCollection.insertOne(document);	
			System.out.println("athlete added to database");
		} else {
			System.out.println("Username in use. Could not add Athlete");
		}
	}
	

	
	// Create Workout in database
	public void createWorkout(Workout workout) throws Exception {
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
public Coach getCoach(String username) throws Exception {
    	
	HashMap<String, String> myMap = new HashMap<String, String>(); 
		
	myMap.put("name", username);
	
	JSONObject objektet = BackendConnector.makeRequest(myMap, "getCoach");
	JSONArray array = (JSONArray) objektet.get("Athletes");
	JSONArray array2 = (JSONArray) objektet.get("Requests");
	List<String> athleteList = new ArrayList<String>();
	List<String> requestList = new ArrayList<String>();
	
	for (int i=0; i<array.length(); i++) {
	    athleteList.add( array.getString(i) );
	}
	 
	for (int i=0; i<array2.length(); i++) {
	    requestList.add( array2.getString(i) );
	}
	
	Coach coach = new Coach(objektet.get("Username").toString(), objektet.get("Password").toString() , objektet.get("Name").toString() , athleteList, requestList);
	return coach;
	    	
}
    	
    
	
	// Returning the password to the user
public String getPassword(String username) throws Exception {
	
 	HashMap<String, String> myMap = new HashMap<String, String>(); 
 	myMap.put("name", username);
 	String pass;
    JSONObject objektet = BackendConnector.makeRequest(myMap, "getPass");
    pass = objektet.get("Password").toString();
    return pass;
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
	

    public Athlete getAthlete(String username) throws Exception {

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
		
		//TODO: fikse opp i dette, legge til i konstruktør
		//for sleepdata
		try {
			athlete.setSleepData((List<List<String>>) found.get("Sleepdata"));
		} catch (Exception e) {
			System.out.println("Fant ikke sleepdata");
			athlete.setSleepData(  new ArrayList <List<String>> ()    );
			
		}
		
		
		
		return athlete;
    	
    }
	
	public List<Athlete> getAllAthletes() throws Exception{
		List<Athlete> athletes = new ArrayList<Athlete>();
		HashMap<String, String> myMap = new HashMap<String, String>(); 
		myMap.put("name", "McGooch");
		JSONObject obj = BackendConnector.makeRequest(myMap, "getAllAthletes");
		JSONArray jArray = obj.getJSONArray("arr");
		for(int i = 0; i < jArray.length(); i++) {
			JSONObject objektet = jArray.getJSONObject(i);
			String c = objektet.get("Coaches").toString();
		    String[] str = c.split("_");
		    String r = objektet.get("Requests").toString();
		    String[] str2 = r.split("_");
		    List<String> coachList = Arrays.asList(str);   //new ArrayList<String>();
		    List<String> requests = Arrays.asList(str2);
		    
		    Athlete athl = new Athlete(objektet.get("Username").toString(), objektet.get("Passord").toString(), objektet.get("Name").toString(), coachList, requests);
		    athl.setMaxHR(Integer.parseInt("190"));
		    athletes.add(athl);
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
//	public Workout getWorkout(Athlete athlete, String date) {
//		//finds the athlete of the workout, and accesses his workout-collection
//		//automatically creates new collection if it does not exists
//		MongoCollection userWorkoutCollection = workoutDatabase.getCollection(athlete.getUsername());
//		Document found = (Document) userWorkoutCollection.find(new Document("date", date)).first();
//		
//		if (found == null) {
//			System.out.println("no workout with this datetime");
//			return null;
//		}
//		//creates workout-object
//		Workout workout = null;
//		try {
//			workout = new Workout( athlete, found.getString("date"),found.getString("type")  , found.getInteger("duration" )  , 
//					found.getDouble("kilometres") , (List<String>) found.get("pulse"), found.getBoolean("Visibility"), (List<List<Double>>)found.get("gpx"));
//			workout.setGpxData((List<List<Double>>)found.get("gpx"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return workout;
//	}
	



public Workout getWorkout(Athlete athl, String date) throws Exception {
	
 	HashMap<String, String> myMap = new HashMap<String, String>(); 
 	myMap.put("name", athl.getUsername());
 	myMap.put("date", date);
 	
    JSONObject objektet = BackendConnector.makeRequest(myMap, "getWorkout");
    System.out.println("get workout, rett for puls");
    //System.out.println(objektet.get("Pulse"));
    String pulseString = objektet.get("Pulse").toString();
    String[] pulseStr = pulseString.split("_");
    List<String> pulse = Arrays.asList(pulseStr);
    boolean vis = true;
    if(objektet.get("Visibility").toString().equals("false")) {
    	vis = false;
    }
    
    
    String gpxString = objektet.get("GpxData").toString();
    if(gpxString.equals("null")) {
    		InputStream gpxFilepath = null;
    		Workout wo = new Workout(athl, objektet.getString("Date"), objektet.getString("Type"),Integer.parseInt(objektet.get("Duration").toString()), Double.parseDouble(objektet.get("Kilometres").toString()), pulse, vis, gpxFilepath );
		return wo;
    }
    List<String> gpxArray = Arrays.asList(gpxString.split("X"));
    List<List<Double>> gpxData = new ArrayList<List<Double>>();
    
    
    
    for(String s : gpxArray) {
    	List<Double> gpxPair = new ArrayList<Double>();
    
    	
    	gpxPair.add(Double.parseDouble(s.split("_")[0] ));
    	gpxPair.add(Double.parseDouble(s.split("_")[1]   ));
    	gpxData.add(gpxPair);
    }
    
    
    Workout wo = new Workout(athl, objektet.getString("Date"), objektet.getString("Type"),Integer.parseInt(objektet.get("Duration").toString()), Double.parseDouble(objektet.get("Kilometres").toString()), pulse, vis, gpxData );
    		

    return wo;
}

	
    //Returns a list of all the Workouts for a spesific Athlete
//	public List<Workout> getAllWorkouts(Athlete athlete) {
//		List<Workout> workouts = new ArrayList<Workout>();
//		
//		//finds the athlete of the workout, and accesses his workout-collection
//		//automatically creates new collection if it does not exists
//		MongoCollection userWorkoutCollection = workoutDatabase.getCollection(athlete.getUsername());
//		
//		try (MongoCursor<Document> cursor = userWorkoutCollection.find().iterator()) {
//		
//		    while (cursor.hasNext()) {
//		    		Document doc = cursor.next();
//
//		 
//		        Workout workout = new Workout( athlete, doc.getString("date"),doc.getString("type")  , doc.getInteger("duration" )  , 
//						doc.getDouble("kilometres") , (List<String>) doc.get("pulse"), doc.getBoolean("Visibility"), (List<List<Double>>)doc.get("gpx") );
//		        workout.setGpxData((List<List<Double>>)doc.get("gpx"));
//		        workouts.add(workout);
//		    } 
//		} catch(Exception e) {
//			System.out.println("oi her var det litt smaafeil gitt");
//			e.printStackTrace();
//		}
//		return workouts;
//	}

////
public List<Workout> getAllWorkouts(Athlete athl) throws Exception {
	//System.out.println("inni get all workouts");
 	HashMap<String, String> myMap = new HashMap<String, String>(); 
 	myMap.put("name", athl.getUsername());
 	//TestBackendConnector co = new TestBackendConnector();
 	
    JSONObject obj = BackendConnector.makeRequest(myMap, "getAllWorkouts");
    JSONArray jArray = obj.getJSONArray("arr");
    List<Workout> woList = new ArrayList<Workout>();
    for(int i = 0; i < jArray.length(); i++) {
    	JSONObject objektet = jArray.getJSONObject(i);
    	String pulseString = objektet.get("Pulse").toString();
        String[] pulseStr = pulseString.split("_");
        List<String> pulse = Arrays.asList(pulseStr);
        boolean vis = true;
        if(objektet.get("Visibility").toString().equals("false")) {
        	vis = false;
        }
        
        
        String gpxString = objektet.get("GpxData").toString();
        if(gpxString.equals("null")) {
        		InputStream gpxFilepath = null;
        		String str = objektet.get("Kilometres").toString();
        		System.out.println(str);
        		Double sinkovic = Double.parseDouble(str);
        		System.out.println(sinkovic);
        		
        		Workout wo = new Workout(athl, objektet.getString("Date"), objektet.getString("Type"),Integer.parseInt(objektet.get("Duration").toString()), Double.parseDouble(objektet.get("Kilometres").toString()), pulse, vis, gpxFilepath );
    		 woList.add(wo);
        }else {
        List<String> gpxArray = Arrays.asList(gpxString.split("X"));
        List<List<Double>> gpxData = new ArrayList<List<Double>>();

        for(String s : gpxArray) {
        	List<Double> gpxPair = new ArrayList<Double>();

        	gpxPair.add(Double.parseDouble(s.split("_")[0] ));
        	gpxPair.add(Double.parseDouble(s.split("_")[1]   ));
        	gpxData.add(gpxPair);
        }
        Workout wo = new Workout(athl, objektet.getString("Date"), objektet.getString("Type"),Integer.parseInt(objektet.get("Duration").toString()), Double.parseDouble(objektet.get("Kilometres").toString()), pulse, vis, gpxData );
        woList.add(wo);
        }
    }
    
    return woList;
}

//
//	
	// Add Coach to the Athletes Coach-list
	public void addCoachToAthlete(Athlete athlete, String coach) throws Exception {
		
	////method for adding coach to athlete's coach-list
			Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
			
			if (found == null) {
				System.out.println("no athlete with this username");
			} else {
				List<String> coaches = (ArrayList<String>) found.get("Coaches");
				boolean alreadyPresent = false;
				
				for (String element : coaches) {
				    if ( element.equals(coach) ) {
					    	System.out.println("coach already in athlete-list");
					    	alreadyPresent = true;
					    	break;
				    }
				}
				if (! alreadyPresent ) {
					//updates coach-list
					coaches.add(coach);

					//updates document with updated coach-array
					
					Document found2 = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();

					Bson updatedvalue = new Document("Coaches", coaches);
					Bson updateoperation = new Document("$set", updatedvalue);
					athleteCollection.updateOne(found2, updateoperation);
					System.out.println("adding " + coach + " to "+athlete.getUsername() + "'s coach-list.");
				}	
			}
	}
	
	// Add Athlete to the Coach Athletes-list
	public void addAthleteToCoach(Coach coach, String athlete) throws Exception {
		
		//method for adding athlete to coach's athlete-list
				Document found = (Document) coachCollection.find(new Document("Username", coach.getUsername() )).first();
				
				if (found == null) {
					System.out.println("no coach with this username");
				} else {
					List<String> athletes = (ArrayList<String>) found.get("Athletes");
				
					boolean alreadyPresent = false;
					for (String element : athletes) {
				    
					    if ( element.equals(athlete )) {
						    	System.out.println("athlete already in coach-list");
						    	alreadyPresent = true;
						    	break;
					    }
					}
					if (! alreadyPresent ) {
						//updates coach-list
						athletes.add(athlete);
				
						//updates document with updated coach-array
						
						Document found2 = (Document) coachCollection.find(new Document("Username", coach.getUsername())).first();

						Bson updatedvalue = new Document("Athletes", athletes);
						Bson updateoperation = new Document("$set", updatedvalue);
						coachCollection.updateOne(found2, updateoperation);
						System.out.println("adding " + athlete + " to "+coach.getUsername() + "'s athlete-list.");
					}
				}	}
	
	
	// Add Athlete to Coaches RequestAthlete-list
	public void addRequestAthleteToCoach(Coach coach, String athlete) throws Exception {
		//method for adding athlete to coach's requests
				Document found = (Document) coachCollection.find(new Document("Username", coach.getUsername() )).first();
				
				if (found == null) {
					System.out.println("no coach with this username");
				} else {
					List<String> requestAthletes = (ArrayList<String>) found.get("Requests");
					
					boolean alreadyPresent = false;
					for (String element : requestAthletes) {
				    
					    if ( element.equals(athlete) ) {
						    	System.out.println("athlete already in request-list");
						    	alreadyPresent = true;
						    	break;
					    }
					}
					if (! alreadyPresent ) {
						//updates coach-list
						requestAthletes.add(athlete);
					
						//updates document with updated coach-array
						Document found2 = (Document) coachCollection.find(new Document("Username", coach.getUsername())).first();

						Bson updatedvalue = new Document("Requests", requestAthletes);
						Bson updateoperation = new Document("$set", updatedvalue);
						coachCollection.updateOne(found2, updateoperation);
						System.out.println("adding " + athlete + " to "+coach.getUsername() + "'s request-list.");
					}
				}
	}
	
	// Add Atlete to Coaches RequestAthlete-list
	public void addRequestCoachToAthlete(Athlete athlete, String coach) throws Exception {
		
		
		//method for adding coach to athlete's requests
				Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername() )).first();
				
				if (found == null) {
					System.out.println("no athlete with this username");
				} else {
					List<String> requestCoaches = (ArrayList<String>) found.get("Requests");
				
					boolean alreadyPresent = false;
					for (String element : requestCoaches) {
				    
					    if ( element.equals(coach) ) {
						    	System.out.println("athlete already in request-list");
						    	alreadyPresent = true;
						    	break;
					    }
					}
					if (! alreadyPresent ) {
						//updates coach-list
						requestCoaches.add(coach);
				
						//updates document with updated coach-array
						
						Document found2 = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();

						Bson updatedvalue = new Document("Requests", requestCoaches);
						Bson updateoperation = new Document("$set", updatedvalue);
						athleteCollection.updateOne(found2, updateoperation);
						System.out.println("adding " + coach + " to "+athlete.getUsername() + "'s request-list.");
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
	
	// *****************
	//****************
	//*****************
	
	
	
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
	public List<String> getRequestsForCoach(Coach coach) throws Exception {
		
		//String str = coach.getUsername();
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", coach.getUsername());
	    JSONObject objektet = BackendConnector.makeRequest(myMap, "getRequestsForCoach");
		List<String> reqs = Arrays.asList(objektet.get("Requests").toString().split("_"));
	    
		return reqs;
	}

	
	// Returns a list with all the CoachRequests usernames for the Athlete
public List<String> getRequestsForAthlete(Athlete athl) throws Exception {
		
		//String str = coach.getUsername();
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", athl.getUsername());
	    JSONObject objektet = BackendConnector.makeRequest(myMap, "getRequestsForAthlete");
		List<String> reqs = Arrays.asList(objektet.get("Requests").toString().split("_"));
	    
		return reqs;
	}

	
	// Check if athlete has a Workout at the datetime
public boolean datetimeExists(Athlete athl, String date) throws Exception {
	
	//String str = coach.getUsername();
	HashMap<String, String> myMap = new HashMap<String, String>();
	myMap.put("name", athl.getUsername());
	myMap.put("date", date);
    JSONObject objektet = BackendConnector.makeRequest(myMap, "dateTimeExists");
	String reqs = (objektet.get("value").toString());
    if(reqs.equals("true")) {
    		return true;
    }
	
	return false;
}
	
	// Check if the username exists
	public boolean usernameExists(String username) throws Exception {
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", username);
	    JSONObject objektet = BackendConnector.makeRequest(myMap, "usernameExists");
		String reqs = (objektet.get("value").toString());
	    if(reqs.equals("true")) {
	    		return true;
	    }
		
		return false;
	}
	
	// Check if the Coachusername exists
	public boolean coachUsernameExists(String username) throws Exception {	
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", username);
	    JSONObject objektet = BackendConnector.makeRequest(myMap, "coachUsernameExists");
		String reqs = (objektet.get("value").toString());
	    if(reqs.equals("true")) {
	    		return true;
	    }
		
		return false;
	}
	
	// Check if the Athleteusername exists
	public boolean athleteUsernameExists(String username) throws Exception {
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", username);
	    JSONObject objektet = BackendConnector.makeRequest(myMap, "athleteUsernameExists");
		String reqs = (objektet.get("value").toString());
	    if(reqs.equals("true")) {
	    		return true;
	    }
		
		return false;
	}
	
	// Check if the user is an Athlete
	public boolean isAthlete(String username) throws Exception {
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", username);
	    JSONObject objektet = BackendConnector.makeRequest(myMap, "isAthlete");
		String reqs = (objektet.get("value").toString());
	    if(reqs.equals("true")) {
	    		return true;
	    }
		
		return false;
	}
	
	public void setWorkoutVisibility(boolean bool, Workout workout, Athlete athlete) throws Exception {
		//String str = coach.getUsername();
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", String.valueOf(bool));
		myMap.put("date", workout.getDateString());
		myMap.put("username", athlete.getUsername());
		
	    BackendConnector.makeRequest(myMap, "setVisibility");
		
	}
	
	// **** ACTIVITIES TAB ****
	public ArrayList<String> getAllActivities() throws Exception{

		//String str = coach.getUsername();
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", "hei");
	    JSONObject objektet = BackendConnector.makeRequest(myMap, "getAllActivities");
	    ArrayList<String> acts = new ArrayList<String>();
	    acts.addAll(Arrays.asList(objektet.get("Activities").toString().split("<")));
		return acts;
	}
	
	public int getNrOfWorkoutsForAthlete(String athlete, String activity) throws Exception {
		
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", athlete);
		myMap.put("activity", activity);

	    JSONObject objektet = BackendConnector.makeRequest(myMap, "getNrWorkoutsForAthlete");
		return Integer.parseInt(objektet.get("Nr").toString()) ;
	}
	
	public List<Athlete> getAthletesForActivity(String activity) throws Exception{
		
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", activity);
		JSONObject obj = BackendConnector.makeRequest(myMap, "getAthletesForActivity");
		List<Athlete> athletes = new ArrayList<Athlete>();
		JSONArray arr = obj.getJSONArray("arr");
		
		for(int i = 0; i < arr.length(); i++) {
			JSONObject objektet = arr.getJSONObject(i);
			String c = objektet.get("Coaches").toString();
		    String[] str = c.split("_");
		    String r = objektet.get("Requests").toString();
		    String[] str2 = r.split("_");
		    List<String> coachList = Arrays.asList(str);   //new ArrayList<String>();
		    List<String> requests = Arrays.asList(str2);
		    
		    Athlete athl = new Athlete(objektet.get("Username").toString(), objektet.get("Passord").toString(), objektet.get("Name").toString(), coachList, requests);
		    athl.setMaxHR(Integer.parseInt("190"));
		    athletes.add(athl);
		}
		
		return athletes;
	}
	
	public List<Workout> getWorkoutsForActivity(String activity) throws Exception{
		
		HashMap<String, String> myMap = new HashMap<String, String>(); 
	 	myMap.put("name", activity);
	 	//TestBackendConnector co = new TestBackendConnector();
	 	
	    JSONObject obj = BackendConnector.makeRequest(myMap, "getWorkoutsForActivity");
	    JSONArray jArray = obj.getJSONArray("arr");
	    List<Workout> woList = new ArrayList<Workout>();
	    for(int i = 0; i < jArray.length(); i++) {
	    	JSONObject objektet = jArray.getJSONObject(i);
	    	String pulseString = objektet.get("Pulse").toString();
	        String[] pulseStr = pulseString.split("_");
	        List<String> pulse = Arrays.asList(pulseStr);
	        boolean vis = true;
	        if(objektet.get("Visibility").toString().equals("false")) {
	        	vis = false;
	        }
	        
	        
	        
	        String gpxString = objektet.get("GpxData").toString();
	        if(gpxString.equals("null")) {
	        		InputStream gpxFilepath = null;
	        		String str = objektet.get("Kilometres").toString();
	        		System.out.println(str);
	        		Double sinkovic = Double.parseDouble(str);
	        		System.out.println(sinkovic);
	        		
	        		Workout wo = new Workout(this.getAthlete(objektet.get("Athlete").toString()), objektet.getString("Date"), objektet.getString("Type"),Integer.parseInt(objektet.get("Duration").toString()), Double.parseDouble(objektet.get("Kilometres").toString()), pulse, vis, gpxFilepath );
	        		woList.add(wo);
	        }
	        else {
	        List<String> gpxArray = Arrays.asList(gpxString.split("X"));
	        List<List<Double>> gpxData = new ArrayList<List<Double>>();

	        for(String s : gpxArray) {
	        	List<Double> gpxPair = new ArrayList<Double>();

	        	gpxPair.add(Double.parseDouble(s.split("_")[0] ));
	        	gpxPair.add(Double.parseDouble(s.split("_")[1]   ));
	        	gpxData.add(gpxPair);
	        }
	        Workout wo = new Workout(this.getAthlete(objektet.get("Athlete").toString()), objektet.getString("Date"), objektet.getString("Type"),Integer.parseInt(objektet.get("Duration").toString()), Double.parseDouble(objektet.get("Kilometres").toString()), pulse, vis, gpxData );
	        woList.add(wo);
	        }
	    }
	    
	    return woList;
	}
	
	public List<Integer> getAthleteActivityTypes(String username) throws Exception{
		List<Integer> list = new ArrayList<>();
		
		List<String> allAct = getAllActivities();
		for (String act : allAct) {
			int n = getNrOfWorkoutsForAthlete(username, act);
			list.add(n);
		}
		return list;
	}
	
	//**************HOME-TAB*******************
	public List<String> getCoachNotes(String coachUsername) throws Exception {

		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", coachUsername);
		JSONObject obj = BackendConnector.makeRequest(myMap, "getCoachNotes");
		JSONArray arr =  obj.getJSONArray("arr");
		List<String> cNotes = new ArrayList<String>();
		for(int i = 0; i < arr.length(); i++) {
			cNotes.add(  arr.getJSONObject(i).get("note").toString()    );
		}
	    return cNotes;
		
	}
	
	public void addCoachNotes(String username, String note) throws Exception {
		
		HashMap<String, String> myMap = new HashMap<String, String>();
		String str = username + "_" + note;
		myMap.put("name", str);
		BackendConnector.makeRequest(myMap, "addCoachNotes");

	}
	
	public void updateCoachNotes(String username, String note) throws Exception {
		
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", username);
		myMap.put("note", note);
		BackendConnector.makeRequest(myMap, "updateCoachNotes");
		
	}
	
	public static void main(String[] args) throws Exception {
//		Database db = new Database();
//		System.out.println(db.getAthletesForActivity("RUNNING"));
//		System.out.println(db.getCoachNotes("petter22"));
//		System.out.println(db.getAthleteActivityTypes("TeddyWestside"));

	}
	
	// QUOTES
	
	public List<String> getQuotes() throws Exception{
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("name", "getQuotes");
		
		JSONObject obj = BackendConnector.makeRequest(myMap, "getQuotes");
		JSONArray arr = obj.getJSONArray("arr");
		List<String> quotes = new ArrayList<String>();
		for(int i = 0; i < arr.length(); i++) {
			quotes.add(arr.getJSONObject(i).get("quote").toString());
		}
		
		return quotes; 
	}
	
	public void addSleepData (Athlete athlete) {
		//forutsetter at alle athletes har sleepdata, men denne kan være empty. har en try-block for det uansett
		
		
		Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
	
		if (found == null) {
			System.out.println("no athlete with this username");
		} else {
			List<List<String>> sleepdata = new ArrayList <List<String>> () ;
			try {
				Bson updatedvalue = new Document("Sleepdata", athlete.getSleepData());
				Bson updateoperation = new Document("$set", updatedvalue);
				athleteCollection.updateOne(found, updateoperation);
				
				} catch (Exception e) {
					System.out.println("inside try/catch block addSleepData");
				} 			
		
		}	
		
	}
	
	
	public List<List<String>> getSleepData (Athlete athlete) {
		List<List<String>> sleepdata = new ArrayList <List<String>> () ;

		
		Document found = (Document) athleteCollection.find(new Document("Username", athlete.getUsername())).first();
	
		if (found == null) {
			System.out.println(athlete.getUsername());
			System.out.println("inside getSleepData, no athlete with this username");
			return null;
		} else {
			try {
			sleepdata = (List<List<String>>) found.get("Sleepdata");
			return sleepdata;
			} catch (Exception e) {
				System.out.println("inside try/catch block getSleepData");
				return null;
			}
					
		}
		
	}
	

}
