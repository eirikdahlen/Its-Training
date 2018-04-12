package tdt4140.gr1802.app.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;

public class TestBackendConnector {
    public static void main(String[] args) throws Exception {
        // Prints "Hello, World" to the terminal window.
    		HashMap<String, String> myMap = new HashMap<String, String>(); 
    		Athlete athl = new Athlete("TeddyWestside", null, null);
    		
    		
    		TestBackendConnector co = new TestBackendConnector();
    		//co.getWorkout(athl, date)
    		System.out.println(co.getAllWorkouts(athl).get(0).getDateString());
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
    String c = objektet.get("Coaches").toString();
    String[] str = c.split("_");
    String r = objektet.get("Requests").toString();
    String[] str2 = r.split("_");
    List<String> coachList = Arrays.asList(str);   //new ArrayList<String>();
    List<String> requests = Arrays.asList(str2);
    System.out.println(objektet.get("Username").toString());
    System.out.println(objektet.get("Passord").toString());
    System.out.println(objektet.get("Name").toString());
    System.out.println(coachList);
    System.out.println(requests);
           
    Athlete athl = new Athlete(objektet.get("Username").toString(), objektet.get("Passord").toString(), objektet.get("Name").toString(), coachList, requests);
  
    return athl;
    	
    }

public Coach getCoach(String username) throws Exception {
    	
    	
    	HashMap<String, String> myMap = new HashMap<String, String>(); 
		
	myMap.put("name", username);
    JSONObject objektet = BackendConnector.makeRequest(myMap, "getCoach");
    String c = objektet.get("Athletes").toString();
    String[] str = c.split("_");
    String r = objektet.get("Requests").toString();
    String[] str2 = c.split("_");
    List<String> athleteList = new ArrayList<String>();
    List<String> requests = new ArrayList<String>();
    
    for(String s : str) {
    	athleteList.add((String) s);
    }
    for(String s : str2) {
    	requests.add((String ) s);
    }
           
    Coach coach = new Coach(objektet.get("Username").toString(), objektet.get("Passord").toString(), objektet.get("Name").toString(), athleteList, requests);
  
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
 	TestBackendConnector co = new TestBackendConnector();
 	
    JSONObject objektet = BackendConnector.makeRequest(myMap, "getAllWorkouts");
    System.out.println("inni get all workouts");
    //System.out.println(objektet.get("liste").toString());
    List<String> dates = Arrays.asList(objektet.get("liste").toString().split("_"));
    List<Workout> workouts = new ArrayList<Workout>();
    int i = 0;
    for(String dat: dates) {
    	System.out.println(i);
    	i++;
    	System.out.println(dat);
    		workouts.add(co.getWorkout(athl, dat) );
    }
    
    return workouts;
}



public Workout getWorkout(Athlete athl, String date) throws Exception {
	
 	HashMap<String, String> myMap = new HashMap<String, String>(); 
 	List<String> argList = new ArrayList<String>();
 	argList.add(athl.getUsername());
 	argList.add(date);
 	String str = String.join("_", argList );
 	myMap.put("name", str);
 	
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