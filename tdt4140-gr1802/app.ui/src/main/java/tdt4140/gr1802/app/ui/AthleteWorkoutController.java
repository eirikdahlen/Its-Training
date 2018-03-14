package tdt4140.gr1802.app.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Workout;

public class AthleteWorkoutController {
	
	@FXML
	private Button btSeeWorkout;
	
	@FXML
	private Button btAddWorkout;
	
	@FXML
	private Button btSeeWorkouts;
	
	@FXML
	private Button btSeeCoaches;
	
	@FXML
	private Button btCoachRequests;
	
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
	
	private Athlete athlete;
	private static Workout workout;
	
	public void initialize() {
		App.updateAthlete();
		this.athlete = App.getAthlete();
		this.txtLabelUsername.setText(this.athlete.getUsername());
		
		dateLabel.setText(workout.getDateString());
		typeLabel.setText(workout.getType());
		durationLabel.setText(String.valueOf(workout.getDuration()));
		maxHRLabel.setText(String.valueOf(workout.getMaxHR()));
	}
	
	public void setWorkout(Workout wo) {
		this.workout = wo;
	}
	
	// Side-menu buttons, changes scenes
		public void clickAddWorkout(ActionEvent event) throws IOException{
			Parent root = FXMLLoader.load(getClass().getResource("AddWorkout.fxml"));
			Scene scene = new Scene(root, 800, 600);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
		}
		
		public void clickSeeWorkouts (ActionEvent event) throws IOException{
			System.out.println("clicked see workouts");
			Parent root = FXMLLoader.load(getClass().getResource("SeeWorkouts.fxml"));
			Scene scene = new Scene(root, 800, 600);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		}
		
		public void clickSeeCoaches(ActionEvent event) throws IOException{
			Parent root = FXMLLoader.load(getClass().getResource("SeeCoaches.fxml"));
			Scene scene = new Scene(root, 800, 600);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
		}
		public void clickCoachRequest(ActionEvent event) throws IOException{
			Parent root = FXMLLoader.load(getClass().getResource("CoachRequests.fxml"));
			Scene scene = new Scene(root, 800, 600);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
		}

}
