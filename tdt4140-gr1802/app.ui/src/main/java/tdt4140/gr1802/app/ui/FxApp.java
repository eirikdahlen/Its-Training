package tdt4140.gr1802.app.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxApp extends Application {
	
	private Stage window;
	//Scene loginScreen, signUpScreen, mainScreen;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("FxApp.fxml"));
		Scene scene = new Scene(root, 800, 600);
		// scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	// Main method that launches the app
	public static void main(String[] args) {
		launch(args);
		System.out.println("main method in FxApp");
	}
}
