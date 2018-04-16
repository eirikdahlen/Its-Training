package tdt4140.gr1802.app.ui;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Database;

public class SeeCoachesController {
	
	// Declaring variables for elements in fxml
	private Database database;
	private Athlete athlete;
	
	@FXML
	private TableView<Coach> tableView;
	
	@FXML
	private TableColumn<Coach, String> usernameColumn;
	
	@FXML
	private TableColumn<Coach, String> nameColumn;
	
	@FXML
	private Button btAddCoach;
	
	@FXML
	private TextField newCoachTextField;
	
	@FXML
	private Button btAddWorkout;
	
	@FXML
	private Button btAddSleepdata ;
	
	@FXML
	private Button btSeeWorkouts;
	
	@FXML
	private Button btCoachRequests;
	
	@FXML
	private Button btRemove;
	
	@FXML
	private Label txtAddCoachRespons;
	
	@FXML
	private Label txtLabelUsername;
	
	@FXML
	private Button homeScreenButton;
	
	private ObservableList<Coach> coaches = FXCollections.observableArrayList();
	
	
	
	// Returns an ObervableList with the Coaches registered to the Athlete logged in
	public ObservableList<Coach> getCoaches(){
		for (String uname : athlete.getCoaches()) {
			Coach coach = database.getCoach(uname);
			if (coach != null) {
				coaches.add(coach);
			}
		}
		return coaches;
	}
	
	public void initialize() {
		App.updateAthlete();
		this.athlete = App.getAthlete();
		this.database = App.getDb(); 
		
		// Sets username label
		this.txtLabelUsername.setText(this.athlete.getUsername());
		
		// Connect columns to right attribute
		nameColumn.setCellValueFactory(new PropertyValueFactory<Coach,String>("name"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<Coach,String>("username"));
		
		// Make it legal to select multiple rows
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// Fill table with values
		tableView.setItems(getCoaches());
		
	}
	
	public void addCoachButton(ActionEvent event) throws RuntimeException, InvocationTargetException{
		// Get the String from the TextField
		String newCoach = newCoachTextField.getText();
		
		// Check which case that apply
		if(database.coachUsernameExists(newCoach) && !athlete.getCoaches().contains(newCoach)) {
			txtAddCoachRespons.setText("Request sent to "+newCoach);
			athlete.sendCoachRequest(newCoach);
		} else if (database.coachUsernameExists(newCoach) && athlete.getCoaches().contains(newCoach)){
			txtAddCoachRespons.setText("This is already your coach");
		} else if (!database.coachUsernameExists(newCoach)) {
			txtAddCoachRespons.setText("Username does not exist");
		}
		
		// Clear the TextField
		newCoachTextField.clear();
	}
	
	public void removeCoachButton(ActionEvent event) throws RuntimeException, InvocationTargetException{
		// ObservableList with the selectedRow
		ObservableList<Coach> selectedRows;
		selectedRows = tableView.getSelectionModel().getSelectedItems();
		
		// Iterate through all the selected rows, and deleting them
		for (Coach coach : selectedRows) {
			coaches.remove(coach);
			athlete.removeCoach(coach.getUsername());
		}
	}
	
	@FXML
    public void homeScreenButtonCursorHand() {
    	homeScreenButton.setCursor(Cursor.HAND);
    }
    @FXML
    public void homeScreenButtonCursorDefault() {
    	homeScreenButton.setCursor(Cursor.DEFAULT);
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
	
	public void backToHomeScreen(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("HomeScreenAthlete.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	public void clickAddSleepdata (ActionEvent event) throws IOException, LoadException{
		// Open new window 
		Parent root = FXMLLoader.load(getClass().getResource("AddSleepdata.fxml"));
		Scene scene = new Scene(root,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	
}
