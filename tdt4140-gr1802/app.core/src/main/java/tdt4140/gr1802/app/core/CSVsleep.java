package tdt4140.gr1802.app.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVsleep {
	
	private URL filePath;
	
	public static final int timeStep = 10;

	
	public CSVsleep (URL filePath) {
		this.filePath = filePath ;
	}
	
	public List<List<String>> getSleepData() {

		URL csvFile = this.filePath;
        
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        
        try {
        	List<List<String>> sleepdata = new ArrayList<List<String>>(); 
        	int i = 0;
        	System.out.println("SHA");
            br = new BufferedReader(new InputStreamReader(filePath.openStream()));
            line = br.readLine();
            System.out.println(line);
            while (line != null) {
            		if (line.isEmpty() || i == 0 || i == 1) {
            			System.out.println("okeliii AS");
            		} else {
            			System.out.println("256-SHA");
                // use comma as separator
            			String[] linje = line.split(cvsSplitBy);
            			String date = linje[1].substring(0,10);
            			System.out.println(linje[2].substring(0, linje[2].length()-1));
            			String quality = linje[2].substring(0, linje[2].length()-1);
            			String duration = linje[3].substring(0,1);
            			System.out.println(duration);
            			List <String> day = Arrays.asList(date, quality, duration);
            			sleepdata.add(day);
            		}
                i++;
            }
            //returns array
            return sleepdata;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
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
