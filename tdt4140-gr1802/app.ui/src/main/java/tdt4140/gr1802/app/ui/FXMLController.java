package tdt4140.gr1802.app.ui;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapShape;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Database;



public class FXMLController implements Initializable, MapComponentInitializedListener{

    //Variables to get the logic (?)
    
	private Database database;
	private Athlete athlete;
	
	//Variables to get the map

    @FXML
    private GoogleMapView mapView;
    
    private GoogleMap map;

	@Override
	public void mapInitialized() {
		
	this.athlete = App.getAthlete();
	this.database = App.getDb();	
    MapOptions mapOptions = new MapOptions();
    
    LatLong StartPoint = new LatLong(63.414236, 10.402698);
    LatLong l1 = new LatLong(63.414908, 10.402397);
    LatLong l2 = new LatLong(63.415119, 10.399565);
    LatLong l3 = new LatLong(63.418065, 10.397748);
    LatLong l4 = new LatLong(63.418965, 10.397518);
    LatLong l5 = new LatLong(63.420687, 10.398544);
    LatLong l6 = new LatLong(63.421146, 10.397732);
    LatLong l7 = new LatLong(63.421797, 10.397347);
    LatLong l8 = new LatLong(63.422161, 10.395636);
    LatLong FinishPoint = new LatLong(63.422429, 10.395422);
   
        mapOptions.center(StartPoint)
                .mapType(MapTypeIdEnum.SATELLITE)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(13);
                   
        map = mapView.createMap(mapOptions);
        
        //Codeblock for getting markers at start -and finishpositions.
        
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(StartPoint);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(FinishPoint);
        
        Marker StartPointMarker = new Marker(markerOptions1);
        Marker FinishPointMarker = new Marker(markerOptions2);
        
        map.addMarker(StartPointMarker);
        map.addMarker(FinishPointMarker);
        
        //Codeblock for getting lines between the gps-positions from the workout. 
        
        LatLong[] ary = new LatLong[]{StartPoint, l1, l2, l3, l4, l5, l6, l7, l8,FinishPoint};
        MVCArray mvc = new MVCArray(ary);
        PolylineOptions polyOpts = new PolylineOptions()
        .path(mvc)
        .strokeColor("blue")
        .strokeWeight(2);
        Polyline poly = new Polyline(polyOpts);
        map.addMapShape((MapShape)poly);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInitializedListener(this);
	}
}