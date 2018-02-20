package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Athlete extends User {
	
	private List <String> coaches = new ArrayList<String> () ;
	
	private List <String> queuedCoaches = new ArrayList <String> () ;
	

	public Athlete (String username, String name, List <String> coaches, List <String> queuedCoaches) {
		this.username = username ;
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

	private void approveCoach () {
		for ( int n = 0; n < queuedCoaches.size(); n++) {
			System.out.println(queuedCoaches.get(n));
			System.out.println("Type 'Accept' to accept this coach, 'Decline' to decline this coach");
			Scanner scanner = new Scanner (System.in) ;
			String answer = scanner.nextLine() ;
			if ( answer == ("Accept")) {
				coaches.add(queuedCoaches.get(n)) ;
				queuedCoaches.remove(n) ;
			} else if (answer == "Decline") {
				queuedCoaches.remove(n) ;
			}
		}
	}
/*	
	public void addCoach (String coach) {
		if (coaches.contains(coach)) {
			throw new IllegalArgumentException("Athlete is already asigned to this coach...") ;
		} else {
			Database.getCoach(coach).queueAthlete(this.getUsername()) ;
		}
	}
*/
}
