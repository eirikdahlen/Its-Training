package tdt4140.gr1802.app.ui;

import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Database;
import tdt4140.gr1802.app.core.Workout;

public class AddWorkoutController {
	
	// Making variables for every element in the fxml-file
	@FXML
	private Button btAddWorkout;
	
	@FXML
	private Button btSeeWorkouts;
	
	@FXML
	private Button btSeeCoaches;
	
	@FXML
	private Button btCoachRequests;
	
	@FXML
	private TextField filepathTextField;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Label txtLabelUsername;
	
	@FXML
	private CheckBox checkBox;
	
	@FXML
	private TextField gpxField;
	
	
	private Database db;
	
	private static App app;
	
	private static Athlete athlete;
	
	private boolean visibility;
	
	
	// Initialization method
	public void initialize() {
		athlete = app.getAthlete();
		this.db = app.getDb();
		
		// Set username label
		this.txtLabelUsername.setText(athlete.getUsername());
	}
	
	
	// Method called when "Add" button clicked
	public void clickAddButton (ActionEvent event) throws IOException {
		try {
			// The text in the application is used as a filepath, adds workout to the DB
			String path = filepathTextField.getText();
			URL filePath = new File(path).toURI().toURL();
			
			String gpxPath = gpxField.getText();
			System.out.println(gpxPath);
			System.out.println(filePath);
			
			if( checkBox.isSelected() ) {
				this.visibility = false;
			}
			else {
				this.visibility = true;
			}
			
			Workout newWorkout = new Workout(athlete, filePath, this.visibility, gpxPath);
			db.createWorkout(newWorkout);
			System.out.println(this.visibility);
			filepathTextField.setText("Workout Added");
		}
		catch(FileNotFoundException e) {
			filepathTextField.setText("Filepath is not valid");
		}
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

}
