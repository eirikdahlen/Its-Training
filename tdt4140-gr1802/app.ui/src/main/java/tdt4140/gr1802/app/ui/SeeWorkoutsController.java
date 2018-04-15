package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Database;
import tdt4140.gr1802.app.core.Workout;

public class SeeWorkoutsController{
	
	private Database database;
	private Athlete athlete;
	
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
	private Button btAddWorkout;
	
	@FXML
	private Button btSeeWorkouts;
	
	@FXML
	private Button hideButton;
	
	@FXML
	private Button homeScreenButton;
	
	@FXML
	private Button btSeeCoaches;
	
	@FXML
	private Button btCoachRequests;
	
	@FXML
	private Label txtLabelUsername;
	
	private AthleteWorkoutController athleteWorkoutController = new AthleteWorkoutController();
	private Workout workout;

	// Returns an ObservableList with the Workouts registered for the Athlete logged in
	public ObservableList<Workout> getWorkouts() throws Exception{
		ObservableList<Workout> workouts = FXCollections.observableArrayList();
		System.out.println("getWorkouts" + athlete.getAllWorkouts());
		for (Workout wo:athlete.getAllWorkouts()) {
			workouts.add(wo);
		}
		return workouts;
	}
	
	public void initialize() throws Exception {
		//App.updateAthlete();
		this.athlete = App.getAthlete();
		this.database = App.getDb();
		
		// Set username label
		this.txtLabelUsername.setText(this.athlete.getUsername());
		
		// Connect columns to right attribute
		dateColumn.setCellValueFactory(new PropertyValueFactory<Workout,String>("dateString"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<Workout,Integer>("duration"));
		kilometresColumn.setCellValueFactory(new PropertyValueFactory<Workout,Double>("kilometres"));
		maxHRColumn.setCellValueFactory(new PropertyValueFactory<Workout,Integer>("maxHR"));
		averageHRColumn.setCellValueFactory(new PropertyValueFactory<Workout,Integer>("averageHR"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Workout,String>("type"));
		
		// Fill table with values
		tableView.setItems(getWorkouts());

		// Make it legal to select multiple rows
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	
	public void clickSeeWorkout(ActionEvent event) throws IOException{
		// ObservableList with the selectedRow
		ObservableList<Workout> selectedRow;
		selectedRow = tableView.getSelectionModel().getSelectedItems();
				
		// The Athlete selected
		Workout workout = selectedRow.get(0);
		athleteWorkoutController.setWorkout(workout);
		
		Parent root = FXMLLoader.load(getClass().getResource("AthleteWorkout.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	@FXML
    public void homeScreenButtonCursorHand() {
    	homeScreenButton.setCursor(Cursor.HAND);
    }
    @FXML
    public void homeScreenButtonCursorDefault() {
    	homeScreenButton.setCursor(Cursor.DEFAULT);
    }
    
	// Side-menu buttons, changes scenes
	public void clickAddWorkout(ActionEvent event) throws IOException{
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
	
	public void clickSeeCoaches(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("SeeCoaches.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	public void clickCoachRequest(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("CoachRequests.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	public void hideWorkout(ActionEvent event) throws Exception {
		ObservableList<Workout> selectedRows;
		selectedRows = tableView.getSelectionModel().getSelectedItems();
		
		// Iterate through all the selected rows, and deleting them
		for(Workout workout : selectedRows) {
			System.out.println("inni loop");
			database.setWorkoutVisibility(false, workout, this.athlete);
		}
	}
	
	public void backToHomeScreen(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("HomeScreenAthlete.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
}
