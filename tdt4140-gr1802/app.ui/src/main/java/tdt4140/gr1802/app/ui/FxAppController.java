package tdt4140.gr1802.app.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.App;

public class FxAppController {

    @FXML
    private Button btbLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblOverhead;

    @FXML
    private Button btbSignUp;
    
  
    
    private App app;
    
    Parent root;
    Scene scene;
    Stage window;
    
  

    @FXML
    void loginButton(ActionEvent event) throws IOException {
    		app = new App();
    	
    		LoginScreenController loginScreen = new LoginScreenController();
    		String typedUsername = txtUsername.getText();
    		String typedPassword = txtPassword.getText();
    		
    		loginScreen.loginButton(event, typedUsername, typedPassword, app.getDb());
    		
    		//
    		if (app.getDb().isAthlete(typedUsername)) {
    			app.setUser(app.getDb().getAthlete(typedUsername));
    		}
    		
    		

    		this.root = FXMLLoader.load(getClass().getResource("HomeScreenAthlete.fxml"));
		this.scene = new Scene(root);
		this.window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		this.changeScene();

    }
    
 

    @FXML
    void signUpButton(ActionEvent event) throws IOException {
    	
    		String typedUsername = txtUsername.getText();
    		String typedPassword = txtPassword.getText();
    	
    		SignUpScreenController signUpScreen = new SignUpScreenController();
    		signUpScreen.getEventuallyTypedInformation(typedUsername, typedPassword);
	
    		this.root = FXMLLoader.load(getClass().getResource("SignUpScreen.fxml"));
    		this.scene = new Scene(root);
    		this.window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    		this.changeScene();
    	
    	
    
    }
    
   
    
    
    private void changeScene() {
    		// Help-method for changing scene, should be called after root, scene and window are changed. 
    		window.setScene(this.scene);
    		window.show();
    }
}
