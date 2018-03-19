package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Database;
import tdt4140.gr1802.app.core.Workout;

public class HomeScreenCoachController {
	
	private Coach coach;
	private Database db;
	
	public class ActivityAthlete {
		private String username;
		private int nrOfWorkouts;
		
		public ActivityAthlete(String username, int nrOfWorkouts) {
			this.username = username;
			this.nrOfWorkouts = nrOfWorkouts;
		}
		
		public String getUsername() {
			return username;
		}
		
		public int getNrOfWorkouts() {
			return nrOfWorkouts;
		}
	}
	
	// Declearing variables for elements in fxml
	@FXML
	private Button btSeeAthletes;
	
	@FXML
	private Button btAthleteRequests;
	
	@FXML
	private Label txtLabelUsername;
	
	@FXML
	private TableView<Athlete> tableViewTop5;
	
	@FXML
	private TableColumn<Athlete, String> top5Name;
	
	@FXML
	private TableColumn<Athlete, Integer> top5Workouts;
	
	@FXML
	private ChoiceBox<String> activitiesChoice;
	ObservableList<String> actChoiceList;
	
	@FXML
	private Button btShowActivities;
	
	@FXML
	private TableView<Workout> actWorkoutsTableView;
	
	@FXML
	private TableColumn<Workout, String> actWorkoutDateColumn;
	
	@FXML
	private TableColumn<Workout, Integer> actWorkoutDurationColumn;
	
	@FXML
	private TableColumn<Workout, Integer> actWorkoutKmColumn;
	
	@FXML
	private TableColumn<Workout, Integer> actWorkoutAvgHRColumn;
	
	@FXML
	private TableColumn<Workout, Athlete> actWorkoutAthleteColumn;
	
	@FXML
	private TableView<ActivityAthlete> actAthleteTableView;
	
	@FXML
	private TableColumn<ActivityAthlete, String> actAthleteAthColumn;
	
	@FXML
	private TableColumn<ActivityAthlete, Integer> actAthleteNrColumn;
	
	
	public void initialize() {
		App.updateCoach();
		this.coach = App.getCoach();
		this.db = App.getDb();
		this.txtLabelUsername.setText(this.coach.getUsername());
		
		
		// **** ACTIVITIES TAB ****
		actChoiceList = FXCollections.observableArrayList(db.getAllActivities());
		activitiesChoice.setItems(actChoiceList);
	
	}
	
	// ***** ACTIVITIES TAB ****
	public void clickShowActivities(ActionEvent event) {
		// Get selected activity
		String activity = activitiesChoice.getSelectionModel().getSelectedItem();
		
		// Set up Athletes-table
		List<Athlete> activityAthletes = db.getAthletesForActivity(activity);
		ObservableList<ActivityAthlete> obsActivityAthletes = FXCollections.observableArrayList();
		
		for (Athlete ath : activityAthletes) {
			ActivityAthlete actAth = new ActivityAthlete(ath.getUsername(), ath.getNrOfWorkouts(activity));
			obsActivityAthletes.add(actAth);
		}
		
		actAthleteAthColumn.setCellValueFactory(new PropertyValueFactory<ActivityAthlete, String>("username"));
		actAthleteNrColumn.setCellValueFactory(new PropertyValueFactory<ActivityAthlete, Integer>("nrOfWorkouts"));
	
		actAthleteTableView.setItems(obsActivityAthletes);
		
		// Set up Workouts-table
		List<Workout> activityWorkouts = db.getWorkoutsForActivity(activity);
		ObservableList<Workout> obsActivityWorkouts = FXCollections.observableArrayList(activityWorkouts);
		
		actWorkoutDateColumn.setCellValueFactory(new PropertyValueFactory<Workout, String>("dateString"));
		actWorkoutDurationColumn.setCellValueFactory(new PropertyValueFactory<Workout, Integer>("duration"));
		actWorkoutKmColumn.setCellValueFactory(new PropertyValueFactory<Workout, Integer>("kilometres"));
		actWorkoutAvgHRColumn.setCellValueFactory(new PropertyValueFactory<Workout, Integer>("averageHR"));
		actWorkoutAthleteColumn.setCellValueFactory(new PropertyValueFactory<Workout, Athlete>("athlete"));
		
		actWorkoutsTableView.setItems(obsActivityWorkouts);
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
