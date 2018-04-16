package tdt4140.gr1802.app.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class test {
	
	
	public static void main(String[] args) throws MalformedURLException {
		URL filePath = new File("/Users/frikkhaldandersen/Downloads/sleepdata_ida.csv").toURI().toURL();
		CSVsleep test = new CSVsleep(filePath) ;
		List<List<String>> testdata = test.getSleepData();
		for (List <String> s : testdata) {
			System.out.println(""+s.get(0)+", "+s.get(1)+ ", " +s.get(2));
		}
		
	}
	


}
