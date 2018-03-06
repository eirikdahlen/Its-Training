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
import javafx.scene.control.TextArea;
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
    @FXML
    private Label txtLabelFeedback;
   
    private LoginScreenController loginScreenController;
    private SignUpScreenController signUpScreenController;
    
    private App app;
    
    private Parent root;
    private Scene scene;
    private Stage window;
    
  
    public String getTypedUsername() {
    		return txtUsername.getText();
    }
    

    @FXML
    void loginButton(ActionEvent event) throws IOException {
    		app = new App();
    	
    		loginScreenController = new LoginScreenController();
    		
    		String typedUsername = txtUsername.getText();
    		String typedPassword = txtPassword.getText();
    		
    		loginScreenController.loginButton(event, typedUsername, typedPassword);
    		
    		// Check valid login and set TextAreaFeedback text
    		boolean validLogin = false;
    		
    		if (!loginScreenController.getLogin().checkUserNameExits(typedUsername)) {
    			
    			txtLabelFeedback.setText("Username don't exits");
    			System.out.println(txtLabelFeedback.getText());
    			
    		} else if (loginScreenController.getLogin().checkUserNameExits(typedUsername) &&
    				!loginScreenController.getLogin().checkUsernameMatchPassword(typedUsername, typedPassword)) {
    			txtLabelFeedback.setText("Wrong password");
    		} else if (loginScreenController.getLogin().validLogIn()) {
    			txtLabelFeedback.setText("Logging in...");
    			validLogin = true;
    		}
    		
    		if (validLogin && loginScreenController.getLogin().checkUsernameAthlete(typedUsername)) {
    			
    			// --- Valid Athlete-login ---
    			this.app.setUser(this.loginScreenController.getLogin().getUser());
    			
    			this.root = FXMLLoader.load(getClass().getResource("HomeScreenAthlete.fxml"));
    			this.scene = new Scene(root);
    			this.window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    			this.changeScene();
    		} else if (validLogin && loginScreenController.getLogin().checkUsernameCoach(typedUsername)) {
    			
    			// --- Valid Coach-login ---
    			this.app.setUser(this.loginScreenController.getLogin().getUser());
    			
    			this.root = FXMLLoader.load(getClass().getResource("HomeScreenCoach.fxml"));
    			this.scene = new Scene(root);
    			this.window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    			this.changeScene();
    		}
    		
    		System.out.println(this.app.getUser());

    }
    
 

    @FXML
    public void signUpButton(ActionEvent event) throws IOException {
    		
    		this.signUpScreenController = new SignUpScreenController();
 
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
