package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Database;
import tdt4140.gr1802.app.core.Workout;
import tdt4140.gr1802.app.ui.HomeScreenCoachController.DateAthlete;

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
	
	public class DateAthlete {
		private String name;
		private Date lastWorkout;
		
		public DateAthlete(String name, Date lastWorkout) {
			this.name = name; this.lastWorkout = lastWorkout;
		}
		
		public String getName() { return this.name; }
		public Date getLastWorkout() { return this.lastWorkout; }
	}
	
	// Declaring variables for elements in fxml
	@FXML
	private Button btSeeAthletes;
	
	@FXML
	private Button btAthleteRequests;
	
	@FXML
	private Label txtLabelUsername;
	
	// ------------- Home-tab -------------
	@FXML private TableView<Athlete> tableViewTop3;
	@FXML private TableColumn<Athlete, String> top3Name;
	@FXML private TableColumn<Athlete, Integer> top3Workouts;
	@FXML private Text homeTabWelcomeText;
	
	@FXML private ComboBox<String> homeComboBoxDate;
	@FXML private Button homeBtnOK;
	@FXML private TableView<DateAthlete> homeTableViewAthletes;
	@FXML private TableColumn<DateAthlete, String> homeColumnName;
	@FXML private TableColumn<DateAthlete, Date> homeColumnDate;
	
	@FXML private ComboBox<LocalDate> homeComboBoxNoteDate;
	@FXML private Button homeBtnNotesOK;
	@FXML private TextField homeTextFieldNote;
	@FXML private Button homeBtnSave;
	
	// -------------------------------------
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
		
		// ------------- HOME -------------
		System.out.println(coach.getName());
		System.out.println(homeTabWelcomeText);
		homeTabWelcomeText.setText("Welcome " + coach.getName() + "!");
		// Fill Top 3
		List<Athlete> top3 = coach.getTop3Athletes();
		ObservableList<Athlete> obsList = FXCollections.observableArrayList(top3);
		top3Name.setCellValueFactory(new PropertyValueFactory<Athlete, String>("name"));
		top3Workouts.setCellValueFactory(new PropertyValueFactory<Athlete, Integer>("numbWorkouts"));
		tableViewTop3.setItems(obsList);
		
		
		// Fill dates not working out
		List<String> dateChoices = new ArrayList<>();
		dateChoices.add("7 days"); dateChoices.add("14 days"); dateChoices.add("30 days");
		ObservableList<String> obsDateChoices = FXCollections.observableArrayList(dateChoices);
		homeComboBoxDate.setItems(obsDateChoices);
		
		// Fill dates for notes 
		List<LocalDate> dates = this.coach.getDatesWithNotes();
		if (! dates.contains(LocalDate.now())) {
			dates.add(LocalDate.now());
		}
		ObservableList<LocalDate> obsDates = FXCollections.observableArrayList(dates);
		// Add todays date as well
		homeComboBoxNoteDate.setItems(obsDates);
		
		
		
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
	
	// ------------- HOME/WELCOME ------------- 
	
	public void clickHomeOKButton(ActionEvent event) {
		String chosenTimePeriod = homeComboBoxDate.getValue();
		if (chosenTimePeriod == null) { return; }
		
		LocalDate date = LocalDate.now();
		
		if (chosenTimePeriod.equals("7 days")) {
			date = LocalDate.now().minusDays(7);
		} else if (chosenTimePeriod.equals("14 days")) {
			date = LocalDate.now().minusDays(14);
		} else {
			date = LocalDate.now().minusDays(30);	
		}
		
		Date sinceDate = java.sql.Date.valueOf(date);
		
		List<Athlete> toShowAthletes = this.coach.getAthletesNotWorkingOutSince(sinceDate);
		ObservableList<DateAthlete> obsToShowAthletes = FXCollections.observableArrayList();
		
		for (Athlete ath : toShowAthletes) {
			obsToShowAthletes.add(new DateAthlete(ath.getName(), ath.getDateLastWorkout()));
		}
		homeColumnName.setCellValueFactory(new PropertyValueFactory<DateAthlete, String>("name"));
		homeColumnDate.setCellValueFactory(new PropertyValueFactory<DateAthlete, Date>("lastWorkout"));
		
		homeTableViewAthletes.setItems(obsToShowAthletes);
		System.out.println(obsToShowAthletes);	
	}
	
	public void clickHomeOKNotesButton(ActionEvent event) {
		LocalDate chosenDate = homeComboBoxNoteDate.getValue();
		String note = this.coach.getNote(chosenDate);
		homeTextFieldNote.setText(note);
		if (chosenDate.isEqual(LocalDate.now())) {
			// The chosen date is today, must be able to edit the note 
			homeTextFieldNote.setEditable(true);
		} else {
			// The chosen is not today, should not be able to edit
			homeTextFieldNote.setEditable(false);
		}
		
	}
	
	public void clickHomeSaveNoteButton(ActionEvent event) {
		
		if (! homeComboBoxNoteDate.getValue().isEqual(LocalDate.now())) {
			// Chosen date is not today, should not save new
			return;
		}
		
		if (this.homeTextFieldNote.getText().equals("") || this.homeTextFieldNote.getText().equals(null)) {
			// No text to save
			return;
		}
		
		if (this.coach.getNote(LocalDate.now()).equals("")) {
			// not saved note for today
			this.coach.saveNote(LocalDate.now(), this.homeTextFieldNote.getText());
		} else {
			// note saved, should update the note
			this.coach.updateNote(LocalDate.now(), this.homeTextFieldNote.getText());
		}
		
	}
	
	
	
	
	// -----------------------------------

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
