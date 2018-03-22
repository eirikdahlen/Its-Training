package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lynden.gmapsfx.javascript.object.LatLong;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Latitude;
import io.jenetics.jpx.Longitude;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;


public class GPXReader {
	
	public List<List<Double>> getLatLong(InputStream stream) throws IOException{
		List<List<Double>> list = new ArrayList<>();
		GPX gpx = GPX.read(stream);
		for (Track t : gpx.getTracks()) {
			for(TrackSegment ts : t.getSegments()) {
				for (WayPoint w : ts.getPoints()) {
					List<Double> v = new ArrayList<>();
					v.add(w.getLatitude().doubleValue());
					v.add(w.getLongitude().doubleValue());
					System.out.println(w.getLatitude().doubleValue());
					System.out.println(w.getLongitude().doubleValue());
					//list.add(w.getLatitude().doubleValue());
					//list.add(w.getLongitude().doubleValue());
					System.out.println("higoheohgiohoihbolkvsbnf");
					//list.add(new LatLong(w.getLatitude().doubleValue(), w.getLongitude().doubleValue()));
					list.add(v);
				}
			}
		}
		return list;
	}
	
	
	
}
