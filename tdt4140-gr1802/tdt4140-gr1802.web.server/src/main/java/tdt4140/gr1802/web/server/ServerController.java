//package tdt4140.gr1802.web.server;
//
//
//import org.json.JSONArray
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//
//@RestController
//
//public class ServerController {
//	// Maps to signup-endpoint
//	@RequestMapping("/signup")
//    //@RequestMapping(method = RequestMethod.POST)
//    public String signup(@RequestParam("username") String username, //gets all parameters from request-body
//                        @RequestParam("password") String password,
//                        @RequestParam("sport") String sport,
//                        @RequestParam("firstname") String firstname,
//                        @RequestParam("surname") String surname,
//                        @RequestParam("maxpulse") String maxpulse,
//                        @RequestParam("weight") String weight,
//                        @RequestParam("gender") String gender
//                        ) {
//     String feedback = ""; //Variable letting user know outcome. Only success/failure implemented.
//   
//		try {
//        ServerLogic.signup(username, password, sport, firstname, surname, maxpulse, weight, gender);
//        feedback = new JSONObject()
//                  .put("status", "success").toString();
//		} catch (Exception e) { // Catches all outcomes that are not successful. Should be specified in more detail in later versions.
//			try {
//				feedback = new JSONObject()
//				          .put("status", "failed").toString();
//			} catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//        return feedback;
//    }
//	
//    @RequestMapping("/login") //mapping to login endpoint
//    public String login(@RequestParam("username") String username,
//    					   @RequestParam("password") String password) {
//    		
//    	String feedback = ""; 
//    		try{
//    			boolean loginResult = ServerLogic.login(username, password);
//    			if (loginResult) {
//    				JSONObject responseObject = new JSONObject().put("status", "success");  
//    				feedback = responseObject.toString(); 
//    			}
//    			else {
//    				feedback = new JSONObject()
//  		                  .put("status", "failed").toString();
//    			}
//    			
//    		}catch (Exception e) {
//    			e.printStackTrace();
//				try {
//					feedback = new JSONObject()
//					          .put("status", "failed").toString();
//				} catch (JSONException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//    		}
//    		
//        return feedback;
//    }
//    @RequestMapping("")
//    public String index() {
//    		return "Welcome. I am your server.";
//    }
//    
//    
//    @RequestMapping("workoutRegistration")
//    public String workoutRegistration(@RequestParam("username") String username, //need this from model
//    									 @RequestParam("duration") String duration,
//    									 @RequestParam("pulses") String pulses,
//    									 @RequestParam("goal") String goal,
//    									 @RequestParam("sport") String sport,
//    									 @RequestParam("privacy") String privacy) {
//    	
//    	String feedback = "";
//    	
//    	try{
//			boolean workoutRegistrationResult = ServerLogic.registerWorkout(username, duration, pulses, goal, sport, privacy);
//			if (workoutRegistrationResult) {
//				JSONObject responseObject = new JSONObject().put("status", "success");  
//				feedback = responseObject.toString(); 
//			}
//			else {
//				feedback = new JSONObject()
//		                  .put("status", "failed").toString();
//			}
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//			try {
//				feedback = new JSONObject()
//				          .put("status", "failed").toString();
//			} catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//    	
//    	
//    	
//    	
//    	return feedback;
//
//    }
//    @RequestMapping("athletesInSport")
//    public String requestAthletesInSport(@RequestParam("sport") String sport) {
//    	
//    	JSONObject feedback = new JSONObject();
//
//		try{
//			ArrayList<Athlete> athletesInSport = ServerLogic.getAthletesInSport(sport);
//
//			if (athletesInSport.size()<1) {
//				feedback.put("status", "failed");
//				feedback.put("message", "No athletes in sport");
//			}else if (athletesInSport.size()>=1) {
//				JSONArray jArray = new JSONArray();
//				for (Athlete athlete: athletesInSport){
//						JSONObject athleteJson = new JSONObject();
//						athleteJson.put("firstname", athlete.firstname);
//						athleteJson.put("surname", athlete.surname);
//						athleteJson.put("username", athlete.username);
//						jArray.put(athleteJson);
//				}
//				feedback.put("status", "success");
//				feedback.put("athletes", jArray);
//			}
//			else {
//				feedback.put("status", "failed");
//			}
//			
//		}catch (Exception e) {
//    		try{
//				feedback.put("status", "failed");
//			}
//			catch (Exception es){
//
//			}
//		}
//    	 return feedback.toString();
//    }
//   
//    @RequestMapping("sportForCoach")
//    public String requestSportForCoach(@RequestParam("username") String username) {
//    	
//    	String feedback = "";
//    	
//    	try{
//			String getSportForCoachResult = ServerLogic.getSportForCoach(username);
//			if (getSportForCoachResult != "") {
//				JSONObject responseObject = new JSONObject().put("status", getSportForCoachResult);  
//				feedback = responseObject.toString(); 
//			}
//			else {
//				feedback = new JSONObject()
//		                  .put("status", "failed").toString();
//			}
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//			try {
//				feedback = new JSONObject()
//				          .put("status", "failed").toString();
//			} catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//    	
//    	
//    	
//    	
//    	return feedback; 
//
//    }
//}