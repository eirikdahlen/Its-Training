package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Database;

public class SeeAthletesController {
	
	private Database database = App.getDb();
	private Coach coach;
	
	// Declaring variables for elements in fxml
	@FXML
	private Button btSeeAthletes;
	
	@FXML
	private Button btAthleteRequests;
	
	@FXML
	private TextField addAthleteTextField;
	
	@FXML
	private Button btAddAthlete;
	
	@FXML
	private Button btSeeWorkouts;
	
	@FXML
	private Button btSeeSleepdata ;
	
	@FXML
	private Button btRemove;
	
	@FXML
	private Label txtAddAthleteRespons;
	
	@FXML
	private TableView<Athlete> tableView;
	
	@FXML
	private TableColumn<Athlete, String> usernameColumn;
	
	@FXML
	private TableColumn<Athlete, String> nameColumn;
	
	@FXML
	private Label txtLabelUsername;
	
	@FXML
	private Button homeScreenButton;
	
	private CoachSeeSleepdataController coachSeeSleepdata = new CoachSeeSleepdataController();
	private CoachSeeWorkoutsController coachSeeWorkouts = new CoachSeeWorkoutsController();
	private ObservableList<Athlete> athletes = FXCollections.observableArrayList();
	
	// returns an ObservableList with all the Athletes registered to the coach logged in
	public ObservableList<Athlete> getAthletes(){
		// Loops through the athletes for a coach and adds to list of athletes in application
		for (String uname : coach.getAthletes()) {
			Athlete athlete = database.getAthlete(uname);
			if (athlete != null) {
				athletes.add(athlete);
			}
		}
		return athletes;
	}
	
	// Yo
	public void initialize() {
		App.updateCoach();
		this.coach = App.getCoach();
		
		// Set username label
		this.txtLabelUsername.setText(this.coach.getUsername());
		
		//TODO: this.database = App.db;
		System.out.println("Seeathletes init");
		
		// Connect columns to right attribute
		usernameColumn.setCellValueFactory(new PropertyValueFactory<Athlete,String>("username"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Athlete,String>("name"));
		
		// Fill table with values
		tableView.setItems(getAthletes());
		
	}
	
	public void seeWorkoutsButton(ActionEvent event) throws IOException, RuntimeException, InvocationTargetException {
		// ObservableList with the selectedRow
		ObservableList<Athlete> selectedRow;
		selectedRow = tableView.getSelectionModel().getSelectedItems();
		
		// The Athlete selected
		Athlete athlete = selectedRow.get(0);
		
		// Set Athlete in  the CoachSeeWorkoutsController
		coachSeeWorkouts.setAthlete(athlete);
		
		// Load new scene with the Athlete's workouts
		Parent root = FXMLLoader.load(getClass().getResource("CoachSeeWorkouts.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
		window.setScene(scene);
		window.show();
		
	}

	public void seeSleepdataButton(ActionEvent event) throws IOException, RuntimeException, InvocationTargetException {
		// ObservableList with the selectedRow
		ObservableList<Athlete> selectedRow;
		selectedRow = tableView.getSelectionModel().getSelectedItems();
		
		// The Athlete selected
		Athlete athlete = selectedRow.get(0);
		
		// Set Athlete in  the CoachSeeWorkoutsController
		coachSeeSleepdata.setAthlete(athlete);
		
		// Load new scene with the Athlete's workouts
		Parent root = FXMLLoader.load(getClass().getResource("CoachSeeSleepdata.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
		window.setScene(scene);
		window.show();
		
	}
	
	public void removeButton(ActionEvent event) throws IOException{
		// ObservableList with the selectedRow
		ObservableList<Athlete> selectedRow;
		selectedRow = tableView.getSelectionModel().getSelectedItems();
		
		// The Athlete selected
		Athlete athlete = selectedRow.get(0);
		
		// Remove Athlete from screen
		athletes.remove(athlete);
		
		// Remove Athlete from database
		if (athlete != null) {
			coach.removeAthlete(athlete.getUsername());
		}
	}
	
	public void addAthleteButton(ActionEvent event) throws RuntimeException, InvocationTargetException{
		// Get the String in the TextField
		String newAthlete = addAthleteTextField.getText();
		
		// Check which case that apply
		if(database.athleteUsernameExists(newAthlete) && !coach.getAthletes().contains(newAthlete)) {
			txtAddAthleteRespons.setText("Request sent to "+newAthlete);
			coach.sendAthleteRequest(newAthlete);
		} else if (database.athleteUsernameExists(newAthlete) && coach.getAthletes().contains(newAthlete)){
			txtAddAthleteRespons.setText("This is already your athlete.");
		} else if (!database.athleteUsernameExists(newAthlete)) {
			txtAddAthleteRespons.setText("Username does not exist");
		}
		
		// Clear the TextField
		addAthleteTextField.clear();
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
}
