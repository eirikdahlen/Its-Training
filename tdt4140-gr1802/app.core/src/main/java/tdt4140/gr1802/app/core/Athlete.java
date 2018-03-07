package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Athlete extends User {
	
	private List <String> coaches = new ArrayList<String> () ;
	
	private List <String> queuedCoaches = new ArrayList <String> () ;
	
	//protected List <String> pendingCoaches = new ArrayList <String> ();
	
	private Database database = new Database();
	
	private List<Workout> allWorkouts;
	
	// TODO: delete this?
	public Athlete (String username, String name, List <String> coaches, List <String> queuedCoaches) {
		this.username = username ;
		this.name = name ;
		if (coaches != null) {
			this.coaches = coaches ;
		} 
		this.queuedCoaches = queuedCoaches ;
	}
	
	public Athlete (String username, String password, String name, List <String> coaches, List <String> queuedCoaches) {
		this.username = username ;
		this.password = password;
		this.name = name ;
		this.coaches = coaches ;
		this.queuedCoaches = queuedCoaches ;
	}
	
	public Athlete (String username, String password, String name) {
		this.username = username;
		this.password = password;
		this.name = name;
	}
	
	public void queueCoach (String newCoach) {
		queuedCoaches.add(newCoach) ;
		// add to queue in database
		database.addRequestCoachToAthlete(this, newCoach);
	}

	public List<String> getCoaches() {
		return coaches;
	}

	public void setCoaches(List<String> coaches) {
		this.coaches = coaches;
	}

	public List<String> getQueuedCoaches() {
		return queuedCoaches;
	}
	

	// Iterate through queuedCoaches and accepts/declines requests. 
	public void approveCoach (String coachUsername) {
		if (queuedCoaches.contains(coachUsername)) {
			Coach coach = database.getCoach(coachUsername);
			queuedCoaches.remove(coachUsername);
			// remove from queue in database
			database.deleteCoachRequestForAthlete(this, coachUsername);
			coaches.add(coachUsername);
			coach.addAthlete(this.getUsername());
			// add to athletes coach-list in database
			database.addCoachToAthlete(this, coachUsername);
			// add to coaches athletes-list in database
			database.addAthleteToCoach(coach, this.getUsername());
		}
	}
	
	public void declineCoach(String coachUsername) {
		if (queuedCoaches.contains(coachUsername)) {
			queuedCoaches.remove(coachUsername);
			// remove from queue in database
			database.deleteCoachRequestForAthlete(this, coachUsername);
		}
	}
	
	//  Adds coaches in pendingCoach-list and calls queueAthlete() in Coach-class. 
	public void sendCoachRequest (String coach) {
		Coach c = database.getCoach(coach);
		if (coaches.contains(coach)) {
			System.out.println("Athlete is already asigned to this coach...");
		} else {
			//pendingCoaches.add(coach);
			c.queueAthlete(this.getUsername()) ;
			database.addRequestAthleteToCoach(c, this.getUsername());
		}
	}
	
	public void addCoach(String coach) {
		coaches.add(coach);
	}
	
	public Boolean hasCoach(String coach) {
		if (coaches.contains(coach)) {
			return true;
		}
		return false;
	}
	
	public void removeCoach(String coach) {
		if (hasCoach(coach)) {
			coaches.remove(coach);
			database.deleteCoachForAthlete(this, coach);
			database.deleteAthleteForCoach(database.getCoach(coach), this.getUsername());
		}
	}
	
	public List<Workout> getAllWorkouts(){
		return database.getAllWorkouts(this);
	}
	
	// Adds workout.
	private void addWorkout() {
		
	}
}
