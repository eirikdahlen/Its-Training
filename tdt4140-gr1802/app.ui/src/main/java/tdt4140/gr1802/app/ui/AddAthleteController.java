package tdt4140.gr1802.app.ui;


import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.Coach;
import tdt4140.gr1802.app.core.Database;
//import java.lang.reflect.InvocationTargetException;

public class AddAthleteController {
	
	Coach coach ; 
	Database database ;
	
	@FXML
	private TextField txtUsername ;
	
	@FXML
	private Label txtResponse ;
	
	@FXML
	private Button btbAdd ;
	
	
	public void addAthleteButton(ActionEvent event) throws RuntimeException, InvocationTargetException{ 
		if (database.athleteUsernameExists(txtUsername.getText())) {
			coach.addAthlete(txtUsername.getText());
			txtResponse.setText("Request sent to "+txtUsername.getText());
		} else {
			txtResponse.setText("Username does not exist");
		}
		txtUsername.clear();
	}

	
	public static void main(String[] args) {
		System.out.println("Hello");
		
	}

}
