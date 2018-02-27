package tdt4140.gr1802.app.ui;

import java.lang.reflect.InvocationTargetException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tdt4140.gr1802.app.core.Athlete;
import tdt4140.gr1802.app.core.Database;

public class RemoveCoachController {
	
	Athlete athlete; 
	Database database = new Database();
	
	@FXML
	private TextField txtUsername ;
	
	@FXML
	private Label txtResponse ;
	
	@FXML
	private Button btbAdd ;
	
	
	public void removeCoachButton(ActionEvent event) throws RuntimeException, InvocationTargetException{ 
		
		System.out.println("hei1");
		if (database.coachUsernameExists(txtUsername.getText()) && athlete.hasCoach(txtUsername.getText())) {
			System.out.println("hei2");
			athlete.removeCoach(txtUsername.getText());
			txtResponse.setText(txtUsername.getText()+"removed from your coach-list");
		} else if (database.coachUsernameExists(txtUsername.getText()) && !athlete.hasCoach(txtUsername.getText())){
			System.out.println("hei3");
			txtResponse.setText(txtUsername.getText()+"is not in your coach-list");
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
