
package tdt4140.gr1802.app.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;


public class DatabaseTest {
	
	Database database;
	
	public DatabaseTest() {
		this.database = new Database();
	}

	public static void main(String[] args) throws Exception {
	
		//uncomment the tests to run
		
		DatabaseTest test = new DatabaseTest();
		
		//___________________
		//test.testCreateAthlete();
		//if username not in use, check to see if athlete is in MongoDB
		
		
		//___________________
		//test.testCreateCoach();
		//if username not in use, check to see if coach is in MongoDB
		
		
		//_____FUNKER BARE HVIS MAN HAR CSV FIL PÅ RIKTIG STED____
		//test.testCreateWorkout();
		//if datetime not in use, check to see if session is in MongoDB (in the right collection)
		
		
		//______________________
		//test.testGetAllWorkouts();
		//should print the three first workouts for athlete in test
		
		//_____________________
		//test.testAddCoachToAthlete();
		//if coach not already in list, check to see if added to athlete's coaches-List MongoDB
		
		//____________________
		//test.testGetCoachesForAthlete();
		//check to see if printed list matches athlete's coach-list in database
		
		
		//____________________
		//test.testAddAthleteToCoach();
		//if coach not already in coachs's athlete-list, adds to this list
		
		//____________________
		//test.testDeleteCoachForAthlete();
		//if coach in athlete's coach-list, check to see if deleted 
		
		//____________________
		//test.testDeleteAthleteForCoach();
		//if athlete in coach's athlete-list, check to see if deleted 
		
		//____________________
		//test.testAddRequestAthleteToCoach();
		//if athlete not in coach's request-list, check to see if added 
		
		//____________________
		//test.testAddRequestCoachToAthlete();
		//if athlete not in coach's request-list, check to see if added 
		
		
		//____________________
		//test.testDeleteCoachRequestForAthlete();
		//if coach in athlete's request-list, check to see if deleted 
		
		
		//____________________
		//test.testDeleteAthleteRequestForCoach();
		//test athlete in coach's request-list, check to see if deleted 
		
		//____________________
		//test.testAddMaxHR()
		//check to see if athletes MaxHR is updated
		
		//____________________
		//test.testAddSleepData();
		//check to see if athletes sleepData is updated
		
		//______________________
		test.testGetSleepData();
		//check if output matches athlete's sleepdata in database
			
	}
	
	
	public void testCreateAthlete() {
		

		
		//adds athlete for testing
		Athlete athlete1 = new Athlete("Nils22", "12345", "Nils");
		
		//adds athlete to database
		database.createAthlete(athlete1);
		
	}
	
	
	public void testCreateCoach() {
		
		//adds coack for testing
		Coach coach1 = new Coach("Petter74", "passord", "Petter");
		
		//adds athlete to database
		database.createCoach(coach1);
		
		//legger til ekstra 
		Coach coach2 = new Coach("Erna33", "erna123","Erna");
		Coach coach3 = new Coach("Jens32","jens123", "Petter");
		
		database.createCoach(coach2);
		database.createCoach(coach3);
	}
	
	
	public void testCreateWorkout() throws IOException {
		
		//adds athlete and workout for testing
		Athlete athlete1 = new Athlete("Kjetil123", "passord", "Kjetil");
		
		URL path = getClass().getResource("CSV1.csv");
		
		Workout workout1 = new Workout(athlete1, path, true, null);
		
		//adds athlete to database
		database.createAthlete(athlete1);
		
		//adds workout to database
		database.createWorkout(workout1);
	
	}
	
	public void testAddMaxHR() {
		Athlete athlete1 = new Athlete("Petter123", "passord", "Petter");
		
		database.addMaxHR(athlete1, 190);
		
	}
	
	public void testGetAllWorkouts() throws IOException {
		

		//creates athlete and workout for testing 
		Athlete athlete1 = new Athlete("Nils22", "passord", "Nils");
		Workout workout1 = new Workout(athlete1, "01-01-2016 10:34:37","ROWING", 133,24.39, new ArrayList<String>(), null );
		Workout workout2 = new Workout(athlete1, "02-01-2016 10:34:37","SKIING", 133,24.39, new ArrayList<String>(), null );
		
		//adds workout to database
		database.createWorkout(workout1);
		database.createWorkout(workout2);
	
		//retrieves workout-object from database
		List<Workout> retrievedWorkouts = database.getAllWorkouts(athlete1);
		
		
		//prints only the three first elemements to confirm test
		int i = 0;
		System.out.println();
		System.out.println("Henter 3 workouts fra " + athlete1.getUsername());
		for (Workout element : retrievedWorkouts) {
		    System.out.println(element.getDateString());

		   i++;
		   if (i > 2) {
			   break;
		   }
		}
	}
	
