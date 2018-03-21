package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;

import io.jenetics.jpx.GPX;
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
import tdt4140.gr1802.app.core.GPXReader;
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
    
    private GeocodingService geocodingService;
    GPXReader gpxreader = new GPXReader();

	@FXML
	private Button homeScreenButton;
	
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
	
	public void backToHomeScreen(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("HomeScreenCoach.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}

	public void mapInitialized() {
        geocodingService = new GeocodingService();
        MapOptions mapOptions = new MapOptions();
        List<List<Double>> liste = workout.getGpxData();
        List<LatLong> liste2 = new ArrayList<>();
        System.out.println("hei for test");
        /*String path = workout.getGpxFilepath();
        System.out.println("Stien: "+path);
        URL url = this.getClass().getResource(path);
        System.out.println("hei etter test");
        InputStream s = null;
        try {
			s = url.openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(url);
        System.out.println(s);
        try {
			liste = gpxreader.getLatLong(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
        //System.out.println(liste);
       */
        
        for (List<Double> l : liste) {
        		liste2.add(new LatLong(l.get(0), l.get(1)));
        }
        
        mapOptions.center(liste2.get(0))
        .mapType(MapTypeIdEnum.ROADMAP)
        .overviewMapControl(false)
        .panControl(false)
        .rotateControl(false)
        .scaleControl(false)
        .streetViewControl(false)
        .zoomControl(false)
        .zoom(12);

        map = mapView.createMap(mapOptions);
        
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(liste2.get(0)).label("S");
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(liste2.get(liste.size()-1)).label("F");
        
        Marker StartPointMarker = new Marker(markerOptions1);
        Marker FinishPointMarker = new Marker(markerOptions2);
        
        map.addMarker(StartPointMarker);
        map.addMarker(FinishPointMarker);
        
        //System.out.println("SIZE: "+ liste.size());
        LatLong[] ary = new LatLong[liste2.size()];
        int i = 0;
        for (LatLong values : liste2) {
        		ary[i] = values;
        		i++;
        }
        //System.out.println(ary);
       
        MVCArray mvc = new MVCArray(ary);
        PolylineOptions polyOpts = new PolylineOptions()
        .path(mvc)
        .strokeColor("pink")
        .strokeWeight(2);
        Polyline poly = new Polyline(polyOpts);
        map.addMapShape((MapShape)poly);
	}

}
