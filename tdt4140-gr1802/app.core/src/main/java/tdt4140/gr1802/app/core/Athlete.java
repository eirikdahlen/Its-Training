package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Athlete extends User {
	
	private List <String> coaches = new ArrayList<String>();
	
	private List <String> queuedCoaches = new ArrayList <String>();
	
	private Database database = new Database();
	
	private List<Workout> allWorkouts;
	
	private int maxHR;
	
	public Athlete (String username, String password, String name, List <String> coaches, List <String> queuedCoaches) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.coaches = coaches;
		this.queuedCoaches = queuedCoaches;
	}
	
	public Athlete (String username, String password, String name) {
		this.username = username;
		this.password = password;
		this.name = name;
	}
	
	// getters and setters 
	public List<String> getCoaches() { return coaches; }
	
	public void setCoaches(List<String> coaches) { this.coaches = coaches; }

	public List<String> getQueuedCoaches() { return queuedCoaches; }
	
	public List<Workout> getAllWorkouts(){ return database.getAllWorkouts(this); }
	
	public void setMaxHR(int maxHR) {this.maxHR = maxHR; }
	
	public int getMaxHR() {return this.maxHR; }

	
	// Method called by a coach-object. The coach that calls this method wants to be this athletes trainer. The coach will be
	// queued in "queuedCoaches" so that the athlete later can accept the coach as his/her coach.
	public void queueCoach (String newCoach) {
		queuedCoaches.add(newCoach);
		// add to queue in database
		database.addRequestCoachToAthlete(this, newCoach);
	}

	// Method for the athlete to approve coach-request (coaches in "queuedCoaches") 
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
	
	// Method for the athlete to decline a coach-request
	public void declineCoach(String coachUsername) {
		if (queuedCoaches.contains(coachUsername)) {
			queuedCoaches.remove(coachUsername);
			// remove from queue in database
			database.deleteCoachRequestForAthlete(this, coachUsername);
		}
	}
	
	//  Calls the queueAthlete-method in the Coach-class, and queue them self in queuedAthletes at the Coach-object
	public void sendCoachRequest (String coach) {
		Coach c = database.getCoach(coach);
		if (coaches.contains(coach)) {
			System.out.println("Athlete is already asigned to this coach...");
		} else {
			c.queueAthlete(this.getUsername()) ;
			database.addRequestAthleteToCoach(c, this.getUsername());
		}
	}
	
	// When request approved by the Coach, add to the Coach-list
	public void addCoach(String coach) {
		coaches.add(coach);
	}
	
	// Check if the Athlete has the coach
	public Boolean hasCoach(String coach) {
		if (coaches.contains(coach)) {
			return true;
		}
		return false;
	}
	
	// Remove the coach from the coach-list and update the database for both the athlete and the coach.
	public void removeCoach(String coach) {
		if (hasCoach(coach)) {
			coaches.remove(coach);
			database.deleteCoachForAthlete(this, coach);
			database.deleteAthleteForCoach(database.getCoach(coach), this.getUsername());
		}
	}
}