	public void testAddCoachToAthlete() {
		
		//get coachList first -> print list -> update coach_list -> print again
		
		//____________THIS PART OF CODE IS GetCoachesForAthlete-method_______
		//creates athlete for testing	
		Athlete athlete1 = new Athlete("Nils22", "passord", "Nils");
		
		List<String> coaches = database.getCoachesForAthlete(athlete1);
		
		System.out.println("Coach-list before adding");
		for (String element : coaches) {
		    System.out.println(element);
		}
		//_______________________________________
		
		
		
		//creates coaches for testing 
		Coach coach1 = new Coach("Erna33", "passord","Erna");
		Coach coach2 = new Coach("Jens32", "passord","Petter");
		
		//the actual addCoachToAthlete-method
		database.addCoachToAthlete(athlete1, coach1.getUsername());
		
		
		
		//____________THIS PART OF CODE IS GetCoachesForAthlete-method_______
		List<String> coaches2 = database.getCoachesForAthlete(athlete1);
		
		System.out.println("___________________");
		System.out.println("coach list after update");
		for (String element : coaches2) {
		    System.out.println(element);
		}
		//_______________________________________
		
		
	
	}
	
	public void testAddAthleteToCoach() {
		
		//get coachList first -> print list -> update coach_list -> print again
		
		//____________THIS PART OF CODE IS GetCoachesForAthlete-method_______
		//creates coach for testing	
		Coach coach1 = new Coach("Erna33","passord", "Erna");
	
		List<String> athletes = database.getAthleteForCoach(coach1);

		System.out.println("Athlete-list before adding");
			for (String element : athletes) {
				
				System.out.println(element);
		}
		//_______________________________________
//		
		
		
		//creates athletes for testing 
		Athlete athlete1 = new Athlete("Nils22", "passord","Nils");
		
		Athlete athlete2 = new Athlete("Nils100","passord", "Nilse");
		
		//the actual addAthleteToCoach-method
		database.addAthleteToCoach(coach1, athlete1.getUsername());
		
		
		
		//____________THIS PART OF CODE IS GetCoachesForAthlete-method_______
		List<String> athletes2 = database.getCoachesForAthlete(athlete1);
		
		System.out.println("___________________");
		System.out.println("athlete-list after update");
		for (String element : athletes) {
		    System.out.println(element);
		}
		//_______________________________________

		
	}
	
	public void testAddRequestAthleteToCoach() {
		//get coachList first -> print list -> update coach_list -> print again
		
		//____________THIS PART OF CODE IS GetCoachesForAthlete-method_______
		//creates coach for testing	
		Coach coach1 = new Coach("Erna33", "passord", "Erna");
	
		List<String> athletes = database.getRequestsForCoach(coach1);

		System.out.println("request-list before adding");
			for (String element : athletes) {
				
				System.out.println(element);
		}
		//_______________________________________
//		
		
		
		//creates athletes for testing 
		Athlete athlete1 = new Athlete("Nils22","passord", "Nils");
		
	
		
		//the actual database-method
		database.addRequestAthleteToCoach(coach1, athlete1.getUsername());
		
		
		
		//____________THIS PART OF CODE IS GetCoachesForAthlete-method_______
		//creates coach for testing	
		
	
		List<String> athletes2 = database.getRequestsForCoach(coach1);

		System.out.println("request-list after adding");
			for (String element : athletes2) {
				
				System.out.println(element);
		}

		
	}
	
	public void testAddRequestCoachToAthlete() {
		//get coachList first -> print list -> update coach_list -> print again
		
		//____________THIS PART OF CODE IS GetCoachesForAthlete-method_______
		//creates athlete for testing	
		Athlete athlete1 = new Athlete("Nils34", "passord","Nils");
		
	
		List<String> athletes = database.getRequestsForAthlete(athlete1);

		System.out.println("request-list before adding");
			for (String element : athletes) {
				
				System.out.println(element);
		}
		//_______________________________________
//		
		
		
		//creates coache for testing 
		Coach coach1 = new Coach("Erna33", "passord","Erna");
		
		//the actual database-method
		database.addRequestCoachToAthlete(athlete1, coach1.getUsername());
		
		
		
		//____________THIS PART OF CODE IS GetCoachesForAthlete-method_______
		//creates coach for testing	
		
	
		List<String> athletes2 = database.getRequestsForAthlete(athlete1);

		System.out.println("request-list after adding");
			for (String element : athletes2) {
				
				System.out.println(element);
		}

		
	}
	
	public void testGetCoachesForAthlete() {
		
		
		//creates athlete for testing	
		Athlete athlete1 = new Athlete("Nils22","passord", "Nils");
		
		List<String> coaches = database.getCoachesForAthlete(athlete1);
		
		System.out.println("prints coach-list for "+athlete1.getUsername());
		for (String element : coaches) {
		    System.out.println(element);
		}
		
	}
	
