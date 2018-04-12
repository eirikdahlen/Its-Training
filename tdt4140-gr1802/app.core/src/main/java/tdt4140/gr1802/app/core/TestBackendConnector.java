package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;

public class TestBackendConnector {
    public static void main(String[] args) throws Exception {
        // Prints "Hello, World" to the terminal window.
    		HashMap<String, String> myMap = new HashMap<String, String>(); 
    		
    		myMap.put("name", "TeddyWestside");
        JSONObject objektet = BackendConnector.makeRequest(myMap, "getAthlete");
        
        
        
        String c = objektet.get("Coaches").toString();
        String[] str = c.split("_");
        String r = objektet.get("Requests").toString();
        String[] str2 = c.split("_");
        
        
        List<String> coachList = new ArrayList<String>();
        List<String> requests = new ArrayList<String>();
        
        for(String s : str) {
        	coachList.add((String) s);
        }
        for(String s : str2) {
        	requests.add((String ) s);
        }
        System.out.println("ostostostosotostosototosts");
        //System.out.println(coachList);
        System.out.println(coachList.get(0));
        System.out.println(coachList.get(1));
        System.out.println(objektet.get("Username").toString());
        System.out.println("ostostostosotostosototosts");
       
        
               
        Athlete athl = new Athlete(objektet.get("Username").toString(), objektet.get("Passord").toString(), objektet.get("Name").toString(), coachList, requests);
        System.out.println("xxxxxxxxxxx");
        System.out.println(objektet.get("Coaches"));
        System.out.println((objektet.get("Coaches")).getClass());
        System.out.println("xxxxxxxxxxx");
        
        
        System.out.println(athl.getPassword());
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
        System.out.println("yolo");
        System.out.print(objektet.toString());
        
        
    }
    
    public Athlete getAthlete(String username) throws Exception {
    	
    	
    	HashMap<String, String> myMap = new HashMap<String, String>(); 
		
	myMap.put("name", "TeddyWestside");
    JSONObject objektet = BackendConnector.makeRequest(myMap, "getAthlete");
    String c = objektet.get("Coaches").toString();
    String[] str = c.split("_");
    String r = objektet.get("Requests").toString();
    String[] str2 = c.split("_");
    List<String> coachList = new ArrayList<String>();
    List<String> requests = new ArrayList<String>();
    
    for(String s : str) {
    	coachList.add((String) s);
    }
    for(String s : str2) {
    	requests.add((String ) s);
    }
           
    Athlete athl = new Athlete(objektet.get("Username").toString(), objektet.get("Passord").toString(), objektet.get("Name").toString(), coachList, requests);
  
    return athl;
    	
    }

public Coach getCoach(String username) throws Exception {
    	
    	
    	HashMap<String, String> myMap = new HashMap<String, String>(); 
		
	myMap.put("name", "TeddyWestside");
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

    
}