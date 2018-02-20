package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Coach extends User {
	
	private List <String> athletes = new ArrayList <String>() ;
	
	private List <String> queuedAthletes = new ArrayList <String> () ;
	
	public Coach (String username, String name, List <String> athletes, List <String> queuedAthletes){
		this.name = name ;
		this.username = username ;
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
	
	private void approveAthlete (String athlete) {
		for ( int n = 0; n < queuedAthletes.size(); n++) {
			System.out.println(queuedAthletes.get(n));
			System.out.println("Type 'Accept' to accept this coach, 'Decline' to decline this coach");
			Scanner scanner = new Scanner (System.in) ;
			String answer = scanner.nextLine() ;
			if ( answer == ("Accept")) {
				athletes.add(queuedAthletes.get(n)) ;
				queuedAthletes.remove(n) ;
			} else if (answer == "Decline") {
				queuedAthletes.remove(n) ;
			}
		}
	}
/*
	public void addAthlete (String athlete) {
		if (athletes.contains(athlete)) {
			throw new IllegalArgumentException("Athlete is already asigned to this coach...") ;
		} else {
			Database.getAthlete(athlete).queueCoach(this.getUsername());
		}
	}
*/
}	
