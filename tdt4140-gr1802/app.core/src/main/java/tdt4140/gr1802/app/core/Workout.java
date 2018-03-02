package tdt4140.gr1802.app.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Workout {
	
	private Date date;
	
	private String dateString, type;
	
	private double kilometres;

	private int duration;
		
	private Athlete athlete;
	
	private List<String> pulsList = new ArrayList<String> ();
	
	public String filePath = "";
	
	private int maxHR;
	
	private int averageHR;
	

	public Workout(Athlete athl, String filepath) throws IOException {
		this.filePath = filepath;
		CSVReader reader = new CSVReader(filePath);
		
		this.pulsList = reader.getPulse();
		
		this.dateString = reader.getDate();
		this.type = reader.getType();
		this.duration = reader.getDuration();
		this.kilometres = reader.getDistance();
		this.athlete = athl;
		this.maxHR = Integer.parseInt(Collections.max(pulsList));
		int sum = 0;
		for (String puls: pulsList) {
			int m = Integer.parseInt(puls);
			sum += m;
		}
		this.averageHR = sum / pulsList.size();
		
		//parse string to date-object
		try {
			this.date = parseDate(this.dateString);
		}
		catch(Exception fnf) {
				fnf.printStackTrace();
		}
		
	}

	
	public Workout(Athlete athl, String dateString, String type, int duration, double kilometres, List<String> pulsList) {
		this.athlete = athl;
		this.dateString = dateString;
		this.type = type;
		this.duration = duration;
		this.kilometres = kilometres;
		this.pulsList = pulsList;
		if (!pulsList.isEmpty()) {
			this.maxHR = Integer.parseInt(Collections.max(pulsList));
			int sum = 0;
			for (String puls: pulsList) {
				int m = Integer.parseInt(puls);
				sum += m;
			}
			this.averageHR = sum / pulsList.size();
			
			System.out.println(averageHR);
			System.out.println(maxHR);
		}
		
		//parse string to date-object
		try {
			this.date = parseDate(this.dateString);
		}
		catch(Exception fnf) {
				fnf.printStackTrace();
		}
		
	}
	
	
	public Date parseDate(String date) throws ParseException {
		
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return df.parse(date);
		
	}

	
	public Athlete getAthlete() {
		return athlete;
	}

	
	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}

	
	public Date getDate() {
		return date;
	}

	
	public String getDateString() {
		return dateString;
	}

	
	public String getType() {
		return type;
	}

	
	public double getKilometres() {
		return kilometres;
	}

	
	public int getDuration() {
		return duration;
	}

	
	public List<String> getPulsList() {
		return pulsList;
	}
	
	public Integer getMaxHR() {
		return maxHR;
	}
	
	public Integer getAverageHR() {
		return averageHR;
	}

	
}