package tdt4140.gr1802.app.ui;

import java.lang.reflect.InvocationTargetException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Database;

public class RemoveAthleteController {
	
	Coach coach; 
	Database database = new Database();
	
	@FXML
	private TextField txtUsername ;
	
	@FXML
	private Label txtResponse ;
	
	@FXML
	private Button btbAdd ;
	
	
	public void removeAthleteButton(ActionEvent event) throws RuntimeException, InvocationTargetException{ 
		
		System.out.println("hei1");
		if (database.athleteUsernameExists(txtUsername.getText()) && coach.hasAthlete(txtUsername.getText())) {
			System.out.println("hei2");
			coach.removeAthlete(txtUsername.getText());
			txtResponse.setText(txtUsername.getText()+"removed from your athlete-list");
		} else if (database.athleteUsernameExists(txtUsername.getText()) && !coach.hasAthlete(txtUsername.getText())){
			System.out.println("hei3");
			txtResponse.setText(txtUsername.getText()+"is not in your athlete-list");
		} else {
			System.out.println("hei4");
			txtResponse.setText("Username does not exist");
		}
		System.out.println("hei5");
		txtUsername.clear();
	}
	
	public static void main(String[] args) {
		System.out.println("hello");
	}

}
