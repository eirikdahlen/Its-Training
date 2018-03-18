package tdt4140.gr1802.app.ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Workout;

public class CoachSeeWorkoutsController {
	
	// Declaring variables for elements in fxml
	
	@FXML
	private TableView<Workout> tableView;
	
	@FXML
	private TableColumn<Workout, String> dateColumn;
	
	@FXML
	private TableColumn<Workout, Integer> durationColumn;
	
	@FXML
	private TableColumn<Workout, Double> kilometresColumn;
	
	@FXML
	private TableColumn<Workout, Integer> maxHRColumn;
	
	@FXML
	private TableColumn<Workout, Integer> averageHRColumn;
	
	@FXML
	private TableColumn<Workout, String> typeColumn;
	
	@FXML
	private Button btSeeAthletes;
	
	@FXML
	private Button btAthleteRequests;
	
	@FXML
	private Button btSeeWorkout;
	
	@FXML
	private Label txtAthlete;
	
	@FXML
	private Label txtLabelUsername;
	
	private static Athlete athlete;
	private Coach coach;
	private CoachSeeWorkoutController coachSeeWorkoutController = new CoachSeeWorkoutController();
	
	// Set the Athlete
	public void setAthlete(Athlete ath) {
		athlete = ath;
	}

	// Help-method that returns an ObservableList with all the Workouts for the chosen Athlete
	public ObservableList<Workout> getWorkouts(){
		ObservableList<Workout> workouts = FXCollections.observableArrayList();
		for (Workout wo : athlete.getAllWorkouts()) {
			//Check if visible for coaches before adding
			if(wo.getVisibility()) {workouts.add(wo);}
		}
		return workouts;
	}
	
	public void initialize() {
		// set up the columns in the table
		txtAthlete.setText("Athlete: " + athlete.getName());
		
		// Set the Coach that is logged in
		App.updateCoach();
		this.coach = App.getCoach();
		
		//Set username label
		this.txtLabelUsername.setText(this.coach.getUsername());
		
		// Connect columns to right attribute
		dateColumn.setCellValueFactory(new PropertyValueFactory<Workout,String>("dateString"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<Workout,Integer>("duration"));
		kilometresColumn.setCellValueFactory(new PropertyValueFactory<Workout,Double>("kilometres"));
		maxHRColumn.setCellValueFactory(new PropertyValueFactory<Workout,Integer>("maxHR"));
		averageHRColumn.setCellValueFactory(new PropertyValueFactory<Workout,Integer>("averageHR"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Workout,String>("type"));
		
		// Fill table with values
		tableView.setItems(getWorkouts());
	}
	
	public void clickSeeWorkout(ActionEvent event) throws IOException{
		// ObservableList with the selectedRow
		ObservableList<Workout> selectedRow;
		selectedRow = tableView.getSelectionModel().getSelectedItems();
						
		// The Athlete selected
		Workout workout = selectedRow.get(0);
		coachSeeWorkoutController.setWorkout(workout);
				
		Parent root = FXMLLoader.load(getClass().getResource("CoachSeeWorkout.fxml"));
		Scene scene = new Scene(root, 800, 600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
				
		window.setScene(scene);
		window.show();
	}
	
	// Side-menu buttons
	public void clickSeeAthletes (ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("SeeAthletes.fxml"));
		Scene scene = new Scene(root,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	public void clickAthleteRequests (ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("AthleteRequests.fxml"));
		Scene scene = new Scene(root,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}

}
