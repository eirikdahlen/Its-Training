package org.web.server;

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
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

@RestController

public class ServerController {
	
	private DatabaseS db = new DatabaseS();
	
	// Maps to signup-endpoint
	@RequestMapping("/getAthlete")
    public String getAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

		//DatabaseS db = new DatabaseS();
		Athlete athl = db.getAthlete(name);
		

		JSONObject obj = new JSONObject();
		//obj.put("status", str);
		obj.put("Username", name);
		obj.put("Passord", athl.getPassword());
		obj.put("Name", athl.getName());
		obj.put("Coaches", String.join("_", athl.getCoaches()) );
		obj.put("Requests", String.join("_", athl.getQueuedCoaches()) );
		obj.put("MAXHR", athl.getMaxHR());
		
		return obj.toString();
		
    }
	

	// Maps to GetCoach EndPoint
	@RequestMapping("/getCoach")
    public String getCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

		//DatabaseS db = new DatabaseS();
		Coach co = db.getCoach(name);
		
		//Workout wo = db.getWorkout(athl, "12-03-2018 14:24:57" );
		//String str = String.join(",", wo.getPulsList());
		
		JSONObject obj = new JSONObject();
		//obj.put("status", str);
		obj.put("Username", name);
		obj.put("Passord", co.getPassword());
		obj.put("Name", co.getName());
		obj.put("Athletes", String.join("_", co.getAthletes()) );
		obj.put("Requests", String.join("_", co.getQueuedAthletes()) );
		//obj.put("", arg1);
		return obj.toString() ;
		

    }
	
	// Maps to GetPass-Endpoint
		@RequestMapping("/getPass")
	    public String getPass(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			String pass = db.getPassword(name);
			
			JSONObject obj = new JSONObject();
			obj.put("Password", pass);
			return obj.toString() ;
	    }
		
		// Maps to GetAllAthletes-Endpoint
		@RequestMapping("/getAllAthletes")
	    public String getAllAthletes(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			List<Athlete> athletes = db.getAllAthletes();
			List<JSONObject> obList = new ArrayList<JSONObject>();
			
			for( Athlete athl : athletes) {
				JSONObject obj = new JSONObject();
				obj.put("Username", athl.getUsername());
				obj.put("Passord", athl.getPassword());
				obj.put("Name", athl.getName());
				obj.put("Coaches",athl.getCoaches()  );
				obj.put("Requests", athl.getQueuedCoaches() );
				obList.add(obj);
				System.out.println(obj.toString());
			}
			
			List<String> stringList = new ArrayList<String>();
			for(JSONObject o : obList) {
				stringList.add( o.toString() );
			}
			
			JSONObject obj = new JSONObject();
			obj.put("Athletes", String.join("-", stringList));
			
			return obj.toString() ;
	    }
		
		@RequestMapping("/getWorkout")
	    public String getWorkout(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String date = name.split("_")[1];
			
			Athlete athl = db.getAthlete(username);
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
			obj.put("Athlete", username);
			obj.put("Date", wo.getDateString());
			obj.put("Type", wo.getType());
			obj.put("Duration", wo.getDuration() );
			obj.put("Kilometres", wo.getKilometres());
			obj.put("Pulse",  String.join("_", wo.getPulsList() ));
			obj.put("Visibility", Boolean.toString(wo.getVisibility()) );
			
			obj.put("GpxData", String.join("X", gpxStringList ));
			return obj.toString() ;
			

	    }
		
		

		@RequestMapping("/getAllWorkouts")
	    public String getAllWorkout(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			
			List<Workout> workouts = db.getAllWorkouts(db.getAthlete(name));
			List<String> datesString = new ArrayList<String>();
			for(Workout wo : workouts) {
				datesString.add(wo.getDateString());
			}
			
			JSONObject obj = new JSONObject();
			obj.put("liste", String.join("_", datesString));
			return obj.toString() ;
			
	    }
		
		@RequestMapping("/addCoachToAthlete")
	    public void addCoachToAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String coach = name.split("_")[1];
			
			db.addCoachToAthlete(db.getAthlete(username), coach);
			
	    }
		
		@RequestMapping("/addAthleteToCoach")
	    public void addAthleteToCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String athlete = name.split("_")[1];
			
			db.addAthleteToCoach(db.getCoach(username), athlete);
			
	    }
		
		@RequestMapping("/addRequestAthleteToCoach")
	    public void addRequestAthleteToCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String athlete = name.split("_")[1];
			
			db.addRequestAthleteToCoach(db.getCoach(username), athlete);
			
	    }

		@RequestMapping("/addRequestCoachToAthlete")
	    public void addRequestCoachToAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String coach = name.split("_")[1];
			
			db.addCoachToAthlete(db.getAthlete(username), coach);
			
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
		
		@RequestMapping("/getRequestForCoach")
	    public String getRequestsforCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			JSONObject obj = new JSONObject();
			obj.put("Requests", db.getRequestsForCoach(db.getCoach(name)));
			return obj.toString();
	    }
		
		@RequestMapping("/getRequestForAthlete")
	    public String getRequestsforAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			JSONObject obj = new JSONObject();
			obj.put("Requests", db.getRequestsForAthlete(db.getAthlete(name)));
			return obj.toString();
	    }
		
		
		
		
		
		@RequestMapping("/dateTimeExists")
	    public String dateTimeExists(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String date = name.split("_")[1];
			
			JSONObject obj = new JSONObject();
			obj.put("value", db.datetimeExists(db.getAthlete(username), date));
			
			return obj.toString();
	    }
		

		@RequestMapping("/usernameExists")
	    public String usernameExists(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			
			JSONObject obj = new JSONObject();
			obj.put("value", db.usernameExists(name));
			
			return obj.toString();
	    }
		

		@RequestMapping("/athleteUsernameExists")
	    public String AthleteUsernameExists(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			
			JSONObject obj = new JSONObject();
			obj.put("value", db.athleteUsernameExists(name));
			
			return obj.toString();
	    }
		
		@RequestMapping("/coachUsernameExists")
	    public String coachUsernameExists(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			
			JSONObject obj = new JSONObject();
			obj.put("value", db.coachUsernameExists(name));
			
			return obj.toString();
	    }
		
		@RequestMapping("/isAthlete")
	    public String isAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			
			JSONObject obj = new JSONObject();
			obj.put("value", db.isAthlete(name));
			
			return obj.toString();
	    }
		
		
		
		@RequestMapping("/setVisibility")
	    public void setVisibility(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String bool = name.split("_")[0];
			String wo = name.split("_")[1];
			String athl = name.split("_")[2];
			
			Athlete athlete = db.getAthlete(athl);
			Workout work = db.getWorkout(athlete, wo);
			
			if(bool.equals("true")) {
				db.setWorkoutVisibility(true, work, athlete);
			}
			else {
				db.setWorkoutVisibility(false, work, athlete);
			}
			
	    }
		
		
		@RequestMapping("/getAllActivities")
	    public String getAllActivities(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			JSONObject obj = new JSONObject();
			obj.put("Activities", db.getAllActivities());
			return obj.toString();
	    }
		
		@RequestMapping("/getNrWorkoutsForAthlete")
	    public String getNrWorkoutsForAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			String athlete = name.split("_")[0];
			String activity = name.split("_")[1];
			JSONObject obj = new JSONObject();
			obj.put("Nr", db.getNrOfWorkoutsForAthlete(db.getAthlete(athlete), activity));
			return obj.toString();
	    }
		

		@RequestMapping("/getAthletesForActivity")
	    public String getAthletesForActivity(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			List<Athlete> liste = db.getAthletesForActivity(name);
			JSONObject obj = new JSONObject();
			obj.put("Athletes", liste);
			return obj.toString();
	    }
		

		@RequestMapping("/getWorkoutsForActivity")
	    public String getWorkoutsForActivity(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			List<Workout> liste = db.getWorkoutsForActivity(name);
			JSONObject obj = new JSONObject();
			obj.put("Workouts", liste);
			return obj.toString();
	    }
		

		@RequestMapping("/getCoachNotes")
	    public String getCoachNotes(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			List<String> liste = db.getCoachNotes(name);
			JSONObject obj = new JSONObject();
			obj.put("Notes", liste);
			return obj.toString();
	    }
		
		@RequestMapping("/addCoachNotes")
	    public void addCoachNotes(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String note = name.split("_")[1];
			
			db.addCoachNotes(username, note);
	    }
		
		@RequestMapping("/updateCoachNotes")
	    public void updateCoachNotes(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			//DatabaseS db = new DatabaseS();
			String username = name.split("_")[0];
			String note = name.split("_")[1];
			
			db.updateCoachNotes(username, note);
	    }
		

		@RequestMapping("/getQuotes")
	    public String getQuotes(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			List<String> liste = db.getQuotes();
			JSONObject obj = new JSONObject();
			obj.put("Notes", liste);
			return obj.toString();
	    }
		

		@RequestMapping("/createCoach")
	    public void createCoach(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			// name on following form 0username_1password_2name_3Athletes(a,b,c,d,)_4Req(a,b,c,d)
			//DatabaseS db = new DatabaseS();
			String[] coachValues = name.split("_");
			String username = coachValues[0];
			String password = coachValues[1];
			String fullName = coachValues[2];
			List<String> athlList = new ArrayList<String>();
			List<String> requestList = new ArrayList<String>();
			for (String str : coachValues[3].split(",")) {
				athlList.add(str);
			}
			for (String str : coachValues[4].split(",")) {
				requestList.add(str);
			}
			
			Coach coach = new Coach(username, password, fullName, athlList, requestList);
			db.createCoach(coach);
	    }
		
		@RequestMapping("/createAthlete")
	    public void createAthlete(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) throws Exception{

			// name on following form 0username_1password_2name_3_MAXHR_4Coaches(a,b,c,d,)_5Req(a,b,c,d)
			//DatabaseS db = new DatabaseS();
			String[] athleteValues = name.split("_");
			String username = athleteValues[0];
			String password = athleteValues[1];
			String fullName = athleteValues[2];
			int maxhr = Integer.parseInt(athleteValues[3]);
			List<String> coachList = new ArrayList<String>();
			List<String> requestList = new ArrayList<String>();
			for (String str : athleteValues[4].split(",")) {
				coachList.add(str);
			}
			for (String str : athleteValues[5].split(",")) {
				requestList.add(str);
			}
			
			Athlete athl = new Athlete(username, password, fullName, coachList, requestList);
			athl.setMaxHR(maxhr);
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