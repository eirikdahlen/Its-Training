package tdt4140.gr1802.app.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;

public class TestBackendConnector {
    public static void main(String[] args) throws Exception {
        // Prints "Hello, World" to the terminal window.
    		TestBackendConnector conn = new TestBackendConnector();
    		
    		System.out.println("__SERVERTEST___");
    		String password = conn.getPassword("TeddyWestside");
    		System.out.println("theodor = " + password);
    		System.out.println("___SERVERTEST FERDIG__");
    		
    		Coach coach1 = conn.getCoach("petter22");
    		System.out.println("__**PRINTER FRA GET COACH**__");
    		System.out.println(coach1.getName());
    		System.out.println("__**print get coach slutt**__");
        /*
         * JSONObject obj = new JSONObject();
		//obj.put("status", str);
		obj.put("Username", name);
		obj.put("Passord", athl.getPassword());
		obj.put("Name", athl.getName());
		obj.put("Coaches", athl.getCoaches()  );
		obj.put("Requests", athl.getQueuedCoaches() );
		obj.put("MAXHR", athl.getMaxHR());
		//obj.put("", arg1);
		return obj.toString() ;
         */
        
        //System.out.println(objektet.get("Passord"));
        //System.out.println("yolo");
       // System.out.print(objektet.toString());
        
        
    }
    
    public Athlete getAthlete(String username) throws Exception {
    	
    	
    	HashMap<String, String> myMap = new HashMap<String, String>(); 
		
	myMap.put("name", username);
    JSONObject objektet = BackendConnector.makeRequest(myMap, "getAthlete");

    System.out.println("_inni getAthlete__");
    System.out.println(objektet.get("Username").toString());
    System.out.println(objektet.get("Password").toString());
    System.out.println(objektet.get("Name").toString());

    System.out.println("___SLUTT INNI GETATHLETE__");
    
    JSONArray array = (JSONArray) objektet.get("Coaches");
    JSONArray array2 = (JSONArray) objektet.get("Requests");
    List<String> coachList = new ArrayList<String>();
    List<String> requestList = new ArrayList<String>();
    
    for (int i=0; i<array.length(); i++) {
        coachList.add( array.getString(i) );
    }
     
    for (int i=0; i<array2.length(); i++) {
        requestList.add( array2.getString(i) );
    }
    
    System.out.println("test");
    System.out.println(coachList);
    System.out.println(requestList);

    Athlete athl = new Athlete(objektet.get("Username").toString(), objektet.get("Password").toString(), objektet.get("Name").toString(), coachList, requestList);
  
    return athl;
    	
    }

public Coach getCoach(String username) throws Exception {
    	

	HashMap<String, String> myMap = new HashMap<String, String>(); 
		
	myMap.put("name", username);
	JSONObject objektet = BackendConnector.makeRequest(myMap, "getCoach");
	
	System.out.println("_inni getCoach__");
	System.out.println(objektet.get("Username").toString());
	System.out.println(objektet.get("Password").toString());
	System.out.println(objektet.get("Name").toString());
	
	System.out.println("___SLUTT INNI GETCoach__");
	
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
	
	System.out.println("test");
	System.out.println(athleteList);
	System.out.println(requestList);
	
	Coach coach = new Coach(objektet.get("Username").toString(), objektet.get("Password").toString() , objektet.get("Name").toString() , athleteList, requestList);

	return coach;
    	
    }

public String getPassword(String username) throws Exception {
	
 	HashMap<String, String> myMap = new HashMap<String, String>(); 
 	myMap.put("name", username);
 	String pass;
    JSONObject objektet = BackendConnector.makeRequest(myMap, "getPass");
    pass = objektet.get("Password").toString();
    return pass;
}

public List<Workout> getAllWorkouts(Athlete athl) throws Exception {
	//System.out.println("inni get all workouts");
 	HashMap<String, String> myMap = new HashMap<String, String>(); 
 	myMap.put("name", athl.getUsername());
 	//TestBackendConnector co = new TestBackendConnector();
 	
    JSONObject obj = BackendConnector.makeRequest(myMap, "getAllWorkouts");
    JSONArray jArray = obj.getJSONArray("arr");
    List<Workout> woList = new ArrayList<Workout>();
    for(int i = 0; i < jArray.length(); i++) {
    System.out.println(Double.parseDouble((jArray.getJSONObject(i).get("Kilometres").toString())));
    		
    }
    
    return woList;
}

public void addRequestCoachToAthlete(Athlete athlete, String coach) throws Exception {
	
	String str = athlete.getUsername() + "_" + coach;
	HashMap<String, String> myMap = new HashMap<String, String>();
	myMap.put("name", str);
	BackendConnector.makeRequest(myMap, "addRequestCoachToAthlete");
}
public void addRequestAthleteToCoach(Coach coach, String athlete) throws Exception {
	
	
	HashMap<String, String> myMap = new HashMap<String, String>();
	myMap.put("name", coach.getUsername());
	myMap.put("athlete", athlete);
	
	BackendConnector.makeRequest(myMap, "addRequestAthleteToCoach");
}

public void mcTester(String coach, String athlete) throws Exception {
	
	
	HashMap<String, String> myMap = new HashMap<String, String>();
	myMap.put("name", coach);
	myMap.put("name", athlete);
	
	BackendConnector.makeRequest(myMap, "addRequestAthleteToCoach");
}

public List<String> getRequestsForCoach(Coach coach) throws Exception {
	
	//String str = coach.getUsername();
	HashMap<String, String> myMap = new HashMap<String, String>();
	myMap.put("name", coach.getUsername());
    JSONObject objektet = BackendConnector.makeRequest(myMap, "getRequestsForCoach");
	List<String> reqs = Arrays.asList(objektet.get("Requests").toString().split("_"));
    
	return reqs;
}

public boolean dateTimeExists(Athlete athl, String date) throws Exception {
	
	//String str = coach.getUsername();
	HashMap<String, String> myMap = new HashMap<String, String>();
	myMap.put("name", athl.getUsername());
	myMap.put("date", date);
    JSONObject objektet = BackendConnector.makeRequest(myMap, "getRequestsForCoach");
	String reqs = (objektet.get("value").toString());
    if(reqs.equals("true")) {
    		return true;
    }
	
	return false;
}







public Workout getWorkout(Athlete athl, String date) throws Exception {
	
 	HashMap<String, String> myMap = new HashMap<String, String>();
 	
// 	List<String> argList = new ArrayList<String>();
// 	argList.add(athl.getUsername());
// 	argList.add(date);
// 	String str = String.join("_", argList );
 	
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



    
}