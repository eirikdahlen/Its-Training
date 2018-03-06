package tdt4140.gr1802.app.ui;

import tdt4140.gr1802.app.core.Database;
import tdt4140.gr1802.app.core.LogIn;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;


public class LoginScreenController {
	
	LogIn login; 
	
	@FXML
	private Label lblOverhead;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	@FXML
	public Button btbSignUp;
	@FXML
	public Button btbLogin;
	@FXML
	private AnchorPane apSignUpPane;
	@FXML
	private Stage signUpStage;
	
	private Database db = new Database();
	
	
	
	
	public void signUpButton(ActionEvent event) throws IOException {
		
		Parent root2 = FXMLLoader.load(getClass().getResource("/tdt4140.gr1802.app.ui/SignUpScreen.fxml"));
		Scene scene = new Scene(root2,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	public LogIn getLogin() {return this.login; }
	
	
	public void loginButton(ActionEvent event, String typedUsername, String typedPassword) throws IOException {

		
		login = new LogIn(typedUsername, typedPassword);
		
		
		if (login.validLogIn()) {
			System.out.println("Login Success!");

		} else if (!login.checkUsernameAthlete(typedUsername) 
				|| !login.checkUsernameCoach(typedUsername)) {
			System.out.println("Username don't excists.");
			
		} else if (login.checkUsernameMatchPassword(typedUsername, typedPassword)) {
			System.out.println("Username don't matches password.");
		}
		
		/*
		Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
		Scene scene = new Scene(root);
		// scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		secondStage.setScene(scene);
		secondStage.show();
		*/
		
	}	
	
}
