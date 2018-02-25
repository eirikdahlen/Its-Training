package tdt4140.gr1802.qpp.ui;

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
	
	
	
	public void signUpButton(ActionEvent event) throws IOException {
		
		Parent root2 = FXMLLoader.load(getClass().getResource("/ui/SignUpScreen.fxml"));
		Scene scene = new Scene(root2,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	
	public void loginButton(ActionEvent event) throws IOException {
		if (txtUsername.getText().equals("Eirik") && txtPassword.getText().equals("passord")) {
			lblOverhead.setText("Login Success!");
			Parent root3 = FXMLLoader.load(getClass().getResource("/ui/MainScreen.fxml"));
			Scene scene = new Scene(root3);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(scene);
			window.show();
		} else {
			lblOverhead.setText("Login Failed!");
			
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
