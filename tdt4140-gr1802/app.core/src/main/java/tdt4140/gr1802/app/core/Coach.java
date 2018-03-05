package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Coach extends User {
	
	protected List <String> athletes = new ArrayList <String>() ;
	
	private List <String> queuedAthletes = new ArrayList <String> () ;
	
	protected List <String> pendingAthletes = new ArrayList <String> () ;
	
	private Database database;
	
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
	}
	
	// Går igjennom alle utøvere i køen og godtar/avslår forspørsler
	public void approveAthlete () {
		for ( int n = 0; n < queuedAthletes.size(); n++) {
			String athlete = queuedAthletes.get(n);
			System.out.println(athlete);
			System.out.println("Type 'Accept' to accept this coach, 'Decline' to decline this coach");
			Scanner scanner = new Scanner (System.in) ;
			String answer = scanner.nextLine() ;
			if ( answer == ("Accept")) {
				athletes.add(athlete) ;
				database.getAthlete(athlete).pendingCoaches.remove(this.username);
				database.getAthlete(athlete).coaches.add(this.username);
				queuedAthletes.remove(n) ;
			} else if (answer == "Decline") {
				queuedAthletes.remove(n);
				database.getAthlete(athlete).pendingCoaches.remove(this.username);
			}
		}
	}
	
	// Legger til utøver i pendingAthletes-listen og kaller queueCoach() i Athletes-klassen
	public void addAthlete (String athlete) {
		if (athletes.contains(athlete)) {
			throw new IllegalArgumentException("Athlete is already asigned to this coach...") ;
		} else {
			pendingAthletes.add(athlete);
			database.getAthlete(athlete).queueCoach(this.getUsername());
		}
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
		}
	}
}	