	public void testDeleteCoachForAthlete() {
		Athlete athlete1 = new Athlete("Nils22","passord", "Nils");
		Coach coach1 = new Coach("Erna33", "passord","Erna");
		database.deleteCoachForAthlete(athlete1, coach1.getUsername());
		
	}
	public void testDeleteAthleteForCoach() {
		
		Coach coach1 = new Coach("Erna33", "passord","Erna");
		Athlete athlete1 = new Athlete("Nils22", "passord","Nils");
		database.deleteAthleteForCoach(coach1, athlete1.getUsername());
		
	}

	
	public void testDeleteCoachRequestForAthlete() {
		Athlete athlete1 = new Athlete("Nils22", "passord","Nils");
		Coach coach1 = new Coach("Erna33", "passord","Erna");
		database.deleteCoachRequestForAthlete(athlete1, coach1.getUsername());
	}
	
	public void testDeleteAthleteRequestForCoach() {
		
		Coach coach1 = new Coach("Erna33", "passord","Erna");
		Athlete athlete1 = new Athlete("Nils22", "passord", "Nils");
		database.deleteAthleteRequestForCoach(coach1, athlete1.getUsername());
	}
	
	//_______sleepdata_______
	public void testAddSleepData() throws Exception {
		Athlete athlete1 = new Athlete("TeddyWestside", "theodor", "Theodor");
		
		
		URL filePath = new File("/Users/petter/Downloads/sleepdata_ida.csv").toURI().toURL();
		CSVsleep test = new CSVsleep(filePath);
		
		List<List<String>> sleepdata = test.getSleepData();
		athlete1.setSleepData(sleepdata);
		
		
		database.addSleepData(athlete1);
	}
	
	public void testGetSleepData() {
		Athlete athlete1 = new Athlete("TeddyWestside", "theodor", "Theodor");
		List<List<String>> sleepdata = database.getSleepData(athlete1);
		System.out.println("sleep-data:");
		for (List <String> s : sleepdata) {
			System.out.println(""+s.get(0)+", "+s.get(1)+ ", " +s.get(2));
		}
	}
	
	//__________________
	
/*
	public void testGetAllWorkoutsNonExistingAthlete() {
		

		//creates athlete and workout for testing 
		Athlete athlete1 = new Athlete("Nils54", "Nils", new ArrayList<String>(), new ArrayList<String>());


		//retrieves workout-object from database
		List<Workout> retrievedWorkouts = database.getAllWorkouts(athlete1);
		
		for (Workout element : retrievedWorkouts) {
		    System.out.println(element.getDateString());

		    // ...
		}

	}
	*/

	
	
	
	//_______jUnit_______

	@Test
	public void testGetAthlete() {

		//adds athlete for testing
		Athlete athlete1 = new Athlete("TeddyWestside", "theodor","Bajsunge");
				
		//adds athlete to database
		database.createAthlete(athlete1);
	
		//retrieves coach-object from database
		Athlete retrievedAthlete = database.getAthlete(athlete1.getUsername());

		//compares username, to see if its the same coach
		assertEquals(athlete1.getUsername(), retrievedAthlete.getUsername());
		
		
	}
	
	
	@Test
	public void testGetCoach() {

		//adds coack for testing
		Coach coach1 = new Coach("petter22", "petter123","Petter");
				
		//adds athlete to database
		database.createCoach(coach1);
	
		//retrieves coach-object from database
		Coach retrievedCoach = database.getCoach(coach1.getUsername());

		//compares username, to see if its the same coach
		assertEquals(coach1.getUsername(), retrievedCoach.getUsername());
		
		
	}
	
	
	@Test
	public void testGetWorkout() throws IOException {

		//creates athlete and workout for testing 
		Athlete athlete1 = new Athlete("williamkvaale","test123","William Kvaale");
		
		
//		String path = "src/test/resources/tdt4140/gr1802/app/core/CSV5.csv";
		
		URL path = getClass().getResource("CSV5.csv");
		
		Workout workout1 = new Workout(database.getAthlete("williamkvaale"),path, null);
		
		//adds workout to database
		database.createWorkout(workout1);
	
		//retrieves workout-object from database
		Workout retrievedWorkout = database.getWorkout(athlete1, workout1.getDateString());

		//compares datestring, to see if its the same workout
		assertEquals(workout1.getDateString(), retrievedWorkout.getDateString());
	}

	
	@Test
	public void testGetPulseList() throws IOException {
		
		Athlete athl = new Athlete("williamkvaale","test123","William Kvaale");
		
//		String path = "src/test/resources/tdt4140/gr1802/app/core/CSV5.csv";
		
		URL path = getClass().getResource("CSV1.csv");
		
		Workout wo1 = new Workout(athl, path, null);
		
		Workout wo2 = new Workout(database.getAthlete("williamkvaale"),path, null);
		
		assertTrue(wo1.getAthlete().getUsername().equals(wo2.getAthlete().getUsername()));
		
		assertEquals(wo1.getPulsList(),wo2.getPulsList());
		
		
	}


	
}
	