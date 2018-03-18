package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class CoachSeeWorkoutController {
	
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
	
	private Coach coach;
	private static Workout workout;
	private AnalyzeWorkout analyzer = new AnalyzeWorkout();
	
	private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	
	public void setWorkout(Workout wo) {
		this.workout = wo;
	}
	
	public void initialize() {
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

}
