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
	
	// Declaring variables for elements in fxml
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
    @FXML
    private Label txtLoadingText;
   
    private LoginScreenController loginScreenController = new LoginScreenController();
    private SignUpScreenController signUpScreenController = new SignUpScreenController();
    
    private AddWorkoutController addWorkoutController = new AddWorkoutController();
    private AthleteRequestsController athleteRequestsController = new AthleteRequestsController();
    private CoachRequestsController coachRequestsController = new CoachRequestsController();
    private SeeAthletesController seeAthletesController = new SeeAthletesController();
    private SeeCoachesController seeCoachesController = new SeeCoachesController();
    private SeeWorkoutsController seeWorkoutsController = new SeeWorkoutsController();
    
    private App app;
    private Parent root;
    private Scene scene;
    private Stage window;
    
    // Getter method for username
    public String getTypedUsername() {
    	return txtUsername.getText();
    }
    
    @FXML
    void loginButton(ActionEvent event) throws Exception {
		app = new App();
	
		loginScreenController = new LoginScreenController();
		
		String typedUsername = txtUsername.getText();
		String typedPassword = txtPassword.getText();
		
		loginScreenController.loginButton(event, typedUsername, typedPassword);
		
		// Check valid login and set TextAreaFeedback text
		boolean validLogin = false;
		System.out.println("shjfkldbv");
		if (true) {
			txtLoadingText.setText("Loading...");
		}
		
		
		// If-statements checking username and password
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
			app.setAthlete(app.getDb().getAthlete(this.loginScreenController.getLogin().getUser().getUsername()));
			
			// TODO: Might be deleted, must test first. 
			//App.athlete = App.db.getAthlete(this.loginScreenController.getLogin().getUser().getUsername());
			
			this.root = FXMLLoader.load(getClass().getResource("HomeScreenAthlete.fxml"));
			this.scene = new Scene(root);
			this.window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			this.changeScene();
		} else if (validLogin && loginScreenController.getLogin().checkUsernameCoach(typedUsername)) {
			// --- Valid Coach-login ---
			//this.app.setUser(this.loginScreenController.getLogin().getUser());
			this.app.setCoach(this.app.getDb().getCoach(this.loginScreenController.getLogin().getUser().getUsername()));
			
			// TODO: Might be deleted, must test first
			//App.coach = App.db.getCoach(this.loginScreenController.getLogin().getUser().getUsername());
			
			this.root = FXMLLoader.load(getClass().getResource("HomeScreenCoach.fxml"));
			this.scene = new Scene(root);
			this.window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			this.changeScene();
		}
    }
    
    @FXML
    public void signUpButton(ActionEvent event) throws IOException {
		this.signUpScreenController = new SignUpScreenController();
		this.root = FXMLLoader.load(getClass().getResource("SignUpScreen.fxml"));
		this.scene = new Scene(root);
		this.window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		this.changeScene();
    }
    
    // Help-method for changing scene, should be called after root, scene and window are changed. 
    private void changeScene() {
    	window.setScene(this.scene);
    	window.show();
    }
    
//    private void setControllers() {
//    	
//    	    private AddWorkoutController addWorkoutController = new AddWorkoutController();
//    	    private    AthleteRequestsController athleteRequestsController = new AthleteRequestsController ();
//    	    private CoachRequestsController coachRequestsController = new CoachRequestsController();
//    	    private SeeAthletesController seeAthletesController = new SeeAthletesController();
//    	    private SeeCoachesController seeCoachesController = new SeeCoachesController();
//    	    private SeeWorkoutsController seeWorkoutsController = new SeeWorkoutsController();
//    	
//    	
//    	    addWorkoutController.setAthlete();
//    	
//    		
//    }
}
