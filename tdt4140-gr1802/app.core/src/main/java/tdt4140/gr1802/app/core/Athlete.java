package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Athlete extends User {
	
	protected List <String> coaches = new ArrayList<String> () ;
	
	private List <String> queuedCoaches = new ArrayList <String> () ;
	
	protected List <String> pendingCoaches = new ArrayList <String> ();
	
	private Database database = new Database();
	
	private List<Workout> allWorkouts;
	

	public Athlete (String username, String name, List <String> coaches, List <String> queuedCoaches) {
		this.username = username ;
		this.name = name ;
		this.coaches = coaches ;
		this.queuedCoaches = queuedCoaches ;
	}
	
	public Athlete (String username, String password, String name, List <String> coaches, List <String> queuedCoaches) {
		this.username = username ;
		this.password = password;
		this.name = name ;
		this.coaches = coaches ;
		this.queuedCoaches = queuedCoaches ;
	}
	
	
	public void queueCoach (String newCoach) {
		queuedCoaches.add(newCoach) ;
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
	public void approveCoach () {
		for ( int n = 0; n < queuedCoaches.size(); n++) {
			String coach = queuedCoaches.get(n);
			System.out.println(coach);
			System.out.println("Type 'Accept' to accept this coach, 'Decline' to decline this coach");
			Scanner scanner = new Scanner (System.in) ;
			String answer = scanner.nextLine() ;
			if ( answer == ("Accept")) {
				coaches.add(coach) ;
				database.getCoach(coach).pendingAthletes.remove(this.getUsername());
				database.getCoach(coach).athletes.add(this.getUsername());
				queuedCoaches.remove(n) ;
			} else if (answer == "Decline") {
				queuedCoaches.remove(n) ;
			}
		}
	}
	
	//  Adds coaches in pendingCoach-list and calls queueAthlete() in Coach-class. 
	public void addCoach (String coach) {
		if (coaches.contains(coach)) {
			throw new IllegalArgumentException("Athlete is already asigned to this coach...") ;
		} else {
			pendingCoaches.add(coach);
			database.getCoach(coach).queueAthlete(this.getUsername()) ;
		}
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
		}
	}
	
	public List<Workout> getAllWorkouts(){
		return database.getAllWorkouts(this);
	}
	
	// Adds workout.
	private void addWorkout() {
		
	}
}
