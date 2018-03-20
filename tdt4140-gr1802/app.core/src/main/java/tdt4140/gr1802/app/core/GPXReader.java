package tdt4140.gr1802.app.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.io.IOException;
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
	
	
	public static LatLong[] readGPX (GPX gpx) throws IOException {
		String path = "src/main/java/tdt4140/gr1802/app/core/roing8.gpx";
//		URL filepath = getClass().getResource(path);
		GPX.write(gpx, path);
		Stream<WayPoint> kandetgaa = GPX.read(path).tracks().flatMap(Track::segments).flatMap(TrackSegment::points);
		List<WayPoint> temp = kandetgaa.collect(Collectors.toList());
		LatLong[] result = convert(temp);
		return result;
		 
	}

	public static LatLong[] convert (List<WayPoint> waypointers) {
		LatLong[] result = {};
		for(WayPoint wp : waypointers) {
			Latitude latitjud = (wp.getLatitude());
			Longitude longitjud = (wp.getLongitude());
			double lat = Double.parseDouble(latitjud.toString());
			double lon = Double.parseDouble(longitjud.toString());
			Arrays.asList(result).add(new LatLong(lat,lon));
			System.out.println("OKELI AS");
		}
		return result;
	}
	
	
}
