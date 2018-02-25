package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.List;

public class Workout {
	
	public String date;
	
	public String type;
	
	public Athlete athlete;
	
	public int duration;

	public int kilometres;
	
	public List<String> getPulsList() {
		return pulsList;
	}

	public void setPulsList(List<String> pulsList) {
		this.pulsList = pulsList;
	}
	
	public List<String> pulsList = new ArrayList<String> ();

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Athlete getAthlete() {
		return athlete;
	}

	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getKilometres() {
		
		return kilometres;
	}

	public void setKilometres(int kilometres) {
		this.kilometres = kilometres;
	}
}
