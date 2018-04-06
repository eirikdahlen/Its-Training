package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
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
import javafx.util.Pair;
import tdt4140.gr1802.app.core.AnalyzeCoach;
import tdt4140.gr1802.app.core.AnalyzeWorkout;
import tdt4140.gr1802.app.core.AnalyzeWorkouts;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Database;
import tdt4140.gr1802.app.core.GPXReader;
import tdt4140.gr1802.app.core.Workout;

public class HomeScreenCoachController implements Initializable, MapComponentInitializedListener {
	
	private Coach coach;
	private Database db;
	private AnalyzeWorkout analyzer = new AnalyzeWorkout();
	private AnalyzeCoach coachAnalyzer = new AnalyzeCoach();
	
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
	
	// GmapsFX
    @FXML
    private GoogleMapView mapView;
    
    private GoogleMap map;
    
    private GeocodingService geocodingService;
    GPXReader gpxreader = new GPXReader();
	
	//_________________ATHLETE TAB_________________________
	
	private Athlete choosenAthlete;
	
	@FXML 
	private ChoiceBox<Athlete> cboxChooseAthlete;
	
	@FXML 
	private Button btShowAthlete;
	
	private ObservableList<BarChart.Data<String, Number>> barChartDataHR = FXCollections.observableArrayList();
	
	private ObservableList<BarChart.Data<String, Number>> barChartDataDuration = FXCollections.observableArrayList();
	
	private ObservableList<BarChart.Data<String, Number>> barChartDataAmount = FXCollections.observableArrayList();
	
	@FXML
	private PieChart athlActTypesChart;
	@FXML
	private PieChart mvActTypesChart;
	
    @FXML 
    private CategoryAxis xAxisHR, xAxisDuration, xAxisWorkoutType, xAxisAmount;
    
    @FXML 
    private NumberAxis yAxisHR, yAxisDuration, yAxisWorkoutType, yAxisAmount;
    
    @FXML 
    private BarChart<String, Number> chartHRZones, chartDuration, chartWorkoutType, chartAmount;
    
    List<Workout> workoutsForChoosenAthlete = new ArrayList<>();
    List<Athlete> allAthletes = new ArrayList<>();
  
    
	 
	//_________________________
	
