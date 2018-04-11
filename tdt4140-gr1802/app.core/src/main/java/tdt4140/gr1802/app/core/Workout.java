package tdt4140.gr1802.app.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.lynden.gmapsfx.javascript.object.LatLong;

public class Workout implements Comparable<Workout>{
	
	private Date date;
	
	private String dateString, type;
	
	private double kilometres;

	private int duration;
		
	private Athlete athlete;
	
	private List<String> pulsList = new ArrayList<String> ();
	
	public URL filePath;
	
	private int maxHR;
	
	private int athleteMaxHR;
	
	private int averageHR;
	
	private boolean visibleForCoaches;
	
	private List<List<Double>> gpxData;
	
	private GPXReader gpxReader = new GPXReader();
	

	public Workout(Athlete athl, URL path, InputStream gpxFilepath) throws IOException {
		this.filePath = path;
		CSVReader reader = new CSVReader(filePath);
		
		this.pulsList = reader.getPulse();
		this.visibleForCoaches = true;
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
		if (gpxFilepath != null) {
			this.gpxData = gpxReader.getLatLong(gpxFilepath);
		}
		
	}
	
	public Workout(Athlete athl, URL filepath, boolean bool, InputStream gpxFilepath) throws IOException {
		this.filePath = filepath;
		CSVReader reader = new CSVReader(filePath);
		
		this.pulsList = reader.getPulse();
		this.visibleForCoaches = bool;
		
		this.dateString = reader.getDate();
		this.type = reader.getType();
		this.duration = reader.getDuration();
		this.kilometres = reader.getDistance();
		this.athlete = athl;
		this.maxHR = Integer.parseInt(Collections.max(pulsList));
		this.athleteMaxHR = reader.getAthleteMaxHR();
		int sum = 0;
		for (String puls: pulsList) {
			
			//if pulse is not recorded, its represented by "0", so need to a check for this:
			if (! puls.equals("0") ) {
				int m = Integer.parseInt(puls);
				sum += m;
			} 
			
		}
		this.averageHR = sum / pulsList.size();
		
		//parse string to date-object
		try {
			this.date = parseDate(this.dateString);
		}
		catch(Exception fnf) {
				fnf.printStackTrace();
		}
		if (gpxFilepath != null) {
			this.gpxData = gpxReader.getLatLong(gpxFilepath);
		}
	}

	
	public Workout(Athlete athl, String dateString, String type, int duration, double kilometres, List<String> pulsList, InputStream gpxFilepath) throws IOException {
		this.athlete = athl;
		this.dateString = dateString;
		this.type = type;
		this.duration = duration;
		this.kilometres = kilometres;
		this.pulsList = pulsList;
		this.visibleForCoaches = true;
		
		if (!pulsList.isEmpty()) {
			
			int sum = 0;
			this.maxHR = 0;
			for (String puls: pulsList) {
				int m = Integer.parseInt(puls);
				sum += m;
				if (m > this.maxHR ) {
					this.maxHR = m;
				}
			}
			this.averageHR = sum / pulsList.size();
			
			System.out.println(averageHR);
			System.out.println("________maxHR = ");
			System.out.println(maxHR);
		}
		
		//parse string to date-object
		try {
			this.date = parseDate(this.dateString);
		}
		catch(Exception fnf) {
				fnf.printStackTrace();
		}
		if (gpxFilepath != null) {
			this.gpxData = gpxReader.getLatLong(gpxFilepath);
		}
		
	}
	

	//constructor with visibility boolean
	public Workout(Athlete athl, String dateString, String type, int duration, double kilometres, List<String> pulsList, boolean bool, InputStream gpxFilepath) throws IOException {
		this.athlete = athl;
		this.dateString = dateString;
		this.type = type;
		this.duration = duration;
		this.kilometres = kilometres;
		this.pulsList = pulsList;
		this.visibleForCoaches = bool;
		
		if (!pulsList.isEmpty()) {
			
			int sum = 0;
			this.maxHR = 0;
			for (String puls: pulsList) {
				int m = Integer.parseInt(puls);
				sum += m;
				if (m > this.maxHR ) {
					this.maxHR = m;
				}
			}
			this.averageHR = sum / pulsList.size();
	
		}
		
		//parse string to date-object
		try {
			this.date = parseDate(this.dateString);
		}
		catch(Exception fnf) {
				fnf.printStackTrace();
		}
		if (gpxFilepath != null) {
			this.gpxData = gpxReader.getLatLong(gpxFilepath);
		}
		
	}
	

	//constructor with visibility boolean
	public Workout(Athlete athl, String dateString, String type, int duration, double kilometres, List<String> pulsList, boolean bool, List<List<Double>> gpxliste) throws IOException {
		this.athlete = athl;
		this.dateString = dateString;
		this.type = type;
		this.duration = duration;
		this.kilometres = kilometres;
		this.pulsList = pulsList;
		this.visibleForCoaches = bool;
		this.gpxData = gpxliste;
		
		if (!pulsList.isEmpty()) {
			
			int sum = 0;
			this.maxHR = 0;
			for (String puls: pulsList) {
				int m = Integer.parseInt(puls);
				sum += m;
				if (m > this.maxHR ) {
					this.maxHR = m;
				}
			}
			this.averageHR = sum / pulsList.size();
	
		}
		
		//parse string to date-object
		try {
			this.date = parseDate(this.dateString);
		}
		catch(Exception fnf) {
				fnf.printStackTrace();
		}
	
		
	}
	
	
	
	
	// Getters and setters 
	public Athlete getAthlete() { return athlete; }
	
	public void setAthlete(Athlete athlete) { this.athlete = athlete; }

	public Date getDate() { return date; }
	
	public String getDateString() { return dateString; }
	
	public String getType() { return type; }
	
	public double getKilometres() { return kilometres; }
	
	public int getDuration() { return duration; }
	
	public List<String> getPulsList() { return pulsList; }
	
	public Integer getMaxHR() { return maxHR; }
	
	public Integer getAverageHR() { return averageHR; }
	
	public int getAthleteMaxHR() { return this.athleteMaxHR; }
	
	public void setGpxData(List<List<Double>> list) {
		this.gpxData = list;
	}
	
	protected void setDate(Date date) { this.date = date; }


	protected void setDateString(String dateString) { this.dateString = dateString; }


	protected void setType(String type) { this.type = type; }


	protected void setKilometres(double kilometres) { this.kilometres = kilometres; }


	protected void setDuration(int duration) { this.duration = duration; }


	protected void setPulsList(List<String> pulsList) {this.pulsList = pulsList; }
	
	
	protected void setFilePath(URL filePath) { this.filePath = filePath; }


	protected void setMaxHR(int maxHR) { this.maxHR = maxHR; }
	
	public void setVisibility(boolean bool) {
		this.visibleForCoaches = bool;
	}
	
	public boolean getVisibility() {
		return this.visibleForCoaches;
	}
	
	public List<List<Double>> getGpxData() {
		return this.gpxData;
	}
	
	/*public List<String> getGpxDataDouble(){
		List<String> data = new ArrayList<>();
		for (LatLong ll : this.getGpxData()) {
			data.add(ll.getLatitude()+","+ll.getLongitude());
		}
		return data;
	}*/

	protected void setAverageHR(int averageHR) { this.averageHR = averageHR; }
	
	// parse the date
		public Date parseDate(String date) throws ParseException {
			
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			return df.parse(date);
			
		}

	@Override
	public int compareTo(Workout o) {
		return this.date.compareTo(o.date);
	}

	
	
}