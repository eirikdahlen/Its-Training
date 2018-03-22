package tdt4140.gr1802.app.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;


public class GPXReaderTest {
	
	GPXReader reader = new GPXReader();
	
	@Test
	public void testGetLatLong() throws IOException {
		URL url = this.getClass().getResource("roing.gpx");
		InputStream stream = url.openStream();
		List<List<Double>> getList = reader.getLatLong(stream);
		List<List<Double>> correct = new ArrayList<>();
		List<Double> l1 = new ArrayList<>();
		l1.add(45.80878333);
		l1.add(9.273595);
		List<Double> l2 = new ArrayList<>();
		l2.add(45.80878333);
		l2.add(9.273595);
		correct.add(l1);
		correct.add(l2);
		assertEquals(getList, correct);
	}

}
