package tdt4140.gr1802.app.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.Database;

import java.awt.TextArea;
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
	private Button btbsignUp_back; 
	@FXML
	private Button BackToLoginFromSignUp; 
	@FXML
	private RadioButton radioAthlete;
	@FXML
	private RadioButton radioCoach;

	Database db = new Database();
	
	
	public boolean checkValidName() {
		// Check if name only contains letters
		if (txtName.getText().matches("[a-zA-Z]")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkUserName() {
		String typedUserName = txtFieldUsername.getText(); 
		
		if (db.coachUsernameExists(typedUserName) || db.athleteUsernameExists(typedUserName)) {
			// Username exits in database
			System.out.println("Username already in database");
			return false;
		} else {
			return false;
		}
	}
	
	public boolean checkPasswordLenght() {
		String typedPassword = txtFieldPassword.getText();
		
		if (typedPassword.length() <= 4) {
			System.out.println("Password shorter than 4 chars");
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkEqualPasswords() {
		return txtFieldPassword.getText().equals(txtFieldPasswordRetype.getText());
	}
	
	private void radioAthleteButtonPressed() {radioCoach.setSelected(false); radioAthlete.setSelected(true);}
	private void coachAthleteButtonPressed() {radioCoach.setSelected(true); radioAthlete.setSelected(false);}
	
	
	
	public boolean checkInformation(){
		if (txtName.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
			return false; 
		}
		else{
			return true; 
		}
	}
	
	public void writeToFile() throws IOException{
		String text = txtName.getText();
        Files.write(Paths.get("/Users/Andreas/tdt4100-2017-master/ws/Hola/src/application/Account.txt"), text.getBytes());
		
	}
	
	
	
	public void backToLogin(ActionEvent event) throws IOException{
		if (checkInformation()){
			writeToFile();
			
			Parent root2 = FXMLLoader.load(getClass().getResource("/ui/Login.fxml"));
			Scene toLoginScene = new Scene(root2,800,600);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(toLoginScene);
			window.show();
		}

		txtName.setText("");
		txtUsername.setText("");
		txtPassword.setText("");
		
		
	}
	
	public void backToLoginFromSignUpScreen (ActionEvent event) throws IOException{
		Parent root5 = FXMLLoader.load(getClass().getResource("/ui/LoginScreen.fxml"));
		Scene scene = new Scene(root5,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	

	
	
	public static void main(String[] args) {
	
		System.out.println("hei");
		
		
	}
	
}
