package org.web.server;

import org.bson.BSONObject;
import org.bson.Document;

import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Database;
import tdt4140.gr1802.app.core.Workout;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

@RestController

public class ServerController {
	
	private DatabaseS db = new DatabaseS();
	
	// Maps to signup-endpoint
	@RequestMapping("/getAthlete") //Checkolini not working properly
    public String getAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

		//DatabaseS db = new DatabaseS();
		String obj = db.getAthleteS(name).toJson();

		//String obj = athl.toString();   //new JSONObject();
		//obj.put("status", str);
//		obj.put("Username", name);
//		obj.put("Passord", athl.getPassword());
//		obj.put("Name", athl.getName());
//		if(athl.getCoaches() == null) {
//			List<String> lis = new ArrayList<String>();
//			lis.add("null");
//			obj.put("Coaches", lis);
//		}else {
//			obj.put("Coaches", String.join("_", athl.getCoaches()) );
//		}
//		if(athl.getQueuedCoaches() == null) {
//			List<String> lis = new ArrayList<String>();
//			lis.add("null");
//			obj.put("Requests", lis);
//		}else {
//			obj.put("Requests", String.join("_", athl.getCoaches()) );
//		}
//		obj.put("MAXHR", athl.getMaxHR());
		
		return obj;
		
    }
	

	// Maps to GetCoach EndPoint
	@RequestMapping("/getCoach")  //checkolini  noe rart her tror jeg
    public String getCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{
		System.out.println("getCoach pa server");
		//DatabaseS db = new DatabaseS();
		String obj = db.getCoacheS(name).toJson();
		
		return obj.toString() ;
    }
	
	// Maps to GetPass-Endpoint
		@RequestMapping("/getPass")  //checkolini  works
	    public String getPass(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			String pass = db.getPassword(name);
			
			JSONObject obj = new JSONObject();
			obj.put("Password", pass);
			return obj.toString();
	    }
		
		// Maps to GetAllAthletes-Endpoint
		@RequestMapping("/getAllAthletes")     			//Checkolini   works
	    public String getAllAthletes(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			List<Athlete> athletes = db.getAllAthletes();
			List<JSONObject> obList = new ArrayList<JSONObject>();
			JSONArray arr = new JSONArray();
			
			for( Athlete athl : athletes) {
				JSONObject obj = new JSONObject();
				obj.put("Username", athl.getUsername());
				obj.put("Passord", athl.getPassword());
				obj.put("Name", athl.getName());
				obj.put("Coaches", String.join("<", athl.getCoaches() ) );
				obj.put("Requests", String.join("<", athl.getQueuedCoaches() ));
				obj.put("MAXHR", athl.getMaxHR());
				arr.put(obj);
				
			}
			JSONObject feedback = new JSONObject();
			feedback.put("arr", arr);
			return feedback.toString() ;
	    }
		
		@RequestMapping("/getWorkout") //checkolini   works
	    public String getWorkout(@RequestParam(name="name", required=false, defaultValue="Stranger") String name,
	    							@RequestParam("date") String date) throws Exception{

			//DatabaseS db = new DatabaseS();
			
			
			Athlete athl = db.getAthlete(name);
			Workout wo = db.getWorkout(athl, date);
			//Workout wo = db.getWorkout(athl, "12-03-2018 14:24:57" );
//			String str = String.join(",", wo.getPulsList());
			List<String> gpxStringList = new ArrayList<String>();
			if(wo.getGpxData() == null) {
				gpxStringList.add("null");
			}
			try {
				List<List<Double>> lis = wo.getGpxData();
				for(int i = 0; i < lis.size(); i++) {
					
					gpxStringList.add( Double.toString(lis.get(i).get(0)) + "_"   + Double.toString(lis.get(i).get(1) ) );	
				}
			}
			catch(Exception e){
				System.out.println("heisann på degsann");
				//gpxStringList.add("null");
			}
			
			

			JSONObject obj = new JSONObject();
			//obj.put("status", str);
			obj.put("Athlete", name);
			obj.put("Date", wo.getDateString());
			obj.put("Type", wo.getType());
			obj.put("Duration", wo.getDuration() );
			obj.put("Kilometres", wo.getKilometres());
			obj.put("Pulse",  String.join("_", wo.getPulsList() ));
			obj.put("Visibility", Boolean.toString(wo.getVisibility()) );
			
			obj.put("GpxData", String.join("X", gpxStringList ));
			return obj.toString() ;
			

	    }
		
		

		@RequestMapping("/getAllWorkouts")  //checkolini     works
	    public String getAllWorkout(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			
			List<Workout> workouts = db.getAllWorkouts(db.getAthlete(name));
			
			JSONArray jArray = new JSONArray();
			
			for(Workout wo : workouts) {
				JSONObject obj = new JSONObject();
				obj.put("Athlete", name);
				obj.put("Date", wo.getDateString());
				obj.put("Type", wo.getType());
				obj.put("Duration", wo.getDuration() );
				obj.put("Kilometres", wo.getKilometres());
				obj.put("Pulse",  String.join("_", wo.getPulsList() ));
				obj.put("Visibility", Boolean.toString(wo.getVisibility()) );
				List<String> gpxStringList = new ArrayList<String>();
				if(wo.getGpxData() == null) {
					gpxStringList.add("null");
				}
				try {
					List<List<Double>> lis = wo.getGpxData();
					for(int i = 0; i < lis.size(); i++) {
						
						gpxStringList.add( Double.toString(lis.get(i).get(0)) + "_"   + Double.toString(lis.get(i).get(1) ) );	
					}
				}
				catch(Exception e){
					System.out.println("heisann på degsann");
					//gpxStringList.add("null");
				}
				obj.put("GpxData", String.join("X", gpxStringList ));
				jArray.put(obj);
				
				
			}
			
			
			
			JSONObject feedback = new JSONObject();
			feedback.put("arr", jArray);
			return feedback.toString() ;
			
	    }

		@RequestMapping("/addCoachToAthlete") // chekcolini   fails
	    public void addCoachToAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String coach = name.split("_")[1];
			
			db.addCoachToAthlete(username, coach);
			
	    }
		
		@RequestMapping("/addAthleteToCoach")  //checkolini  fails
	    public void addAthleteToCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String athlete = name.split("_")[1];
			
			db.addAthleteToCoach(username, athlete);
			
	    }
		
		@RequestMapping("/addRequestAthleteToCoach") // checkolini   fails
	    public void addRequestAthleteToCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name
	    										) throws Exception{

			//DatabaseS db = new DatabaseS();
			
			
			db.addRequestAthleteToCoach(name, "petter22");
			
	    }

		@RequestMapping("/addRequestCoachToAthlete") // checkolini   fails
		public void addRequestCoachToAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name,
				@RequestParam("coach") String coach) throws Exception{

			//DatabaseS db = new DatabaseS();


			db.addRequestCoachToAthlete(name, coach);

		}

		@RequestMapping("/deleteCoachForAthlete")
	    public void deleteCoachforAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String coach = name.split("_")[1];
			
			db.deleteCoachForAthlete(db.getAthlete(username), coach);
			
	    }
		
		@RequestMapping("/deleteAthleteForCoach")
	    public void deleteAthleteforCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String athlete = name.split("_")[1];
			
			db.deleteAthleteForCoach(db.getCoach(username), athlete);
			
	    }
		
		@RequestMapping("/deleteCoachRequestForAthlete")
	    public void deleteCoachRequestforAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String coach = name.split("_")[1];
			
			db.deleteCoachRequestForAthlete(db.getAthlete(username), coach);
			
	    }
		
		@RequestMapping("/deleteAthleteRequestForCoach")
	    public void deleteAthleteRequestforCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String athlete = name.split("_")[1];
			
			db.deleteAthleteRequestForCoach(db.getCoach(username), athlete);
			
	    }
		
		@RequestMapping("/getRequestsForCoach") // checkolini ?
	    public String getRequestsforCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			JSONObject obj = new JSONObject();
			obj.put("Requests", String.join("_", db.getRequestsForCoach(name)));
			return obj.toString();
	    }
		
		@RequestMapping("/getRequestsForAthlete") //checkolini ?
	    public String getRequestsforAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			JSONObject obj = new JSONObject();
			obj.put("Requests", db.getRequestsForAthlete(db.getAthlete(name)));
			return obj.toString();
	    }
		
		
		
		
		
		@RequestMapping("/dateTimeExists") // checkolini ?
	    public String dateTimeExists(@RequestParam(name="name", required=false, defaultValue="Stranger") String name,
	    								@RequestParam("date") String date) throws Exception{

			//DatabaseS db = new DatabaseS();
			
			JSONObject obj = new JSONObject();
			obj.put("value", db.datetimeExists(name, date));
			
			return obj.toString();
	    }
		

		@RequestMapping("/usernameExists") // checkolini works
	    public String usernameExists(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			
			JSONObject obj = new JSONObject();
			obj.put("value", db.usernameExists(name));
			
			return obj.toString();
	    }
		

		@RequestMapping("/athleteUsernameExists") // checkolini works
	    public String AthleteUsernameExists(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			
			JSONObject obj = new JSONObject();
			obj.put("value", db.athleteUsernameExists(name));
			
			return obj.toString();
	    }
		
		@RequestMapping("/coachUsernameExists") //checkolini works
	    public String coachUsernameExists(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			
			JSONObject obj = new JSONObject();
			obj.put("value", db.coachUsernameExists(name));
			
			return obj.toString();
	    }
		
		@RequestMapping("/isAthlete") // checkolini works
	    public String isAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			
			JSONObject obj = new JSONObject();
			obj.put("value", db.isAthlete(name));
			
			return obj.toString();
	    }
		
		
		
		@RequestMapping("/setVisibility") // checkolini ? 
	    public void setVisibility(@RequestParam(name="name", required=false, defaultValue="Stranger") String name,
	    							@RequestParam("date") String workou,
	    							@RequestParam("username") String athlet) throws Exception{

			//DatabaseS db = new DatabaseS();
			String bool = name;

			Athlete athlete = db.getAthlete(athlet);
			Workout work = db.getWorkout(athlete, workou);
			
			if(bool.equals("true")) {
				db.setWorkoutVisibility(true, work, athlete);
			}
			else {
				db.setWorkoutVisibility(false, work, athlete);
			}
			
	    }
		
		
		@RequestMapping("/getAllActivities") // checkolini works
	    public String getAllActivities(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			JSONObject obj = new JSONObject();
			obj.put("Activities", String.join("<", db.getAllActivities()));
			return obj.toString();
	    }
		
		
		@RequestMapping("/getNrWorkoutsForAthlete") //checkolini works
	    public String getNrWorkoutsForAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name,
	    										@RequestParam("activity") String activity) throws Exception{

			
			JSONObject obj = new JSONObject();
			obj.put("Nr", db.getNrOfWorkoutsForAthlete(name, activity));
			return obj.toString();
	    }
		

		@RequestMapping("/getAthletesForActivity") //checkolini works
	    public String getAthletesForActivity(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			List<Athlete> liste = db.getAthletesForActivity(name);
			JSONArray arr = new JSONArray();
			
			for(Athlete a : liste) {
				JSONObject obj = new JSONObject();
				obj.put("Username", a.getUsername());
				obj.put("Passord", a.getPassword());
				obj.put("Name", a.getName());
				obj.put("Coaches", String.join("_", a.getCoaches()) );
				obj.put("Requests", String.join("_", a.getQueuedCoaches()) );
				obj.put("MAXHR", a.getMaxHR());
				arr.put(obj);
			}
			JSONObject feedback = new JSONObject();
			feedback.put("arr", arr);
			
			return feedback.toString();
	    }
		

		@RequestMapping("/getWorkoutsForActivity") //checkolini works
	    public String getWorkoutsForActivity(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			List<Workout> workouts = db.getWorkoutsForActivity(name);
			JSONArray jArray = new JSONArray();
			
			for(Workout wo : workouts) {
				JSONObject obj = new JSONObject();
				obj.put("Athlete", wo.getAthlete().getUsername());
				obj.put("Date", wo.getDateString());
				obj.put("Type", wo.getType());
				obj.put("Duration", wo.getDuration() );
				obj.put("Kilometres", wo.getKilometres());
				obj.put("Pulse",  String.join("_", wo.getPulsList() ));
				obj.put("Visibility", Boolean.toString(wo.getVisibility()) );
				List<String> gpxStringList = new ArrayList<String>();
				if(wo.getGpxData() == null) {
					gpxStringList.add("null");
				}
				try {
					List<List<Double>> lis = wo.getGpxData();
					for(int i = 0; i < lis.size(); i++) {
						
						gpxStringList.add( Double.toString(lis.get(i).get(0)) + "_"   + Double.toString(lis.get(i).get(1) ) );	
					}
				}
				catch(Exception e){
					System.out.println("heisann på degsann");
					//gpxStringList.add("null");
				}
				obj.put("GpxData", String.join("X", gpxStringList ));
				jArray.put(obj);
				
				
			}
			
			JSONObject feedback = new JSONObject();
			feedback.put("arr", jArray);
			return feedback.toString() ;
			
	    }
		

		@RequestMapping("/getCoachNotes") //checkolini works
	    public String getCoachNotes(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			List<String> liste = db.getCoachNotes(name);
			JSONArray arr = new JSONArray();
			for(String s : liste) {
				JSONObject obj = new JSONObject();
				obj.put("note", s);
				arr.put(obj);
			}
			JSONObject feedback = new JSONObject();
			feedback.put("arr", arr);
			return feedback.toString();
	    }
		
		@RequestMapping("/addCoachNotes") //checkolini  fails
	    public void addCoachNotes(@RequestParam(name="name", required=false, defaultValue="Stranger") String name,
	    		@RequestParam(name="note") String note) throws Exception{

			//DatabaseS db = new DatabaseS();
			db.addCoachNotes(note, note);
	    }
		
		@RequestMapping("/updateCoachNotes")  //checkolini   ?
	    public void updateCoachNotes(@RequestParam(name="name", required=false, defaultValue="Stranger") String name,
	    								@RequestParam("note") String note) throws Exception{
			db.updateCoachNotes(name, note);
	    }
		

		@RequestMapping("/getQuotes") //checkolini  works
	    public String getQuotes(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{
			List<String> quotes = db.getQuotes();
			JSONArray arr = new JSONArray();
			for(String s : quotes) {
				JSONObject obj = new JSONObject();
				obj.put("quote", s);
				arr.put(obj);
			}
			JSONObject feedback = new JSONObject();
			feedback.put("arr", arr);
			return feedback.toString();
	    }
		

		@RequestMapping("/createCoach") // feil?
	    public void createCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			// name on following form 0username_1password_2name_3Athletes(a,b,c,d,)_4Req(a,b,c,d)
			//DatabaseS db = new DatabaseS();
			List<String> athlList = Arrays.asList(name.split("<"));
			List<String> athls = Arrays.asList(athlList.get(3).split("_"));
			List<String> rAthls = Arrays.asList(athlList.get(4).split("_"));
			
			Coach coach = new Coach(athlList.get(0),athlList.get(1) , athlList.get(2), athls, rAthls);
			db.createCoach(coach);
	    }
		
		@RequestMapping("/createAthlete") // noe feil?
	    public void createAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name,
	    		@RequestParam(value="password") String pass,
	    		@RequestParam(value="fName") String fName,
	    		@RequestParam(value="coaches") String coaches,
	    		@RequestParam(value="requestCoaches") String requestCoaches) throws Exception{
			System.out.println(name);
			System.out.println(pass);
			System.out.println(fName);
			System.out.println(coaches);
			
			List<String> cList = Arrays.asList(coaches.split("<"));
			List<String> requestCList = Arrays.asList(requestCoaches.split("<"));
			
			Athlete athl = new Athlete(name, pass, fName, cList, requestCList);
			//athl.setMaxHR(maxhr);
			db.createAthlete(athl);
	    }
		
		@RequestMapping("/createWorkout")
	    public void createWorkout(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			// name on following form 0username_1date_2duration_3kilometres_4pulse(a,b,c,d,)_5gpx(a,b,c,d)_Visibility
			//DatabaseS db = new DatabaseS();
			String[] workoutValues = name.split("_");
			String username = workoutValues[0];
			String date = workoutValues[1];
			String type = workoutValues[2];
			int duration = Integer.parseInt(workoutValues[3]);
			double kilometres = Double.parseDouble(workoutValues[4]);
			List<String> pulseList = new ArrayList<String>();
			List<List<Double>> gpxList = new ArrayList<List<Double>>();
			String visibility = workoutValues[7];
			boolean bool;
			
			for (String str : workoutValues[5].split(",")) {
				pulseList.add(str);
			}
			
			for (String str : workoutValues[6].split("*")) {
				List<Double> coList = new ArrayList<Double>();
				coList.add( Double.parseDouble(str.split(",")[0] ));
				coList.add( Double.parseDouble(str.split(",")[1] ));
				gpxList.add(coList);
			}
			if(visibility.equals("true")) {
				bool = true;
			}
			else {
				bool = false;
			}
			
			Workout wo = new Workout(db.getAthlete(username), date, type, duration,kilometres,pulseList, bool, gpxList);
			
			db.createWorkout(wo);
	    }
		
		
		
		
		@RequestMapping("/addMaxHR")
	    public void addMaxHR(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			int hr  = Integer.parseInt(name.split("_")[1]);
		
			db.addMaxHR(db.getAthlete(username), hr);
	    }
		
		
		
		/*
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
		 */

}