	public void initialize(URL location, ResourceBundle resources) {
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
		FXCollections.sort(obsDates);
		
		// Add todays date as well
		homeComboBoxNoteDate.setItems(obsDates);
		
		
		
		// **** ACTIVITIES TAB ****
		List<String> allAct = db.getAllActivities();
		actChoiceList = FXCollections.observableArrayList(allAct);
		activitiesChoice.setItems(actChoiceList);
		
		
		//_______STATS ALLTIME TAB________
		System.out.println("PETTER TAB");
		rankingChoiceList = FXCollections.observableArrayList();
		rankingChoiceList.add("Last 30 days");
		rankingChoiceList.add("All-time");
		rankingChoice.setItems(rankingChoiceList);
		
		//________ATHLETES TAB______
		ObservableList<Athlete> obsAthleteTab = FXCollections.observableArrayList();

		for (String athleteName : coach.getAthletes()) {
			obsAthleteTab.add(db.getAthlete(athleteName));
		}
		cboxChooseAthlete.setItems(obsAthleteTab);
		
		allAthletes = new ArrayList<>();
		
		
		//GmapsFX
		mapView.addMapInializedListener(this);
		
		for (String athlete : coach.getAthletes()) {
			allAthletes.add(db.getAthlete(athlete));
		}

	
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
	
	//_____________ATHLETE TAB_______________
	
	public void searchAthlete(ActionEvent event) {
		choosenAthlete = db.getAthlete(cboxChooseAthlete.getValue().getUsername());
		workoutsForChoosenAthlete = db.getAllWorkouts(choosenAthlete);
		
		updateHRZonesChart(workoutsForChoosenAthlete, allAthletes);
		updateDurationChart(workoutsForChoosenAthlete, allAthletes);
		updateAmountChart(workoutsForChoosenAthlete, allAthletes);
		updateActivitesPieChart(choosenAthlete);
	}

	private void updateHRZonesChart(List<Workout> workoutsForAthlete, List<Athlete> allAthletes) {
		List<Integer> dataHRZonesAthlete = analyzer.getAnalyzedHRZonesMeanValueForAthlete(workoutsForAthlete);
		List<Integer> dataHRZonesAll = analyzer.getAnalyzedHRZonesMeanValueForAll(allAthletes);
		
		XYChart.Series<String, Number> HRZones1 = new XYChart.Series<>();
		XYChart.Series<String, Number> HRZones2 = new XYChart.Series<>();
		XYChart.Series<String, Number> HRZones3 = new XYChart.Series<>();
		
		chartHRZones.getData().clear();
        HRZones1.setName("Low");
        System.out.println(choosenAthlete.getName());
        HRZones1.getData().add(new XYChart.Data<String, Number>(choosenAthlete.getUsername(), dataHRZonesAthlete.get(0)));
        System.out.println("testing");
        HRZones1.getData().add(new XYChart.Data<String, Number>("Mean Value", dataHRZonesAll.get(0)));
        System.out.println("testing2");
        HRZones2.setName("Moderate");
        HRZones2.getData().add(new XYChart.Data<String, Number>(choosenAthlete.getUsername(), dataHRZonesAthlete.get(1)));
        System.out.println("testing3");
        HRZones2.getData().add(new XYChart.Data<String, Number>("Mean Value", dataHRZonesAll.get(1)));
        HRZones3.setName("High");
        System.out.println("testing4");
        HRZones3.getData().add(new XYChart.Data<String, Number>(choosenAthlete.getUsername(), dataHRZonesAthlete.get(2)));
        System.out.println("testing5");
        HRZones3.getData().add(new XYChart.Data<String, Number>("Mean Value", dataHRZonesAll.get(2)));
        System.out.println("testing6");
        chartHRZones.getData().addAll(HRZones1, HRZones2, HRZones3);
        System.out.println("testing7");
        System.out.println("charHRZones: "+chartHRZones.getData());
        
	}
	
	private void updateDurationChart(List<Workout> workoutsForAthlete, List<Athlete> allAthletes) {
		int dataDurationAthlete = analyzer.getAnalyzedDurationMeanValueForAthlete(workoutsForAthlete);
		int dataDurationAll = analyzer.getAnalyzedDurtionMeanValueForAll(allAthletes);
		
		XYChart.Series<String, Number> Duration1 = new XYChart.Series<>();
		
		chartDuration.getData().clear();
        Duration1.setName("Duration");
        Duration1.getData().add(new XYChart.Data<>(choosenAthlete.getUsername(), dataDurationAthlete));
        Duration1.getData().add(new XYChart.Data<>("Mean Value", dataDurationAll));
        chartDuration.getData().add(Duration1);
        System.out.println("Chart duration" + chartDuration.getData());
	}
	
	private void updateAmountChart(List<Workout> workoutsForAthlete, List<Athlete> allAthletes) {
		int dataAmountAthlete = analyzer.getAnalyzedAmountForAthlete(workoutsForAthlete);
		int dataAmountAll = analyzer.getAnalyzedAmountMeanValueForAll(allAthletes);
		
		XYChart.Series<String, Number> Amount1 = new XYChart.Series<>();
		
		chartAmount.getData().clear();
        Amount1.setName("Amount");
        Amount1.getData().add(new XYChart.Data<>(choosenAthlete.getUsername(), dataAmountAthlete));
        Amount1.getData().add(new XYChart.Data<>("Mean Value", dataAmountAll));
        chartAmount.getData().add(Amount1);
        System.out.println("Chart amount" + chartAmount.getData());
	}
	
	public void updateActivitesPieChart(Athlete athlete) {
		List<Integer> athlAct = db.getAthleteActivityTypes(athlete.getUsername());
		List<String> allAct = db.getAllActivities();
		List<Integer> meanValueAct = coachAnalyzer.getAvgNrActivites(coach);
		
		// Fill PieChart for Athlete with data
		ObservableList<PieChart.Data> pieChartDataAthl = FXCollections.observableArrayList();
		pieChartDataAthl.add(new PieChart.Data(allAct.get(0), athlAct.get(0)));
		pieChartDataAthl.add(new PieChart.Data(allAct.get(1), athlAct.get(1)));
		pieChartDataAthl.add(new PieChart.Data(allAct.get(2), athlAct.get(2)));
		pieChartDataAthl.add(new PieChart.Data(allAct.get(3), athlAct.get(3)));
		pieChartDataAthl.add(new PieChart.Data(allAct.get(4), athlAct.get(4)));

		athlActTypesChart.setData(pieChartDataAthl);
		athlActTypesChart.setTitle(athlete.getUsername());
		
		// Fill PieChart for Mean value with data
		ObservableList<PieChart.Data> pieChartDataMv = FXCollections.observableArrayList();
		pieChartDataMv.add(new PieChart.Data(allAct.get(0), meanValueAct.get(0)));
		pieChartDataMv.add(new PieChart.Data(allAct.get(1), meanValueAct.get(1)));
		pieChartDataMv.add(new PieChart.Data(allAct.get(2), meanValueAct.get(2)));
		pieChartDataMv.add(new PieChart.Data(allAct.get(3), meanValueAct.get(3)));
		pieChartDataMv.add(new PieChart.Data(allAct.get(4), meanValueAct.get(4)));

		mvActTypesChart.setData(pieChartDataMv);
		mvActTypesChart.setTitle("Mean Value");
		
	}

		
	// ---------------- MAP TAB -----------------------
		public void mapInitialized() {
			System.out.println("Halllllloeojgpjerg");
	        geocodingService = new GeocodingService();
	        MapOptions mapOptions = new MapOptions();
	        List<List<Double>> liste = coach.getWorkoutsStartpoints();
	        List<LatLong> liste2 = new ArrayList<>();
	        System.out.println("liste: " + liste);
	        
	        
	        for (List<Double> l : liste) {
	        		liste2.add(new LatLong(l.get(0), l.get(1)));
	        }
	        System.out.println(liste2.size());
	        
	        mapOptions.center(new LatLong(56.948341, 12.452795))
	        .mapType(MapTypeIdEnum.ROADMAP)
	        .overviewMapControl(false)
	        .panControl(false)
	        .rotateControl(false)
	        .scaleControl(false)
	        .streetViewControl(false)
	        .zoomControl(false)
	        .zoom(4);

	        map = mapView.createMap(mapOptions);
	        
	        for (LatLong latlong : liste2) {
	        		MarkerOptions mo = new MarkerOptions();
	        		mo.position(latlong);
	        		Marker marker = new Marker(mo);
	        		map.addMarker(marker);
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
