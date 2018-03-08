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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Database;

public class AthleteRequestsController {
	
	// Declaring variables for elements in fxml
	
	private Database database;
	private static Coach coach; 
	
	@FXML
	private TableView<Athlete> tableView;
	
	@FXML
	private TableColumn<Athlete, String> usernameColumn;
	
	@FXML
	private TableColumn<Athlete, String> nameColumn;
	
	@FXML
	private Button btSeeAthletes;
	
	@FXML
	private Button btAthleteRequests;
	
	@FXML
	private Label txtLabelUsername;
	
	private ObservableList<Athlete> athletes = FXCollections.observableArrayList();
	
	
	public void setCoach(Coach newCoach) {
		coach = newCoach;
	}
	
	// Help-method that returns an ObervableList with all the Athlete-requests
	public ObservableList<Athlete> getAthletes(){
		// Adds all the queued athletes in the database
		for (String uname : coach.getQueuedAthletes()) {
			Athlete athlete = database.getAthlete(uname);
			if (athlete != null) {
				athletes.add(athlete);
			}
		}
		return athletes;
	}
	
	
	
	public void initialize() {
		App.updateCoach();
		coach = App.getCoach();
		this.database = App.getDb();
		
		// Sets username label
		this.txtLabelUsername.setText(coach.getUsername());
		
		// Connect columns to right attribute
		usernameColumn.setCellValueFactory(new PropertyValueFactory<Athlete,String>("username"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Athlete,String>("name"));
		
		// Make it legal to select multiple rows
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// Fill table with values
		tableView.setItems(getAthletes());
		
	}
	
	public void acceptButton(ActionEvent event) throws IOException, RuntimeException, InvocationTargetException{
		ObservableList<Athlete> selectedRows;
		
		// Gives us the rows that are selected
		selectedRows = tableView.getSelectionModel().getSelectedItems();
		
		// For each Athlete selected; remove from the screen and approve
		for (Athlete athlete : selectedRows) {
			athletes.remove(athlete);
			coach.approveAthlete(athlete.getUsername());
		}
		
	}

	public void declineButton(ActionEvent event) throws IOException, RuntimeException, InvocationTargetException{
		ObservableList<Athlete> selectedRows;
		
		// Gives us the rows that are selected
		selectedRows = tableView.getSelectionModel().getSelectedItems();
		
		// For each Athlete selected; remove from the screen and decline
		for (Athlete athlete : selectedRows) {
			athletes.remove(athlete);
			coach.declineAthlete(athlete.getUsername());
		}
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
