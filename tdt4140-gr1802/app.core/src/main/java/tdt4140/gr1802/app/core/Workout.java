package tdt4140.gr1802.app.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Workout {
	
	public String date, type, kilometres;

	public int duration;
		
	public Athlete athlete;
	
	public List<String> pulsList = new ArrayList<String> ();
	
	public String filePath = "";
	
	CSVReader reader = new CSVReader(filePath);
	
	public Workout(Athlete athl, String filepath) throws IOException {
		this.date = reader.getDate();
		this.type = reader.getType();
		this.duration = reader.getDuration();
		this.kilometres = reader.getDistance();
		this.athlete = athl;
	}

	public Athlete getAthlete() {
		return athlete;
	}

	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}
	
}