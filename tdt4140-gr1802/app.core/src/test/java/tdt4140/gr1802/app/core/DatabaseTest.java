package tdt4140.gr1802.app.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;


public class DatabaseTest {
	
	Database database;
	
	public DatabaseTest() {
		this.database = new Database();
	}

	
	public static void main(String[] args) throws IOException {
		
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
		
				
	}
	
	
	public void testCreateAthlete() {
		
		//adds athlete for testing
		Athlete athlete1 = new Athlete("Nils22", "Nils", new ArrayList<String>(), new ArrayList<String>());
		
		//adds athlete to database
		database.createAthlete(athlete1);
		
	}
	
	
	public void testCreateCoach() {
		
		//adds coack for testing
		Coach coach1 = new Coach("Petter74", "Petter", new ArrayList<String>(), new ArrayList<String>());
		
		//adds athlete to database
		database.createCoach(coach1);
		
	}
	
	
	public void testCreateWorkout() throws IOException {
		
		//adds athlete and workout for testing
		Athlete athlete1 = new Athlete("Nils22", "Nils", new ArrayList<String>(), new ArrayList<String>());
		Workout workout1 = new Workout(athlete1, "/Users/petter/Documents/oppdatertCSV.csv");
		
		//adds to database
		database.createWorkout(workout1);
	
	}

	
	
	//_______jUnit_______

	@Test
	public void testGetAthlete() {

		//adds athlete for testing
		Athlete athlete1 = new Athlete("Nils34", "Nils", new ArrayList<String>(), new ArrayList<String>());
				
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
		Coach coach1 = new Coach("Petter74", "Petter", new ArrayList<String>(), new ArrayList<String>());
				
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
		Athlete athlete1 = new Athlete("Nils22", "Nils", new ArrayList<String>(), new ArrayList<String>());
		Workout workout1 = new Workout(athlete1, "01-01-2016 10:34:37","ROWING", 133,24.39, new ArrayList<String>() );
		
		//adds workout to database
		database.createWorkout(workout1);
	
		//retrieves workout-object from database
		Workout retrievedWorkout = database.getWorkout(athlete1, workout1.getDateString());

		//compares datestring, to see if its the same workout
		assertEquals(workout1.getDateString(), retrievedWorkout.getDateString());
		
		
	}

	

}
