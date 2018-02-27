package tdt4140.gr1802.app.ui;

import java.lang.reflect.InvocationTargetException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Database;

public class AddCoachController {
	
	Athlete athlete ; 
	Database database ;
	
	@FXML
	private TextField txtUsername ;
	
	@FXML
	private Label txtResponse ;
	
	@FXML
	private Button btbAdd ;
	
	
	
	public void addCoachButton(ActionEvent event) throws RuntimeException, InvocationTargetException{ 
		if (database.coachUsernameExists(txtUsername.getText())) {
			athlete.addCoach(txtUsername.getText());
			txtResponse.setText("Request sent to "+txtUsername.getText());
		} else {
			txtResponse.setText("Username does not exist");
		}
		txtUsername.clear();
	}
	
	public static void main(String[] args) {
		System.out.println("hello");
	}
	
	

}
