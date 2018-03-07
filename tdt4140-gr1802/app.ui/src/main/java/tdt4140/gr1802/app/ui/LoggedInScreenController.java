package tdt4140.gr1802.app.ui;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoggedInScreenController {
	
	// Declearing variables for elements in fxml
	@FXML
	private Button backToLogin; 

	public void backToLoginScreen (ActionEvent event) throws IOException{
		Parent root4 = FXMLLoader.load(getClass().getResource("/ui/LoginScreen.fxml"));
		Scene scene = new Scene(root4,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
}
