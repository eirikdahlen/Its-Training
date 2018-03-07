package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Coach extends User {
	
	protected List <String> athletes = new ArrayList <String>() ;
	
	private List <String> queuedAthletes = new ArrayList <String> () ;
	
	//protected List <String> pendingAthletes = new ArrayList <String> () ;
	
	private Database database = new Database();
	
	// TODO: delete this?
	public Coach (String username, String name, List <String> athletes, List <String> queuedAthletes){
		this.name = name ;
		this.username = username ;
		this.athletes = athletes ;
		this.queuedAthletes = queuedAthletes ;
	}
	
	public Coach (String username, String password, String name, List <String> athletes, List <String> queuedAthletes){
		this.name = name ;
		this.username = username ;
		this.password = password;
		this.athletes = athletes ;
		this.queuedAthletes = queuedAthletes ;
	}
	
	public Coach (String username, String password, String name) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public List<String> getQueuedAthletes() {
		return queuedAthletes;
	}

	public void setAthletes(List<String> athletes) {
		this.athletes = athletes;
	}

	public List<String> getAthletes() {
		return athletes;
	}
	
	public void queueAthlete (String newAthlete) {
		queuedAthletes.add(newAthlete) ;
		// add to queue in database
		database.addRequestAthleteToCoach(this, newAthlete);
	}
	
	// Iterate through  queued athletes and accepts/declines requests.
	public void approveAthlete (String athleteUsername) {
		if (queuedAthletes.contains(athleteUsername)) {
			Athlete athlete = database.getAthlete(athleteUsername);
			queuedAthletes.remove(athleteUsername);
			// remove from queue in database
			database.deleteAthleteRequestForCoach(this, athleteUsername);
			athletes.add(athleteUsername);
			athlete.addCoach(this.getUsername());
			// add to coaches athletes-list in database
			database.addAthleteToCoach(this, athleteUsername);
			// add to athletes coach-list in database
			database.addCoachToAthlete(athlete, this.getUsername());
		}
	}
	
	public void declineAthlete(String athleteUsername) {
		if (queuedAthletes.contains(athleteUsername)) {
			queuedAthletes.remove(athleteUsername);
			// remove from queue in database
			database.deleteAthleteRequestForCoach(this, athleteUsername);
		}
	}
	
	// Adds athletes i PendingAthletes-listen and calls queueCoach in Athlete-class.
	public void sendAthleteRequest (String athlete) {
		Athlete a = database.getAthlete(athlete);
		if (athletes.contains(athlete)) {
			throw new IllegalArgumentException("Athlete is already asigned to this coach...") ;
		} else {
			//pendingAthletes.add(athlete);
			a.queueCoach(this.getUsername());
			database.addRequestCoachToAthlete(a, this.getUsername());
		}
	}
	
	public void addAthlete(String athlete) {
		athletes.add(athlete);
	}
	
	public Boolean hasAthlete(String athlete) {
		if (athletes.contains(athlete)) {
			return true;
		}
		return false;
	}
	
	public void removeAthlete(String athlete) {
		if (hasAthlete(athlete)) {
			athletes.remove(athlete);
			database.deleteAthleteForCoach(this, athlete);
			database.deleteCoachForAthlete(database.getAthlete(athlete), this.getUsername());
		}
	}
}	
