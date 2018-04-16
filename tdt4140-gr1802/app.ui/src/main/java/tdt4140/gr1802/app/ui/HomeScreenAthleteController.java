package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.AnalyzeWorkouts;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Database;


public class HomeScreenAthleteController {
	
	private Athlete athlete;
	private Database db; 
	
	
	// Declaring variables for elements in fxml
	@FXML
	private Button btAddWorkout;
	
	@FXML
	private Button btSeeWorkouts;
	
	@FXML
	private Button btSeeCoaches;
	
	@FXML
	private Button btAddSleepdata ;
	
	@FXML
	private Button btCoachRequests;
	
	@FXML
	private Label txtLabelUsername;
	
	@FXML
	private Label lowLabel;
	
	@FXML
	private Label moderateLabel;
	
	@FXML
	private Label highLabel;
	
	@FXML
	private PieChart zoneChart;
	
	@FXML
	private Label txtQuote; 
	
	@FXML 
	private Label txtHelloUser; 
	
	
	
	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	private AnalyzeWorkouts analyzer = new AnalyzeWorkouts();
	
	public void initialize() throws Exception {
		App.updateAthlete();
		this.db = App.getDb();
		this.athlete = App.getAthlete();
		this.txtLabelUsername.setText(this.athlete.getUsername());
		
		List<Integer> timeInZones = analyzer.getTimeInHRZones(athlete.getAllWorkouts());
		
		lowLabel.setText(String.valueOf(timeInZones.get(0)) + " min");
		moderateLabel.setText(String.valueOf(timeInZones.get(1)) + " min");
		highLabel.setText(String.valueOf(timeInZones.get(2)) + " min");
		
		// Fill PieChart with pulsZones
		System.out.println(timeInZones);
		pieChartData.add(new PieChart.Data("Low", timeInZones.get(0)));
		pieChartData.add(new PieChart.Data("Moderate", timeInZones.get(1)));
		pieChartData.add(new PieChart.Data("High", timeInZones.get(2)));
		System.out.println(pieChartData);
		zoneChart.setData(pieChartData);
		zoneChart.setTitle("Total time in pulse zones");
		
		//Random quotes
		
		txtHelloUser.setText("Welcome "+ athlete.getName()+"!");
		List<String> listQuotes = db.getQuotes();
		int number = randomNumber(listQuotes);
		this.txtQuote.setText(listQuotes.get(number));
		
		
	}
	
	public static int randomNumber(List<String> list) {
		Random rand = new Random();
		int value = rand.nextInt(list.size()); 
		return value; 
				
	}
	
	
	// Side-menu buttons
	public void clickAddWorkout (ActionEvent event) throws IOException, LoadException{
		// Open new window 
		Parent root = FXMLLoader.load(getClass().getResource("AddWorkout.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	public void clickSeeWorkouts (ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("SeeWorkouts.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	public void clickSeeCoaches (ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("SeeCoaches.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

		window.setScene(scene);
		window.show();
	}
	
	public void clickCoachRequest (ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("CoachRequests.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	public void clickAddSleepdata (ActionEvent event) throws IOException, LoadException{
		// Open new window 
		Parent root = FXMLLoader.load(getClass().getResource("AddSleepdata.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
}
