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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.SignUp;
import java.io.IOException;
import javafx.event.ActionEvent;


public class SignUpScreenController{
	
	// Declearing variables for elements in fxml	
	private SignUp signUp; 
	@FXML
	private Text txtName; 
	@FXML
	private Text txtUsername;
	@FXML
	private Text txtPassword;
	@FXML
	private Text txtRetype;
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
	
	//Redirects you back to login-screen(fxApp.fxml) when "back to login" button is pressed. 
	public void backToLoginFromSignUpScreen (ActionEvent event) throws IOException{
		Parent root5 = FXMLLoader.load(getClass().getResource("FxApp.fxml"));
		Scene scene = new Scene(root5,1280,720);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	
	//If all conditions are satisfied, user gets redirected to login-screen. 
	@FXML
	public void signUpButtonPressed(ActionEvent event) throws Exception {
		String typedName = txtFieldName.getText();
		String typedUsername = txtFieldUsername.getText();
		String typedPassword = txtFieldPassword.getText();
		String typedPasswordRetype = txtFieldPasswordRetype.getText();
		
		System.out.println(typedName + typedUsername + typedPassword);
		
		boolean isAthlete = radioAthlete.isSelected(); 
		this.signUp = new SignUp(typedUsername, typedName, typedPassword, typedPasswordRetype, isAthlete);
		System.out.println("signp"+this.signUp);
		String textToLabel = "";
		
		// Feedback on possible errors, added in new line
		if (!this.signUp.checkNameOnlyLetters()) {
			textToLabel += "Name can only be letters. \n";
		}
		System.out.println("CHECKKKK");
		if (!this.signUp.checkUserNameValidAndLenght()) {
			textToLabel += "Usename must be only letters and numbers. \n";
		}
		if (!this.signUp.checkUserNameNotExitsInDB()) {
			textToLabel += "Username already in use. \n";
		}
		System.out.println("CHECKKKK2");
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
			Scene scene = new Scene(root5,1280,720);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		}
	}
}
