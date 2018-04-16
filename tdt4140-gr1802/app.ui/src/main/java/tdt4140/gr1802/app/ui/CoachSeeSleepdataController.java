package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Database;

public class CoachSeeSleepdataController implements Initializable {
	
	private Coach coach;
	private Database db;
	private static Athlete choosenAthlete;
	
	
	public class ActivityAthlete {
		private String username;
		
		public ActivityAthlete(String username) {
			this.username = username;
		}
		
		public String getUsername() {
			return username;
		}

	}
	
	
	@FXML
	private Button btSeeAthletes;
	
	@FXML
	private Button btAthleteRequests;
	
	@FXML
	private Label txtLabelUsername;
	
	@FXML
	private Label txtAthleteLabel ;
	
	@FXML 
	private ChoiceBox<Athlete> cboxChooseAthlete;
	
	@FXML 
	private Button btShowAthlete;
	
	private ObservableList<BarChart.Data<String, Number>> barChartSleepdata= FXCollections.observableArrayList();
	
	@FXML 
    private CategoryAxis xAxisDuration, xAxisQuality ;
	
    @FXML 
    private NumberAxis yAxisLength, yAxisQuality ;
    
    @FXML 
    private LineChart<String, Number> chartDuration, chartQuality ;    
    //List<Athlete> allAthletes = new ArrayList<>();
    
    
    public void setAthlete(Athlete ath) {
		choosenAthlete = ath;
	}
    
    public void initialize(URL location, ResourceBundle resources) {
		App.updateCoach();
		this.coach = App.getCoach();
		this.db = App.getDb();
		
		this.txtLabelUsername.setText(this.coach.getUsername());
		this.txtAthleteLabel.setText(choosenAthlete.getUsername());
		System.out.println("Heihei");
		updateSleepdata(choosenAthlete) ;
		
    }
    /*
    public void searchAthlete(ActionEvent event) {
		choosenAthlete = db.getAthlete(cboxChooseAthlete.getValue().getUsername());
		updateSleepdata(choosenAthlete);
    }
    */
    
    private void updateSleepdata(Athlete athlete) {
    		List<List<String>> sleepdata = athlete.getRecentSleepData();
    		XYChart.Series<String, Number> quality = new XYChart.Series<>();
    		XYChart.Series<String, Number> length = new XYChart.Series<>();
    		chartDuration.getData().clear();
    		chartQuality.getData().clear();
    		yAxisQuality.setAutoRanging(false);
    		yAxisQuality.setLowerBound(0);
    		yAxisQuality.setUpperBound(100);
    		yAxisQuality.setTickUnit(10);
    		yAxisLength.setAutoRanging(false);
    		yAxisLength.setLowerBound(0);
    		yAxisLength.setUpperBound(11);
    		yAxisLength.setTickUnit(2);
    		System.out.println("McKommerHit");
    		System.out.println();
    		
    		for (List<String> l : sleepdata) {
    			if (l.get(1).equals("0%")) {
    				quality.getData().add(new XYChart.Data<>(l.get(0), Integer.parseInt(l.get(1).substring(0, 1))));
    			} else {
    				String s = l.get(2);
        			String[] s1 = s.split(":");
        			double i = Double.parseDouble(s1[1])/60;
        			double j = Double.parseDouble(s1[0]) + i;
    				quality.getData().add(new XYChart.Data<>(l.get(0), Integer.parseInt(l.get(1).substring(0, 2))));
        			length.getData().add(new XYChart.Data<>(l.get(0), j));
    			}
    		}
    		chartQuality.getData().add(quality);
    		chartDuration.getData().add(length);
    		//chartSleepdata.getData().addAll(length);

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
 	public void backToSeeAthletes(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("SeeAthletes.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
 	
}
