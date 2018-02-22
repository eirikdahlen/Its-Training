package tdt4140.gr1802.app.ui;

public class FxAppController {
	
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
	
	private Stage signUpStage;
	
	
public void signUpButton(ActionEvent event) throws IOException {
		
		Parent root2 = FXMLLoader.load(getClass().getResource("/application/SignUpScreen.fxml"));
		Scene scene = new Scene(root2);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	
	public void loginButton(ActionEvent event) throws IOException {
		if (txtUsername.getText().equals("Eirik") && txtPassword.getText().equals("passord")) {
			lblOverhead.setText("Login Success!");
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
