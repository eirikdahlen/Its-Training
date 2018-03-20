package tdt4140.gr1802.app.core;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Route;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;
import com.lynden.gmapsfx.javascript.object.LatLong;


public class GPXReader {
	
	
	public static void readGPX (GPX gpx, String path) throws IOException {
		GPX.write(gpx, path);
		Stream<WayPoint> kandetgaa = GPX.read(path).tracks().flatMap(Track::segments).flatMap(TrackSegment::points);
		List<WayPoint> result = kandetgaa.collect(Collectors.toList());
		System.out.println("Gikk det?");
		 
	}
	
	public static void main(String[] args) throws IOException {
		String path = "src/main/java/tdt4140/gr1802/app/core/roing8.gpx";
		final GPX gpx = GPX.read(path);
		readGPX(gpx, path);
	}
}
