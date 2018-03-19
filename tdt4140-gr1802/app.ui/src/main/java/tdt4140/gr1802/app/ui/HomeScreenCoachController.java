package tdt4140.gr1802.app.ui;

import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;

public class HomeScreenCoachController {
	
	private Coach coach;
	
	// Declaring variables for elements in fxml
	@FXML
	private Button btSeeAthletes;
	
	@FXML
	private Button btAthleteRequests;
	
	@FXML
	private Label txtLabelUsername;
	
	@FXML
	private TableView<Athlete> tableViewTop3;
	
	@FXML
	private TableColumn<Athlete, String> top3Name;
	
	@FXML
	private TableColumn<Athlete, Integer> top3Workouts;
	
	// Home-tab
	@FXML 
	private Text homeTabWelcomeText;
	
	public void initialize() {
		App.updateCoach();
		this.coach = App.getCoach();
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
	
	}
	
	// ------------- HOME ------------- 
	
	
	
	
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
