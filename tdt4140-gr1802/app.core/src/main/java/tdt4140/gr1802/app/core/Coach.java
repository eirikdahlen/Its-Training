package tdt4140.gr1802.app.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class Coach extends User {
	
	private List<String> athletes = new ArrayList<String>();
	
	private List<String> queuedAthletes = new ArrayList<String>();
	
	private List<String> notes = new ArrayList<String>();
	
	private Database database = App.getDb();
	
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

	// Getters and setters
	public List<String> getQueuedAthletes() { return queuedAthletes; }

	public void setAthletes(List<String> athletes) { this.athletes = athletes; }

	public List<String> getAthletes() { return athletes; }
	
	public List<String> getNotes() { return notes; }
	
	// Method called by a athlete-object. The athlete that calls this method wants to be this coaches athlete. The athlete will be
	// queued in "queuedAthletes" so that the coach later can accept the athlete as his/her coach.
	public void queueAthlete (String newAthlete) {
		queuedAthletes.add(newAthlete) ;
		// add to queue in database
		database.addRequestAthleteToCoach(this, newAthlete);
	}
	
	// Method for the coach to approve athlete-request (athlete in "queuedAthletes") 
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
	
	
	// Method for the coach to decline an athlete-request
	public void declineAthlete(String athleteUsername) {
		if (queuedAthletes.contains(athleteUsername)) {
			queuedAthletes.remove(athleteUsername);
			// remove from queue in database
			database.deleteAthleteRequestForCoach(this, athleteUsername);
		}
	}
	
	// Calls the queueCoach-method in the Athlete-class, and queue them self in queuedCoaches at the Athlete-object
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
	
	// When request approved by the Athlete, add to the Athlete-list
	public void addAthlete(String athlete) {
		athletes.add(athlete);
	}
	
	// Check if the Coach has the Athlete in the Athletes-list
	public Boolean hasAthlete(String athlete) {
		if (athletes.contains(athlete)) {
			return true;
		}
		return false;
	}
	
	// Remove the athlete from the athlete-list and update the database for both the athlete and the coach.
	public void removeAthlete(String athlete) {
		if (hasAthlete(athlete)) {
			athletes.remove(athlete);
			database.deleteAthleteForCoach(this, athlete);
			database.deleteCoachForAthlete(database.getAthlete(athlete), this.getUsername());
		}
	}
	
	// Get coach top 3 athletes 
	public List<Athlete> getTop3Athletes() {
		List<Athlete> sorted = new ArrayList<>();
		List<Athlete> top3 = new ArrayList<>();
		
		for (String name : athletes) {
			database.getAthlete(name).getAllWorkouts();
			sorted.add(database.getAthlete(name));
		}
		System.out.println("sorted before sort: " + sorted);
		if (sorted.size() > 1) { Collections.sort(sorted);}
		
		if (sorted.size() <= 3) {
			top3.addAll(sorted);
		} else {
			top3.add(sorted.get(0)); top3.add(sorted.get(1)); top3.add(sorted.get(2));

		}
		
		return top3; 
		
	}
	// ----- get athletes not working out since -----
	// TODO: Move this method to analyze athletes? 
	public List<Athlete> getAthletesNotWorkingOutSince(Date date) {
		List<Athlete> resultAthletes = new ArrayList<>();
		
		for (String athName : this.athletes) {
			if (database.getAthlete(athName).getDateLastWorkout().before(date)) {
				resultAthletes.add(database.getAthlete(athName));
			}
		}
		
		return resultAthletes;	
	}
	
	public HashMap<LocalDate, String> getNotesMap() {
		List<String> notes = database.getCoachNotes(this.username);
		HashMap<LocalDate, String> dateString = new HashMap<>();
		
		for (String note : notes) {
			int year = Integer.parseInt(note.substring(0, 4));
			int month = Integer.parseInt(note.substring(5, 7));
			int day = Integer.parseInt(note.substring(8, 10));
			String text = note.substring(11);
			
			LocalDate date = LocalDate.of(year, month, day);
			dateString.put(date, text);	
		}
		
		return dateString;
	}
	
	public List<LocalDate> getDatesWithNotes() {
		List<LocalDate> dates = new ArrayList<>();
		dates.addAll(getNotesMap().keySet());	
		return dates;
	}
	
	public String getNote(LocalDate date) {
		HashMap<LocalDate, String> map = getNotesMap();
		
		for (HashMap.Entry<LocalDate, String> entry : map.entrySet()) {
			if (entry.getKey().isEqual(date)) {
				return entry.getValue();
			}
		}
		
		return "";
	}
	
	public void saveNote(LocalDate date, String text) {
		String asString = date.toString() + " " + text;
		database.addCoachNotes(this.username, asString);
	}
	
	public void updateNote(LocalDate date, String text) {
		String asString = date.toString() + " " + text; 
		database.updateCoachNotes(this.username, asString);
	}
	
	public List<List<Double>> getWorkoutsStartpoints(){
		List<List<Double>> liste = new ArrayList<>();
		
        for (String ath : this.getAthletes()) {
    			Athlete athlete = database.getAthlete(ath);
    			List<Workout> workouts = database.getAllWorkouts(athlete);
    				for (Workout w : workouts) {
    					List<List<Double>> gpx = w.getGpxData();
    					if (gpx != null) {
    						liste.add(gpx.get(0));
    					}
    				}
        }
        System.out.println("liste inni coach: "+liste);
        return liste;
	}
}	
