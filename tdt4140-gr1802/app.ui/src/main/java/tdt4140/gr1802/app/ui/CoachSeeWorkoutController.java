package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.AnalyzeWorkout;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Workout;

public class CoachSeeWorkoutController implements Initializable, MapComponentInitializedListener{
	
	@FXML
	private Button btSeeAthletes;
	
	@FXML
	private Button btAthleteRequests;
	
	@FXML
	private Label txtLabelUsername;
	
	@FXML
	private Label dateLabel;
	
	@FXML
	private Label typeLabel;
	
	@FXML
	private Label durationLabel;
	
	@FXML
	private Label maxHRLabel;
	
	@FXML
	private Label avgHRLabel;
	
	@FXML
	private Label athleteLabel;

	@FXML
	private PieChart pulszonesChart;
	
	@FXML
	private LineChart<Number, Number> pulsLine;
	
	@FXML
	private CategoryAxis xAxis;
	
	@FXML
	private NumberAxis yAxis;
	
	// GmapsFX
    @FXML
    private GoogleMapView mapView;
    
    private GoogleMap map;
	
	private Coach coach;
	private static Workout workout;
	private AnalyzeWorkout analyzer = new AnalyzeWorkout();
	
	private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	
	public void setWorkout(Workout wo) {
		this.workout = wo;
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		// Set the Coach that is logged in
		App.updateCoach();
		this.coach = App.getCoach();
		
		//Set username label
		this.txtLabelUsername.setText(this.coach.getUsername());
		this.athleteLabel.setText("Athlete: "+this.workout.getAthlete().getUsername());
		
		// Set information text-fields
		dateLabel.setText(workout.getDateString());
		typeLabel.setText(workout.getType());
		durationLabel.setText(String.valueOf(workout.getDuration()));
		maxHRLabel.setText(String.valueOf(workout.getMaxHR()));
		avgHRLabel.setText(String.valueOf(workout.getAverageHR()));
		
		// Fill PieChart with pulsZones
		List<Integer> timeInZones = analyzer.getTimeInHRZones(workout);
		System.out.println(timeInZones);
		pieChartData.add(new PieChart.Data("Low", timeInZones.get(0)));
		pieChartData.add(new PieChart.Data("Moderate", timeInZones.get(1)));
		pieChartData.add(new PieChart.Data("High", timeInZones.get(2)));
		System.out.println(pieChartData);
		pulszonesChart.setData(pieChartData);
		pulszonesChart.setTitle("Puls Zones");
				
		// Fill LineChart with pulsData
		System.out.println("test");
		xAxis.setLabel("Time");
		yAxis.setLabel("Puls");
		pulsLine.setTitle("Puls");
		System.out.println("test1");
		XYChart.Series series = new XYChart.Series();
		System.out.println("test2");
		series.setName("My pulse");
		System.out.println("test3");
		List<String> pulsList = workout.getPulsList();
		System.out.println("test4");
		for (int i=1;i<pulsList.size();i++) {
			series.getData().add(new XYChart.Data(String.valueOf(i), Integer.parseInt(pulsList.get(i-1))));
		}
		System.out.println("test5");
		pulsLine.getData().add(series);
		
		//GmapsFX
		mapView.addMapInializedListener(this);
	}
	
	// Side-menu buttons
	public void clickSeeAthletes (ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("SeeAthletes.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
		window.setScene(scene);
		window.show();
	}
		
	public void clickAthleteRequests (ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("AthleteRequests.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
		window.setScene(scene);
		window.show();
	}

	public void mapInitialized() {
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
	                .zoom(15);
	                   
	        map = mapView.createMap(mapOptions);
	        
	        //Codeblock for getting markers at start -and finishpositions.
	        
	        MarkerOptions markerOptions1 = new MarkerOptions();
	        markerOptions1.position(StartPoint).label("S");
	        //markerOptions1.position(StartPoint).visible(true).title("Point1").icon("src/main/resources/tdt4140/gr1802/app/ui/Images/BluePointer.png");
	        MarkerOptions markerOptions2 = new MarkerOptions();
	        markerOptions2.position(FinishPoint).label("F");
	       //markerOptions2.position(FinishPoint).visible(true).icon(MarkerImageFactory.createMarkerImage("/src/main/resources/tdt4140/gr1802/app/ui/Images/BluePointer.png", "png"));
	        
	        Marker StartPointMarker = new Marker(markerOptions1);
	        Marker FinishPointMarker = new Marker(markerOptions2);
	        
	        map.addMarker(StartPointMarker);
	        map.addMarker(FinishPointMarker);
	        
	        //Codeblock for getting polylines between the gps-positions from the workout. 
	        
	        LatLong[] ary = new LatLong[]{StartPoint, l1, l2, l3, l4, l5, l6, l7, l8,FinishPoint};
	        MVCArray mvc = new MVCArray(ary);
	        PolylineOptions polyOpts = new PolylineOptions()
	        .path(mvc)
	        .strokeColor("blue")
	        .strokeWeight(2);
	        Polyline poly = new Polyline(polyOpts);
	        map.addMapShape((MapShape)poly);
	    }

}
