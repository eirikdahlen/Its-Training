package tdt4140.gr1802.app.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
	
	private URL filePath;
	
	//TODO: change to 5, now it only saves every 10 seconds
	//all pulse-data in database uses 10
	public static final int timeStep = 10;
	
	public CSVReader(URL filePath) {
		this.filePath = filePath;
	}
	
	// Finds the length of the workout
	public int converToMinutes(String dur) {
		String[] duration = dur.split(":");
		int hoursToMin = Integer.parseInt(duration[0]);
		int minToMin = Integer.parseInt(duration[1]);
		int secToMin = Integer.parseInt(duration[2]);
		
		return hoursToMin*60 + minToMin + secToMin/60;
	}
	
	// Processing the input CSV-file using Readers
	public String readFile(int i) throws IOException {
		String csvSplitBy = ",";
		BufferedReader br = null;
		String type = "";

		try {
			br = new BufferedReader(new InputStreamReader(filePath.openStream()));
			String linje = br.readLine();
			linje = br.readLine();
			String[] liste = linje.split(csvSplitBy);
			type = liste[i]; // Saving type of activity in type	
		}
		
		catch(FileNotFoundException fnf) {
				fnf.printStackTrace();
				throw fnf;
		}
		
		catch(IOException io) {
			io.printStackTrace();
		}
		
		finally {
				if (br != null) {
					try {
						br.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		return type;
	}
	
	// Finds the duration
	public int getDuration() throws IOException {
		String dist = readFile(4);
		return converToMinutes(dist);
	}
	
	// Finds the type
	public String getType() throws IOException {
		return readFile(1);
	}
	
	// Finds the Date
	public String getDate() throws IOException {
		return readFile(2) + " " + readFile(3);
	}

	// Finds the distance
	public double getDistance() throws IOException {
		return Double.parseDouble(readFile(5));

	}
	
	public int getAthleteMaxHR() throws IOException {
		System.out.println("TESTTEST");
		System.out.println(readFile(22));
		return Integer.parseInt(readFile(22));
	}
	
	// Finds the pulsedata
	public List<String> getPulse() {

		URL csvFile = this.filePath;
        
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        try {
        		List<String> pulse = new ArrayList<String>(); 
        		int i = 0;

            br = new BufferedReader(new InputStreamReader(filePath.openStream()));
            while ((line = br.readLine()) != null) {
		
                // use comma as separator
                String[] linje = line.split(cvsSplitBy);
               
                
                if (i > 3 && i%(this.timeStep) == 0) {
                	
                	//need to represent non-recorded pulse-step by 0:
                	if (! linje[2].equals("") ) {
                		pulse.add(linje[2]);
                	} else { 
                		pulse.add("0"); 
                		}
                }
                i++;
            }
            //returns array
            return pulse;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    return null;
	}

}
