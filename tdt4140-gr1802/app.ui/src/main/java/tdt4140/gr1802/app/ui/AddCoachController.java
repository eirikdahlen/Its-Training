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
	
	Athlete athlete; 
	Database database = new Database();
	
	@FXML
	private TextField txtUsername ;
	
	@FXML
	private Label txtResponse ;
	
	@FXML
	private Button btbAdd ;
	
	
	public void addCoachButton(ActionEvent event) throws RuntimeException, InvocationTargetException{ 
		
		System.out.println("hei1");
		txtUsername.clear();
		if (database.coachUsernameExists(txtUsername.getText())) {
			System.out.println("hei2");
			athlete.addCoach(txtUsername.getText());
			txtResponse.setText("Request sent to "+txtUsername.getText());
		} else {
			System.out.println("hei3");
			txtResponse.setText("Username does not exist");
		}
		System.out.println("hei4");
		txtUsername.clear();
	}
	
	public static void main(String[] args) {
		System.out.println("hello");
	}
	
	

}
