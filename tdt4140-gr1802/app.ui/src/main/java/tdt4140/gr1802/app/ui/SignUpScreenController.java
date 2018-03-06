package tdt4140.gr1802.app.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.Database;
import tdt4140.gr1802.app.core.SignUp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;


public class SignUpScreenController{
	
	private SignUp signUp; 
	
	@FXML
	private Text txtName; 
	@FXML
	private Text txtUsername;
	@FXML
	private Text txtPassword;
	@FXML
	private Text txtRetypePassword;
	@FXML
	private Text txtAthOrCoa;
	@FXML
	private TextField txtFieldName; 
	@FXML
	private TextField txtFieldUsername;
	@FXML
	private TextField txtFieldPassword;
	@FXML
	private TextField txtFieldPasswordRetype;
	@FXML
	private Button btbSignUpButton;
	@FXML
	private Button backToLoginFromSignupButton; 
	@FXML
	private RadioButton radioAthlete;
	@FXML
	private RadioButton radioCoach;
	@FXML
	private Label txtFeedback;

	// Methods for radio-button-function (can only choose one of athlete or coach)
	public void radioAthleteButtonPressed() {radioCoach.setSelected(false); radioAthlete.setSelected(true);}
	public void radioCoachButtonPressed() {radioCoach.setSelected(true); radioAthlete.setSelected(false);}
	

	
	public boolean checkEmptyInformation(){
		if (txtName.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
			return false; 
		}
		else{
			return true; 
		}
	}
	

		
	public void backToLogin(ActionEvent event) throws IOException{
		if (checkEmptyInformation()){
			
			
			Parent root2 = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
			Scene toLoginScene = new Scene(root2,800,600);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(toLoginScene);
			window.show();
		}
		
	}
	
	public void backToLoginFromSignUpScreen (ActionEvent event) throws IOException{
		
		Parent root5 = FXMLLoader.load(getClass().getResource("FxApp.fxml"));
		Scene scene = new Scene(root5,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	
	@FXML
	public void signUpButtonPressed(ActionEvent event) throws IOException {
		String typedName = txtFieldName.getText();
		String typedUsername = txtFieldUsername.getText();
		String typedPassword = txtFieldPassword.getText();
		String typedPasswordRetype = txtFieldPasswordRetype.getText();
		
		System.out.println(typedName + typedUsername + typedPassword);
		
		boolean isAthlete = radioAthlete.isSelected(); 
		
		this.signUp = new SignUp(typedUsername, typedName, typedPassword, typedPasswordRetype, isAthlete);
		
		String textToLabel = "";
		
		if (!this.signUp.checkNameOnlyLetters()) {
			textToLabel += "Name can only be letters. \n";
		}
		
		if (!this.signUp.checkUserNameValidAndLenght()) {
			textToLabel += "Usename must be only letters and numbers. \n";
		}
		
		if (!this.signUp.checkUserNameNotExitsInDB()) {
			textToLabel += "Username already in use. \n";
		}
		
		if (!this.signUp.checkPasswordLength()) {
			textToLabel += "Password must be 4 chars or longer. \n";
		}
		
		if (!this.signUp.checkEqualPasswords()) {
			textToLabel += "Passwors must be equal. \n";
		}
		
		txtFeedback.setText(textToLabel);
		
		if (this.signUp.validSignUp()) {
			System.out.println("Valid sign up. User created.");
			System.out.println("Redirecting to login screen");
			
			Parent root5 = FXMLLoader.load(getClass().getResource("FxApp.fxml"));
			Scene scene = new Scene(root5,800,600);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		}
	}
	
	// Currently not in use
	/*public ArrayList<String> getEventuallyTypedInformation(String typedUsername, String typedPassword) {
		ArrayList<String> nameAndPassword = new ArrayList<>();
		nameAndPassword.add(typedUsername); nameAndPassword.add(typedUsername);
		return nameAndPassword;
	}*/
	
}
