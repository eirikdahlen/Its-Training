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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import tdt4140.gr1802.app.core.AnalyzeWorkouts;
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
	
	public class RankAthlete{
		private String userName;
		private int numbWorkouts;
		private int totalDuration;
		private double lowHR;
		private double medHR;
		private double highHR;
		
		
		public RankAthlete(Athlete athlete, boolean tirthy){
			AnalyzeWorkouts analyzer = new AnalyzeWorkouts();
			this.userName = athlete.getUsername();
			
			
			List<Workout> workoutList = db.getAllWorkouts(athlete);
			for (int i = 0; i < workoutList.size();i++) {
				if (! workoutList.get(i).getVisibility()) {
					workoutList.remove(i);
				}
			}
			
			if (tirthy) {
				//local date
				LocalDate dateRank = LocalDate.now().minusDays(30);
				Date sinceDateRank = java.sql.Date.valueOf(dateRank);
				//slett fra workout-liste
				
				
				for (int i = 0; i < workoutList.size();i++) {
					if (workoutList.get(i).getDate().before(sinceDateRank)) {
						workoutList.remove(i);
						
					}
				}
				
			}
			
			this.numbWorkouts = workoutList.size();
			this.totalDuration = analyzer.getTotalDuration(workoutList);
			this.lowHR = analyzer.getTimeInHRZones(workoutList).get(0);
			this.medHR = analyzer.getTimeInHRZones(workoutList).get(1);
			this.highHR = analyzer.getTimeInHRZones(workoutList).get(2);
			
		}
		
		
		public String getUserName() {
			return userName;
		}
		public int getNumbWorkouts() {
			return numbWorkouts;
		}
		public int getTotalDuration() {
			return totalDuration;
		}
		public double getLowHR() {
			return lowHR;
		}
		public double getMedHR() {
			return medHR;
		}
		public double getHighHR() {
			return highHR;
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
	
	
	
	//_________STATS ALL-TIME TAB________
	@FXML
	private Button btShowRanking;
	
	@FXML
	private ChoiceBox<String> rankingChoice;
	
	ObservableList<String> rankingChoiceList;
	
	@FXML
	private TableView<RankAthlete> rankAthletesTableView;
	
	@FXML
	private TableColumn<RankAthlete, String> rankAthletesColumn;
	
	@FXML
	private TableColumn<RankAthlete, Integer> rankNumberofSessionsColumn;
	
	@FXML
	private TableColumn<RankAthlete, Double> rankTotalDurationColumn;
	
	@FXML
	private TableColumn<RankAthlete, Double> rankLowHRColumn;
	
	@FXML
	private TableColumn<RankAthlete, Double> rankModerateHRColumn;
	
	@FXML
	private TableColumn<RankAthlete, Double> rankHighHRColumn;
	
	
	
	//_________________________
	
	
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
		
		List<String> dateChoices = new ArrayList<>();
		dateChoices.add("7 days"); dateChoices.add("14 days"); dateChoices.add("30 days");
		ObservableList<String> obsDateChoices = FXCollections.observableArrayList(dateChoices);
		homeComboBoxDate.setItems(obsDateChoices);
		
		
		// **** ACTIVITIES TAB ****
		actChoiceList = FXCollections.observableArrayList(db.getAllActivities());
		activitiesChoice.setItems(actChoiceList);
		
		
		//_______STATS ALLTIME TAB________
		System.out.println("PETTER TAB");
		rankingChoiceList = FXCollections.observableArrayList();
		rankingChoiceList.add("Last 30 days");
		rankingChoiceList.add("All-time");
		rankingChoice.setItems(rankingChoiceList);
		
		
	
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
	
	
	//_______ALL-TIME TAB_____
	
		public void clickShowRanking(ActionEvent event) {
			// Get selected activity
			String rankingChoiceSelected = rankingChoice.getSelectionModel().getSelectedItem();
			ObservableList<RankAthlete> obsRank = FXCollections.observableArrayList();
			
			
			
			// Set up Athletes-table
			List<String> rankingAthletesUsername = App.getCoach().getAthletes();
			List<Athlete> rankingAthletes = new ArrayList<Athlete>();
					
			for (String athleteUsername : rankingAthletesUsername) {		
				rankingAthletes.add(db.getAthlete(athleteUsername));
			}
			
			  
			//All-Time Selected
			if(rankingChoiceSelected == "All-time") {
				
				//Create tuples with Athlete and total duration (Athlete, Total duration)
				for(Athlete athlete : rankingAthletes) {
						RankAthlete rank = new RankAthlete(athlete, false);
						obsRank.add(rank);
				}
				

			} else {
				
				for(Athlete athlete : rankingAthletes) {
					RankAthlete rank = new RankAthlete(athlete, true);
					obsRank.add(rank);
			}
				
				
			}
			
			//ObservableList<Workout> obsActivityWorkouts = FXCollections.observableArrayList(activityWorkouts);
			rankAthletesColumn.setCellValueFactory(new PropertyValueFactory<RankAthlete, String>("userName"));
			rankNumberofSessionsColumn.setCellValueFactory(new PropertyValueFactory<RankAthlete, Integer>("numbWorkouts"));
			rankTotalDurationColumn.setCellValueFactory(new PropertyValueFactory<RankAthlete, Double>("totalDuration"));
			rankLowHRColumn.setCellValueFactory(new PropertyValueFactory<RankAthlete, Double>("lowHR"));
			rankModerateHRColumn.setCellValueFactory(new PropertyValueFactory<RankAthlete, Double>("medHR"));
			rankHighHRColumn.setCellValueFactory(new PropertyValueFactory<RankAthlete, Double>("highHR"));
			rankAthletesTableView.setItems(obsRank);

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
